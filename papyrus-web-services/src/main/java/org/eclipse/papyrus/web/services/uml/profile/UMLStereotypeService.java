/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.services.uml.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.web.services.api.uml.profile.IUMLStereotypeService;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLStereotypeMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service used to manage UML stereotypes.
 *
 * @author lfasani
 */
@Service
public class UMLStereotypeService implements IUMLStereotypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UMLProfileService.class);

    private final IObjectService objectService;

    public UMLStereotypeService(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public List<UMLStereotypeMetadata> getApplicableStereotypeOn(IEditingContext editingContext, String elementUMLId) {
        List<UMLStereotypeMetadata> stereotypeMetadatas = this.objectService.getObject(editingContext, elementUMLId)
                .filter(Element.class::isInstance)
                .map(Element.class::cast)
                .map(this::collectUnappliedStereotypes)
                .orElse(Collections.emptyList());
        return stereotypeMetadatas;
    }

    private List<UMLStereotypeMetadata> collectUnappliedStereotypes(Element element) {
        List<UMLStereotypeMetadata> result = new ArrayList<>();
        EList<Stereotype> appliedStereotypes = element.getAppliedStereotypes();
        for (Stereotype stereotype : element.getApplicableStereotypes()) {
            if (!appliedStereotypes.contains(stereotype)) {
                String id = this.objectService.getId(stereotype);
                if (id != null) {
                    result.add(new UMLStereotypeMetadata(this.buildStereotypeQualifiedName(stereotype), id));
                }
            }
        }
        return result;
    }

    private String buildStereotypeQualifiedName(Stereotype stereotype) {
        return stereotype.getProfile().getName() + "::" + stereotype.getLabel();
    }

    @Override
    public Optional<Object> applyStereotype(IEditingContext editingContext, String elementUMLId, String stereotypeId) {
        Optional<Object> stereotypeApplication = Optional.empty();
        Optional<Stereotype> stereotypeOpt = this.objectService.getObject(editingContext, stereotypeId)
                .filter(Stereotype.class::isInstance)
                .map(Stereotype.class::cast);

        if (stereotypeOpt.isPresent()) {
            Optional<Element> elementOpt = this.objectService.getObject(editingContext, elementUMLId).filter(Element.class::isInstance).map(Element.class::cast);

            if (elementOpt.isPresent()) {
                stereotypeApplication = Optional.ofNullable(elementOpt.get().applyStereotype(stereotypeOpt.get()));
            } else {
                LOGGER.warn("The element of id {0} has not been found", elementUMLId);
            }
        } else {
            LOGGER.warn("The stereotype of id {0} has not been found", stereotypeId);
        }

        return stereotypeApplication;
    }
}
