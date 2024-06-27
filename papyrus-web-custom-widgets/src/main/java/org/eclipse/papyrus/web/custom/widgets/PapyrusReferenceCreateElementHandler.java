/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Mono and multi reference implementation of {@link IReferenceWidgetCreateElementHandler}.
 *
 * @author Jerome Gout
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PapyrusReferenceCreateElementHandler implements IReferenceWidgetCreateElementHandler {

    private static final String MULTI_REF_DESCRIPTION = PapyrusWidgetsPackage.eINSTANCE.getMultiReferenceWidgetDescription().getName();

    private static final String MONO_REF_DESCRIPTION = PapyrusWidgetsPackage.eINSTANCE.getMonoReferenceWidgetDescription().getName();

    private static final String CHILD_CREATION_DESCRIPTION_ID_SEPARATOR = "::";

    private final IEMFKindService emfKindService;

    private final IObjectService objectService;

    private final IViewRepresentationDescriptionSearchService viewSearchService;

    private final IAQLInterpreterProvider interpreterProvider;

    private final IEditService editService;

    public PapyrusReferenceCreateElementHandler(IEMFKindService emfKindService, IObjectService objectService, IViewRepresentationDescriptionSearchService viewSearchService,
            IAQLInterpreterProvider interpreterProvider, IEditService editService) {
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.objectService = Objects.requireNonNull(objectService);
        this.viewSearchService = Objects.requireNonNull(viewSearchService);
        this.interpreterProvider = Objects.requireNonNull(interpreterProvider);
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(String descriptionId) {
        if (descriptionId != null && descriptionId.startsWith(IFormIdProvider.FORM_ELEMENT_DESCRIPTION_PREFIX)) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(descriptionId);
            String sourceKind = parameters.get(IRepresentationDescriptionIdProvider.SOURCE_KIND).get(0);
            String kind = parameters.get(IRepresentationDescriptionIdProvider.KIND).get(0);
            return IRepresentationDescriptionIdProvider.VIEW_SOURCE_KIND.equals(sourceKind) && (MONO_REF_DESCRIPTION.equals(kind) || MULTI_REF_DESCRIPTION.equals(kind));
        } else {
            return false;
        }
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId) {
        return List.of();
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId) {
        List<EClass> childrenTypes = this.getInstanciableTypesOf(editingContext, referenceKind);
        return this.toEClass(editingContext, kind)//
                .map(eClass -> this.createChildCreationDescription(eClass, childrenTypes))//
                .orElse(List.of());

    }

    private List<ChildCreationDescription> createChildCreationDescription(EClass eClass, List<EClass> childrenTypes) {
        return eClass.getEAllReferences().stream()//
                .filter(ref -> ref.isContainment()).flatMap(ref -> this.createChildCreationDescription(ref, childrenTypes).stream()).sorted(Comparator.comparing(ChildCreationDescription::getLabel))
                .toList();
    }

    private List<ChildCreationDescription> createChildCreationDescription(EReference ref, List<EClass> childrenTypes) {
        List<ChildCreationDescription> children = new ArrayList<>();
        for (EClass child : childrenTypes) {
            if (ref.getEReferenceType().isSuperTypeOf(child)) {
                EObject instance = child.getEPackage().getEFactoryInstance().create(child);
                ChildCreationDescription description = new ChildCreationDescription(ref.getName() + CHILD_CREATION_DESCRIPTION_ID_SEPARATOR + child.getName(),
                        child.getName() + " (in " + ref.getName() + ")", this.objectService.getImagePath(instance));
                children.add(description);
            }
        }
        return children;
    }

    private Optional<EClass> toEClass(IEditingContext editingContext, String kind) {
        Optional<Registry> optionalRegistry = this.getPackageRegistry(editingContext);
        if (optionalRegistry.isPresent() && !kind.isBlank() && kind.startsWith(SemanticKindConstants.PREFIX)) {
            var ePackageRegistry = optionalRegistry.get();
            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);
            Optional<EPackage> optionalPackage = this.emfKindService.findEPackage(ePackageRegistry, ePackageName);

            if (optionalPackage.isPresent()) {
                var pack = optionalPackage.get();
                EClassifier classifier = pack.getEClassifier(eClassName);
                if (classifier instanceof EClass eClass) {
                    return Optional.of(eClass);
                }
            }
        }
        return Optional.empty();
    }

    private List<EClass> getInstanciableTypesOf(IEditingContext editingContext, String referenceKind) {
        return this.toEClass(editingContext, referenceKind).map(this::getInstanciableTypesOf).orElse(List.of());
    }

    private List<EClass> getInstanciableTypesOf(EClass type) {
        EPackage pack = type.getEPackage();
        return pack.getEClassifiers().stream()//
                .filter(EClass.class::isInstance)//
                .map(EClass.class::cast)//
                .filter(e -> !e.isAbstract() && !e.isInterface() && type.isSuperTypeOf(e))//
                .toList();
    }

    private Optional<Registry> getPackageRegistry(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext) {
            Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return Optional.of(packageRegistry);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, String descriptionId) {
        // No creation at resource level is allowed in Papyrus
        return Optional.empty();
    }

    @Override
    public Optional<Object> createChild(IEditingContext editingContext, Object parent, String childCreationDescriptionId, String descriptionId) {
        var res = Optional.empty();
        Map<String, List<String>> parameters = new URLParser().getParameterValues(descriptionId);
        String kind = parameters.get(IRepresentationDescriptionIdProvider.KIND).get(0);
        if (MONO_REF_DESCRIPTION.equals(kind)) {
            res = this.createMonoReferenceChild(editingContext, parent, childCreationDescriptionId, descriptionId);
        } else if (MULTI_REF_DESCRIPTION.equals(kind)) {
            res = this.createMultiReferenceChild(editingContext, parent, childCreationDescriptionId, descriptionId);
        }
        return res;
    }

    private Optional<Object> createMultiReferenceChild(IEditingContext editingContext, Object parent, String childCreationDescriptionId, String descriptionId) {
        var optionalWidgetDescription = this.viewSearchService.findViewFormElementDescriptionById(editingContext, descriptionId).filter(MultiReferenceWidgetDescription.class::isInstance)
                .map(MultiReferenceWidgetDescription.class::cast);
        if (optionalWidgetDescription.isPresent()) {
            var reference = optionalWidgetDescription.get();
            var optionalView = this.getViewFromWidgetDescription(reference);
            if (optionalView.isPresent() && reference.getName() != null) {
                var createOperation = reference.getCreateElementOperation();
                VariableManager variableManager = this.createVariableManagerForElementCreation(editingContext, parent, childCreationDescriptionId, reference.getName());
                VariableManager childVariableManager = variableManager.createChild();
                AQLInterpreter interpreter = this.interpreterProvider.createInterpreter(optionalView.get(), editingContext);
                OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(createOperation.getBody(), childVariableManager);
                if (optionalVariableManager.isPresent()) {
                    Optional<Object> res = optionalVariableManager.get().get(VariableManager.SELF, Object.class);
                    return res;
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Object> createMonoReferenceChild(IEditingContext editingContext, Object parent, String childCreationDescriptionId, String descriptionId) {
        var optionalWidgetDescription = this.viewSearchService.findViewFormElementDescriptionById(editingContext, descriptionId).filter(MonoReferenceWidgetDescription.class::isInstance)
                .map(MonoReferenceWidgetDescription.class::cast);
        if (optionalWidgetDescription.isPresent()) {
            var reference = optionalWidgetDescription.get();
            var optionalView = this.getViewFromWidgetDescription(reference);
            if (optionalView.isPresent() && reference.getName() != null) {
                var createOperation = reference.getCreateElementOperation();
                VariableManager variableManager = this.createVariableManagerForElementCreation(editingContext, parent, childCreationDescriptionId, reference.getName());
                VariableManager childVariableManager = variableManager.createChild();

                AQLInterpreter interpreter = this.interpreterProvider.createInterpreter(optionalView.get(), editingContext);
                OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
                Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(createOperation.getBody(), childVariableManager);
                if (optionalVariableManager.isPresent()) {
                    Optional<Object> res = optionalVariableManager.get().get(VariableManager.SELF, Object.class);
                    return res;
                }
            }
        }
        return Optional.empty();
    }

    private VariableManager createVariableManagerForElementCreation(IEditingContext editingContext, Object parent, String creationDescriptionId, String name) {
        var variableManager = new VariableManager();

        String[] parts = creationDescriptionId.split(CHILD_CREATION_DESCRIPTION_ID_SEPARATOR);
        String referenceName = parts[0];
        String childType = parts[1];

        variableManager.put("parent", parent);
        variableManager.put("kind", childType);
        variableManager.put("feature", referenceName);

        return variableManager;
    }

    private Optional<View> getViewFromWidgetDescription(EObject widgetDescription) {
        EObject container = widgetDescription;
        while (!(container instanceof View) && container != null) {
            container = container.eContainer();
        }
        if (container instanceof View view) {
            return Optional.of(view);
        }
        return Optional.empty();
    }
}
