/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.services.aqlservices.composite;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CompositeStructureExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CompositeStructureExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.SemanticDropSwitch;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Provides the behavior on a semantic drop event in the "Composite Structure Diagram".
 *
 * @author Arthur Daussy
 */
public class CompositeStructureSemanticDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IEditingContext editionContext;

    private final IViewHelper viewHelper;

    private final IObjectService objectService;

    private final ECrossReferenceAdapter crossRef;

    private final IEditableChecker editableChecker;

    private DiagramNavigator diagramNavigator;

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    public CompositeStructureSemanticDropBehaviorProvider(IEditingContext editionContext, IViewHelper viewHelper, IObjectService objectService, ECrossReferenceAdapter crossRef,
            IEditableChecker editableChecker, DiagramNavigator diagramNavigator, ILogger logger) {
        this.diagramNavigator = Objects.requireNonNull(diagramNavigator);
        this.crossRef = Objects.requireNonNull(crossRef);
        this.editableChecker = Objects.requireNonNull(editableChecker);
        this.editionContext = Objects.requireNonNull(editionContext);
        this.viewHelper = Objects.requireNonNull(viewHelper);
        this.objectService = Objects.requireNonNull(objectService);
        this.logger = logger;
    }

    /**
     * Handles a semantic drop event.
     *
     * @param droppedElement
     *            the dropped element
     * @param targetNode
     *            the target node or <code>null</code> if the drop occurred on the diagram
     */
    @Override
    public void handleSemanticDrop(EObject droppedElement, org.eclipse.sirius.components.diagrams.Node targetNode) {
        Optional<Node> optionalTargetNode = Optional.ofNullable(targetNode);
        new SemanticDropSwitch(optionalTargetNode, this.viewHelper, this.diagramNavigator, this.logger) //
                .withDropChecker(new CompositeStructureExternalSourceToRepresentationDropChecker()) //
                .withDropProvider(new CompositeStructureExternalSourceToRepresentationDropBehaviorProvider()) //
                .withCrossRef(this.crossRef) //
                .withEditableChecker(this.editableChecker) //
                .withEObjectResolver(this::getSemanticObject) //
                .doSwitch(droppedElement);
    }

    private Object getSemanticObject(String id) {
        return this.objectService.getObject(this.editionContext, id).orElse(null);

    }

}
