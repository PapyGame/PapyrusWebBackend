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
package org.eclipse.papyrus.web.services.aqlservices.clazz;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.ViewHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.springframework.stereotype.Service;

/**
 * Gather all services to be used in the Class Diagram.
 *
 * @author Arthur Daussy
 */
@Service
public class ClassDiagramService extends AbstractDiagramService {

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    public ClassDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService, IEditableChecker editableChecker,
            IViewDiagramDescriptionService viewDiagramService, ILogger logger) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService, logger);
        this.logger = logger;
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildSemanticDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        IWebExternalSourceToRepresentationDropBehaviorProvider dropProvider = new ClassSemanticDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
        return dropProvider;
    }

    // Only required until: https://github.com/PapyrusSirius/papyrus-web/issues/208
    public boolean isAssociationSourceShared(Association association) {
        Property source = this.getAssociationSource(association);
        if (source != null) {
            return source.getAggregation() == AggregationKind.SHARED_LITERAL;
        }
        return false;
    }

    // Only required until: https://github.com/PapyrusSirius/papyrus-web/issues/208
    public boolean isAssociationTargetShared(Association association) {
        Property target = this.getAssociationTarget(association);
        if (target != null) {
            return target.getAggregation() == AggregationKind.SHARED_LITERAL;
        }
        return false;
    }

    // Only required until: https://github.com/PapyrusSirius/papyrus-web/issues/208
    public boolean isAssociationSourceComposite(Association association) {
        Property source = this.getAssociationSource(association);
        if (source != null) {
            return source.getAggregation() == AggregationKind.COMPOSITE_LITERAL;
        }
        return false;
    }

    // Only required until: https://github.com/PapyrusSirius/papyrus-web/issues/208
    public boolean isAssociationTargetComposite(Association association) {
        Property target = this.getAssociationTarget(association);
        if (target != null) {
            return target.getAggregation() == AggregationKind.COMPOSITE_LITERAL;
        }
        return false;
    }

    // Only required until: https://github.com/PapyrusSirius/papyrus-web/issues/208
    public boolean isAssociationSourceNavigable(Association association) {
        Property source = this.getAssociationSource(association);
        if (source != null) {
            return source.isNavigable();
        }
        return false;
    }

    // Only required until: https://github.com/PapyrusSirius/papyrus-web/issues/208
    public boolean isAssociationTargetNavigable(Association association) {
        Property target = this.getAssociationTarget(association);
        if (target != null) {
            return target.isNavigable();
        }
        return false;
    }

    public Property getAssociationSource(Association association) {
        EList<Property> memberEnds = association.getMemberEnds();
        if (!memberEnds.isEmpty()) {
            return memberEnds.get(0);
        }
        return null;
    }

    public Property getAssociationTarget(Association association) {
        EList<Property> memberEnds = association.getMemberEnds();
        if (memberEnds.size() > 1) {
            return memberEnds.get(1);
        }
        return null;
    }

    /**
     * Create a composite {@link Association}.
     *
     * @param source
     *            the semantic source
     * @param target
     *            the semantic target
     * @param sourceNode
     *            the source node
     * @param targetNode
     *            the target node
     * @param editingContext
     *            the current {@link IEditingContext}
     * @param diagramContext
     *            the current {@link IDiagramContext}
     * @return a new Association
     */
    public EObject createCompositeAssociation(EObject source, EObject target, Node sourceNode, Node targetNode, IEditingContext editingContext, IDiagramContext diagramContext) {
        EObject newObject = this.createDomainBasedEdge(source, target, "uml::Association", UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), sourceNode, targetNode, editingContext,
                diagramContext);
        if (newObject instanceof Association) {
            Association association = (Association) newObject;
            Property firstMember = association.getMemberEnds().get(0);

            firstMember.setAggregation(AggregationKind.COMPOSITE_LITERAL);
            firstMember.setLower(0);
            firstMember.setUpper(1);
            this.buildFeatureModifier(source).addValue(source, "ownedAttribute", firstMember);

        }
        return newObject;
    }

    /**
     * Create a shared {@link Association}.
     *
     * @param source
     *            the semantic source
     * @param target
     *            the semantic target
     * @param sourceNode
     *            the source node
     * @param targetNode
     *            the target node
     * @param editingContext
     *            the current {@link IEditingContext}
     * @param diagramContext
     *            the current {@link IDiagramContext}
     * @return a new Association
     */
    public EObject createSharedAssociation(EObject source, EObject target, Node sourceNode, Node targetNode, IEditingContext editingContext, IDiagramContext diagramContext) {
        EObject newObject = this.createDomainBasedEdge(source, target, "uml::Association", UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), sourceNode, targetNode, editingContext,
                diagramContext);
        if (newObject instanceof Association) {
            Association association = (Association) newObject;
            Property firstMember = association.getMemberEnds().get(0);

            firstMember.setAggregation(AggregationKind.SHARED_LITERAL);
            firstMember.setLower(0);
            firstMember.setUpper(1);
            this.buildFeatureModifier(source).addValue(source, "ownedAttribute", firstMember);

        }
        return newObject;
    }

}
