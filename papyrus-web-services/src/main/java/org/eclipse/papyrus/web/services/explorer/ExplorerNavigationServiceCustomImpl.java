/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.services.explorer.ExplorerNavigationService;
import org.eclipse.sirius.web.services.explorer.api.IExplorerNavigationService;

/**
 * Custom implementation of the {@link ExplorerNavigationService} that works with non-UUIDs.
 * <p>
 * This class is a temporary fix for <a href="https://github.com/PapyrusSirius/papyrus-web/issues/300">bug #300</a>. It
 * should be removed once the issue is resolved.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@ServiceOverride(ExplorerNavigationService.class)
public class ExplorerNavigationServiceCustomImpl implements IExplorerNavigationService {

    private final IObjectService objectService;

    private final IRepresentationService representationService;

    public ExplorerNavigationServiceCustomImpl(IObjectService objectService, IRepresentationService representationService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationService = Objects.requireNonNull(representationService);
    }

    @Override
    public List<String> getAncestors(IEditingContext editingContext, String selectionEntryId) {
        List<String> ancestorsIds = new ArrayList<>();

        var optionalSemanticObject = this.objectService.getObject(editingContext, selectionEntryId);

        Optional<Object> optionalObject = Optional.empty();
        if (optionalSemanticObject.isPresent()) {
            Optional<RepresentationDescriptor> optionalRepresentation;
            try {
                optionalRepresentation = this.representationService.getRepresentation(UUID.fromString(selectionEntryId));
            } catch (IllegalArgumentException e) {
                // The provided selectionEntryId is not a valid UUID, we can't find a representation for it
                optionalRepresentation = Optional.empty();
            }
            if (optionalRepresentation.isPresent()) {
                // The first parent of a representation item is the item for its targetObject.
                optionalObject = optionalRepresentation.map(RepresentationDescriptor::getTargetObjectId).flatMap(objectId -> this.objectService.getObject(editingContext, objectId));
            } else {
                // The first parent of a semantic object item is the item for its actual container
                optionalObject = optionalSemanticObject.filter(EObject.class::isInstance).map(EObject.class::cast)
                        .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
            }
        }

        while (optionalObject.isPresent()) {
            ancestorsIds.add(this.getItemId(optionalObject.get()));
            optionalObject = optionalObject //
                    .filter(EObject.class::isInstance) //
                    .map(EObject.class::cast) //
                    .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
        }
        return ancestorsIds;
    }

    private String getItemId(Object object) {
        String result = null;
        if (object instanceof Resource) {
            Resource resource = (Resource) object;
            result = resource.getURI().path().substring(1);
        } else if (object instanceof EObject) {
            result = this.objectService.getId(object);
        }
        return result;
    }

}
