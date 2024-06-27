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
package org.eclipse.papyrus.web.services.aqlservices.composite;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.create.ElementBasedEdgeCreator;
import org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer;
import org.eclipse.papyrus.uml.domain.services.create.ICreator;
import org.eclipse.papyrus.uml.domain.services.create.IDomainBasedEdgeCreator;
import org.eclipse.papyrus.uml.domain.services.create.diagrams.CompositeStructureDiagramElementCreator;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeInitializer;
import org.eclipse.papyrus.uml.domain.services.edges.diagrams.CompositeStructureDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.ViewHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramElementHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * Service for the "Composite Structure" diagram.
 *
 * @author Arthur Daussy
 */
public class CompositeStructureDiagramService extends AbstractDiagramService {

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    public CompositeStructureDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService,
            IEditableChecker editableChecker, IViewDiagramDescriptionService viewDiagramService, ILogger logger) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService, logger);
        this.logger = logger;
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildSemanticDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        IWebExternalSourceToRepresentationDropBehaviorProvider dropProvider = new CompositeStructureSemanticDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                this.getECrossReferenceAdapter(semanticDroppedElement), this.getEditableChecker(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
        return dropProvider;
    }

    @Override
    protected IDomainBasedEdgeCreator buildDomainBasedEdgeCreator(EObject source) {
        ElementBasedEdgeCreator baseEdgeCreator = new ElementBasedEdgeCreator(//
                CompositeStructureDomainBasedEdgeContainerProvider.buildDefault(this.getEditableChecker()), //
                new ElementDomainBasedEdgeInitializer(), //
                new ElementConfigurer(), //
                new ElementFeatureModifier(this.getECrossReferenceAdapter(source), this.getEditableChecker()));
        return baseEdgeCreator;
    }

    /**
     * Connector use both {@link ConnectorEnd#getRole()} and {@link ConnectorEnd#getPartWithPort()} information to pick
     * a source and a target. This method prevents displaying invalid connectors.
     *
     * @param semanticSource
     *            the semantic source of the edge
     * @param semanticTarget
     *            the semantic target of the edge
     * @param visualSource
     *            the visual source of the edge candidate
     * @param visualTarget
     *            the visual target of the edge candidate
     * @param cache
     *            the {@link DiagramRenderingCache}
     * @return <code>true</code> if the edge should be displayed, <code>false</code> otherwise
     */
    public boolean shouldDisplayConnector(Connector connector, EObject semanticSource, EObject semanticTarget, Element visualSource, Element visualTarget, DiagramRenderingCache cache,
            IEditingContext editingContext) {

        // compute sourceEnd
        ConnectorEnd sourceEnd = connector.getEnds().get(0);
        ConnectorEnd targetEnd = connector.getEnds().get(1);
        boolean matchPartWithPort = this.matchPartWithPort(sourceEnd, visualSource, cache, editingContext) && this.matchPartWithPort(targetEnd, visualTarget, cache, editingContext);

        if (matchPartWithPort) {
            return this.getCommonVisualAncestor(visualSource, visualTarget, cache, editingContext).map(ancestor -> ancestor == connector.eContainer()).orElse(false);
        } else {
            return false;
        }
    }

    private Optional<Object> getCommonVisualAncestor(Element visualSource, Element visualTarget, DiagramRenderingCache cache, IEditingContext editinContext) {
        return new DiagramElementHelper(visualSource).getCommonAncestor(new DiagramElementHelper(visualTarget), cache)//
                .filter(element -> element.getId().isPresent())//
                .flatMap(ancestor -> ancestor.getElementTarget(this.getObjectService(), editinContext));
    }

    /**
     * Check that the connector end match the given visual element. That is to say that the target role is matched and
     * the {@link ConnectorEnd#getPartWithPort()} feature also matches.
     *
     * @param end
     *            the given {@link ConnectorEnd}
     * @param visualElement
     *            the visual element to match
     * @param cache
     *            the {@link DiagramRenderingCache}
     * @param editingContext
     *            the {@link EditingContext}
     * @return <code>true</code> if the visual element matches the given {@link ConnectorEnd}
     */
    private boolean matchPartWithPort(ConnectorEnd end, Element visualElement, DiagramRenderingCache cache, IEditingContext editingContext) {
        Property partWithPortSource = end.getPartWithPort();
        boolean shouldDiplayConnector = true;
        if (partWithPortSource != null) {
            // connector source is a Port on a Property
            DiagramElementHelper visualSourceHelper = new DiagramElementHelper(visualElement);
            Optional<Object> target = visualSourceHelper.getElementTarget(this.getObjectService(), editingContext);

            if (target.isPresent() && target.get() instanceof Port) {
                shouldDiplayConnector = visualSourceHelper.getParent(cache)//
                        .flatMap(parent -> parent.getElementTarget(this.getObjectService(), editingContext))//
                        .map(sem -> sem == partWithPortSource).orElse(false);
            } else {
                shouldDiplayConnector = false;
            }
        }
        return shouldDiplayConnector;
    }

    @Override
    protected ICreator buildElementCreator(EObject parent) {
        return CompositeStructureDiagramElementCreator.buildDefault(this.getECrossReferenceAdapter(parent), this.getEditableChecker());
    }

}
