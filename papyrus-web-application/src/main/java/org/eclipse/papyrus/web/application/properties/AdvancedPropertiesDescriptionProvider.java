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
 *     CEA - Adaptation for Papyrus Web
 *******************************************************************************/
package org.eclipse.papyrus.web.application.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.compatibility.emf.properties.EEnumIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.EStringIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.NumberIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.PropertiesDefaultDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.uml2.uml.Element;
import org.springframework.stereotype.Service;

/**
 * Custom implemenation of {@link PropertiesDefaultDescriptionProvider} to display an advanced view for UML properties.
 *
 * @see https://github.com/PapyrusSirius/papyrus-web/issues/58
 *
 * @author Arthur Daussy
 */

@Service
public class AdvancedPropertiesDescriptionProvider {

    public static final String ESTRUCTURAL_FEATURE = "eStructuralFeature";

    private final IObjectService objectService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IEMFMessageService emfMessageService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IEMFKindService emfKindService;

    private final IFeedbackMessageService feedbackMessageService;

    public AdvancedPropertiesDescriptionProvider(IObjectService objectService, ComposedAdapterFactory composedAdapterFactory, IEMFMessageService emfMessageService,
            IFeedbackMessageService feedbackMessageService, IEMFKindService emfKindService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = new IPropertiesValidationProvider.NoOp(); // Unplug live validation
                                                                                      // validation
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getId).orElse(null);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);

    }

    public FormDescription getFormDescription() {
        List<GroupDescription> groupDescriptions = new ArrayList<>();
        GroupDescription groupDescription = this.getGroupDescription();

        groupDescriptions.add(groupDescription);

        List<PageDescription> pageDescriptions = new ArrayList<>();
        PageDescription firstPageDescription = this.getPageDescription(groupDescriptions);
        pageDescriptions.add(firstPageDescription);

        Function<VariableManager, String> labelProvider = variableManager -> "Properties";

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(UUID.nameUUIDFromBytes("UMLAdvancedPropertyViewForm".getBytes()).toString())
                .label("Default form description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> this.canCreatePage(variableManager))
                .pageDescriptions(pageDescriptions)
                .build();
    }

    private boolean canCreatePage(VariableManager variableManager) {
        var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
        if (optionalSelf.isPresent()) {
            return optionalSelf.get() instanceof Element;
        }
        return false;
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        Function<VariableManager, String> idProvider = variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();
                return this.objectService.getId(self);
            }
            return UUID.randomUUID().toString();
        };

        Function<VariableManager, String> labelProvider = variableManager -> "Advanced";

        return PageDescription.newPageDescription("UMLAdvancedPropertyViewPage")
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> this.canCreatePage(variableManager))
                .build();
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            List<Object> objects = new ArrayList<>();

            Object self = variableManager.getVariables().get(VariableManager.SELF);
            if (self instanceof EObject) {
                EObject eObject = (EObject) self;

                List<IItemPropertyDescriptor> propertyDescriptors = Optional.ofNullable(this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class))
                        .filter(IItemPropertySource.class::isInstance)
                        .map(IItemPropertySource.class::cast)
                        .map(iItemPropertySource -> iItemPropertySource.getPropertyDescriptors(eObject))
                        .orElse(new ArrayList<>());

                propertyDescriptors.stream() //
                        .map(propertyDescriptor -> propertyDescriptor.getFeature(eObject))
                        .filter(EStructuralFeature.class::isInstance)
                        .map(EStructuralFeature.class::cast)
                        // Prevents EReference targeting EModelElements and EObject.
                        // (https://github.com/PapyrusSirius/papyrus-web/issues/58)
                        // * It can return thousands of elements making the UI really slow
                        // * On some candidates an id cannot be computed (EPackage) causing NPE (nevertheless this case
                        // should be fixed in Sirius Component)
                        // https://github.com/eclipse-sirius/sirius-components/issues/1433
                        .filter(feature -> feature.getEType() != EcorePackage.eINSTANCE.getEObject() && feature.getEType() != EcorePackage.eINSTANCE.getEModelElement())
                        .forEach(objects::add);
            }
            return objects;
        };

        List<AbstractControlDescription> ifDescriptions = new ArrayList<>();
        ifDescriptions.add(new EStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, this.semanticTargetIdProvider).getIfDescription());
        ifDescriptions.add(new EBooleanIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, this.semanticTargetIdProvider).getIfDescription());
        ifDescriptions.add(new EEnumIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, this.semanticTargetIdProvider).getIfDescription());

        ifDescriptions.add(new NonDerivedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService, this.semanticTargetIdProvider, this.propertiesValidationProvider,
                this.feedbackMessageService, this.emfKindService).getIfDescription());

        var numericDataTypes = List.of(
                EcorePackage.Literals.EINT,
                EcorePackage.Literals.EINTEGER_OBJECT,
                EcorePackage.Literals.EDOUBLE,
                EcorePackage.Literals.EDOUBLE_OBJECT,
                EcorePackage.Literals.EFLOAT,
                EcorePackage.Literals.EFLOAT_OBJECT,
                EcorePackage.Literals.ELONG,
                EcorePackage.Literals.ELONG_OBJECT,
                EcorePackage.Literals.ESHORT,
                EcorePackage.Literals.ESHORT_OBJECT);
        for (var dataType : numericDataTypes) {
            ifDescriptions.add(new NumberIfDescriptionProvider(dataType, this.composedAdapterFactory, this.propertiesValidationProvider, this.emfMessageService, this.semanticTargetIdProvider)
                    .getIfDescription());
        }

        ForDescription forDescription = ForDescription.newForDescription("forId")
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .iterator(ESTRUCTURAL_FEATURE)
                .iterableProvider(iterableProvider)
                .controlDescriptions(ifDescriptions)
                .build();

        controlDescriptions.add(forDescription);

        return GroupDescription.newGroupDescription("groupId")
                .idProvider(variableManager -> "Core Properties")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
    }

}
