/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.documents;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.document.UploadDocumentInput;
import org.eclipse.sirius.web.services.api.document.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.documents.EObjectRandomIDManager;
import org.eclipse.sirius.web.services.editingcontext.api.IEditingDomainFactoryService;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler used to create a new document from a file upload.
 *
 * @author sbegaudeau
 */
@ServiceOverride(org.eclipse.sirius.web.services.documents.UploadDocumentEventHandler.class)
public class UploadDocumentEventHandlerCustomImpl implements IEditingContextEventHandler {

    private final Logger logger = LoggerFactory.getLogger(UploadDocumentEventHandlerCustomImpl.class);

    private final IDocumentService documentService;

    private final IServicesMessageService messageService;

    private final IEditingContextMetadataProvider editingContextMetadataProvider;

    private final Counter counter;

    private final IEditingDomainFactoryService editingDomainFactoryService;

    public UploadDocumentEventHandlerCustomImpl(IDocumentService documentService, IServicesMessageService messageService, MeterRegistry meterRegistry,
            IEditingDomainFactoryService editingDomainFactoryService, IEditingContextMetadataProvider editingContextMetadataProvider) {
        this.documentService = Objects.requireNonNull(documentService);
        this.messageService = Objects.requireNonNull(messageService);
        this.editingContextMetadataProvider = Objects.requireNonNull(editingContextMetadataProvider);
        this.editingDomainFactoryService = Objects.requireNonNull(editingDomainFactoryService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UploadDocumentInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof UploadDocumentInput uploadDocumentInput) {

            String projectId = uploadDocumentInput.editingContextId();
            UploadFile file = uploadDocumentInput.file();

            Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext).filter(IEMFEditingContext.class::isInstance).map(IEMFEditingContext.class::cast)
                    .map(IEMFEditingContext::getDomain);

            String name = file.getName().trim();
            if (optionalEditingDomain.isPresent()) {
                AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();

                Optional<String> contentOpt = this.getContent(editingContext, adapterFactoryEditingDomain.getResourceSet().getPackageRegistry(), file, uploadDocumentInput.checkProxies(), projectId);
                var optionalDocument = contentOpt.flatMap(content -> this.documentService.createDocument(projectId, name, content));

                if (optionalDocument.isPresent()) {
                    Document document = optionalDocument.get();
                    ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(document.getId().toString());

                    if (resourceSet.getResource(uri, false) == null) {

                        ResourceSet loadingResourceSet = this.editingDomainFactoryService.createEditingDomain(UUID.randomUUID().toString()).getResourceSet();

                        JsonResource resource = new JSONResourceFactory().createResource(uri);
                        loadingResourceSet.getResources().add(resource);
                        try (var inputStream = new ByteArrayInputStream(document.getContent().getBytes())) {
                            resource.load(inputStream, adapterFactoryEditingDomain.getResourceSet().getLoadOptions());
                        } catch (IOException exception) {
                            this.logger.warn(exception.getMessage(), exception);
                        }

                        resource.eAdapters().add(new ResourceMetadataAdapter(name));
                        resourceSet.getResources().add(resource);

                        payload = new UploadDocumentSuccessPayload(input.id(), document);
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                    }
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<String> getContent(IEditingContext editingContext, EPackage.Registry registry, UploadFile file, boolean checkProxies, String editingContextId) {
        var isStudioProjectNature = this.editingContextMetadataProvider.getMetadata(editingContextId).natures().stream().map(Nature::natureId)
                .anyMatch("siriusComponents://nature?kind=studio"::equals);
        String fileName = file.getName();
        Optional<String> content = Optional.empty();
        // Custo: Need fully configure editing domain to resolve pathmap
        ResourceSet resourceSet = this.editingDomainFactoryService.createEditingDomain(editingContext.getId()).getResourceSet();
        resourceSet.setPackageRegistry(registry);
        if (isStudioProjectNature) {
            this.loadStudioColorPalettes(resourceSet);
        }
        try (var inputStream = file.getInputStream()) {
            URI resourceURI = new JSONResourceFactory().createResourceURI(fileName);
            Optional<Resource> optionalInputResource = this.getResource(inputStream, resourceURI, resourceSet);
            if (optionalInputResource.isPresent()) {
                Resource inputResource = optionalInputResource.get();

                if (checkProxies && this.containsProxies(inputResource)) {
                    this.logger.warn("The resource {} contains unresolvable proxies and will not be uploaded.", fileName);
                } else {
                    JsonResource ouputResource = new JSONResourceFactory().createResourceFromPath(fileName);
                    resourceSet.getResources().add(ouputResource);
                    ouputResource.getContents().addAll(inputResource.getContents());

                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        Map<String, Object> saveOptions = new HashMap<>();
                        saveOptions.put(JsonResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
                        saveOptions.put(JsonResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                        saveOptions.put(JsonResource.OPTION_ID_MANAGER, new EObjectRandomIDManager());

                        ouputResource.save(outputStream, saveOptions);

                        content = Optional.of(outputStream.toString());
                    }
                }
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return content;
    }

    private boolean containsProxies(Resource resource) {
        Iterable<EObject> iterable = () -> EcoreUtil.getAllProperContents(resource, false);
        return StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(eObject -> eObject.eClass().getEAllReferences().stream().filter(ref -> !ref.isContainment() && eObject.eIsSet(ref)).anyMatch(ref -> {
                    boolean containsAProxy = false;
                    Object value = eObject.eGet(ref);
                    if (ref.isMany()) {
                        List<?> list = (List<?>) value;
                        containsAProxy = list.stream().filter(EObject.class::isInstance).map(EObject.class::cast).anyMatch(EObject::eIsProxy);
                    } else if (value instanceof EObject eObjectValue) {
                        containsAProxy = eObjectValue.eIsProxy();
                    }
                    return containsAProxy;
                }));
    }

    /**
     * Returns the {@link Resource} with the given {@link URI} or {@link Optional#empty()} regarding to the content of
     * the first line of the given {@link InputStream}.
     *
     * <p>
     * Returns a {@link JsonResourceImpl} if the first line contains a '{', a {@link XMIResourceImpl} if the first line
     * contains '<', {@link Optional#empty()} otherwise.
     * </p>
     *
     * @param inputStream The {@link InputStream} used to determine which {@link Resource} to create
     * @param resourceURI The {@link URI} to use to create the {@link Resource}
     * @param resourceSet The {@link ResourceSet} used to store the loaded resource
     * @return a {@link JsonResourceImpl}, a {@link XMIResourceImpl} or {@link Optional#empty()}
     */
    private Optional<Resource> getResource(InputStream inputStream, URI resourceURI, ResourceSet resourceSet) {
        Resource resource = null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        try (var reader = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            Map<Object, Object> options = new HashMap<>();
            if (line != null) {
                if (line.contains("{")) {
                    resource = new JSONResourceFactory().createResource(resourceURI);
                } else if (line.contains("<")) {
                    AdapterFactoryEditingDomain editingDomain = this.editingDomainFactoryService.createEditingDomain(UUID.randomUUID().toString());
                    resource = editingDomain.getResourceSet().getResource(resourceURI, false);
                    if (resourceURI.toString().endsWith(".uml")) {

                        resource = new UMLResourceImpl(resourceURI);
                    } else {

                        resource = new XMIResourceImpl(resourceURI);
                    }

                    options = resourceSet.getLoadOptions();
                }
            }
            bufferedInputStream.reset();
            if (resource != null) {
                resourceSet.getResources().add(resource);
                resource.load(bufferedInputStream, options);
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return Optional.ofNullable(resource);
    }

    private void loadStudioColorPalettes(ResourceSet resourceSet) {
        ClassPathResource classPathResource = new ClassPathResource("studioColorPalettes.json");
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
        Resource resource = new JSONResourceFactory().createResource(uri);
        try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
            resourceSet.getResources().add(resource);
            resource.load(inputStream, null);
            resource.eAdapters().add(new ResourceMetadataAdapter("studioColorPalettes"));
        } catch (IOException exception) {
            this.logger.warn("An error occured while loading document studioColorPalettes.json: {}.", exception.getMessage());
            resourceSet.getResources().remove(resource);
        }
    }

}