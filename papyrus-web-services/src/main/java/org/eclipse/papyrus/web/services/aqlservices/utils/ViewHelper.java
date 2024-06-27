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
package org.eclipse.papyrus.web.services.aqlservices.utils;

import static java.util.stream.Collectors.toList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.representations.uml.AbstractRepresentationDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.sirius.contributions.FactoryMethod;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.components.NodeIdProvider;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A helper to manage views (creation, deletion...) in a diagram define in a {@link DiagramDescription}.
 *
 * @author Arthur Daussy
 */
public class ViewHelper implements IViewHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewHelper.class);

    private final IObjectService objectService;

    private final IDiagramOperationsService diagramOperationsService;

    private final IDiagramContext diagramContext;

    private final DiagramDescription diagramDescription;

    private final Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions;

    public ViewHelper(IObjectService objectService, IDiagramOperationsService diagramOperationsService, IDiagramContext diagramContext, DiagramDescription diagramDescription,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        super();
        this.objectService = objectService;
        this.diagramOperationsService = Objects.requireNonNull(diagramOperationsService);
        this.diagramContext = diagramContext;
        this.diagramDescription = diagramDescription;
        this.capturedNodeDescriptions = capturedNodeDescriptions;
    }

    /**
     * Creates a new {@link IViewHelper}.
     *
     * <p>
     * If the capturedNodeDescriptions is empty then return a NoOp implementation
     * </p>
     *
     * @param objectService
     *            the {@link IObjectService}
     * @param viewDiagramService
     *            the {@link IDiagramNavigationService}
     * @param diagramOperationsService
     *            the {@link IDiagramOperationsService}
     * @param diagramContext
     *            the {@link IDiagramContext}
     * @param capturedNodeDescriptions
     *            a map that contains all mapping between {@link org.eclipse.sirius.components.view.NodeDescription} and
     *            {@link NodeDescription} for the current diagram
     * @return a new instance
     */
    @FactoryMethod
    public static IViewHelper create(IObjectService objectService, IViewDiagramDescriptionService viewDiagramService, IDiagramOperationsService diagramOperationsService,
            IDiagramContext diagramContext, Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        return viewDiagramService.getDiagramDescription(capturedNodeDescriptions)
                .map(dd -> (IViewHelper) new ViewHelper(objectService, diagramOperationsService, diagramContext, dd, capturedNodeDescriptions))
                .orElse(new IViewHelper.NoOp());

    }

    @Override
    public boolean createChildView(EObject self, org.eclipse.sirius.components.diagrams.Node selectedNode) {
        return this.createChildView(self, selectedNode, null);
    }

    @Override
    public boolean createChildView(EObject self, Node selectedNode, String mappingName) {
        boolean result = false;
        org.eclipse.sirius.components.view.diagram.NodeDescription targetNodeDescription = this.getViewNodeDescription(selectedNode.getDescriptionId()).orElse(null);
        if (targetNodeDescription != null) {
            final org.eclipse.sirius.components.view.diagram.NodeDescription childrenType;
            if (mappingName == null) {
                childrenType = this.getChildrenNodeDescriptionsOfType(targetNodeDescription, self.eClass());
            } else {
                childrenType = this.getChildrenNodeDescriptionsWithName(targetNodeDescription, mappingName);
            }
            if (childrenType != null) {
                if (!IdBuilder.isFakeChildNode(
                        childrenType)) { /*
                                          * Workaround for https://github.com/PapyrusSirius/papyrus-web/issues/164
                                          */
                    result = this.createView(self, selectedNode, childrenType);
                }
            } else {
                LOGGER.warn("No view description with name {0}", mappingName);
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean createRootView(EObject self) {
        return this.createRootView(self, null);
    }

    @Override
    public boolean createRootView(EObject self, String mappingName) {
        boolean result = false;
        final org.eclipse.sirius.components.view.diagram.NodeDescription childrenType;
        if (mappingName == null) {
            childrenType = this.getChildrenNodeDescriptionsOfType(null, self.eClass());
        } else {
            childrenType = this.getChildrenNodeDescriptionsWithName(null, mappingName);
        }
        if (childrenType != null) {
            result = this.createView(self, null, childrenType);
        } else {
            LOGGER.warn(MessageFormat.format("No root view description with name {0}", mappingName));
            result = false;
        }
        return result;
    }

    @Override
    public boolean createView(EObject semanticElement, Node selectedNode, org.eclipse.sirius.components.view.diagram.NodeDescription newViewDescription) {
        if (newViewDescription != null) {

            var isBorderedNode = newViewDescription.eContainingFeature() == DiagramPackage.eINSTANCE.getNodeDescription_BorderNodesDescriptions();
            final NodeContainmentKind containmentKind;
            if (isBorderedNode) {
                containmentKind = NodeContainmentKind.BORDER_NODE;
            } else {
                containmentKind = NodeContainmentKind.CHILD_NODE;
            }

            // Need to check that no other view on this element is already created
            NodeDescription nodeDescription = this.capturedNodeDescriptions.get(newViewDescription);
            String nodeDescriptionId = nodeDescription.getId();
            String semanticId = this.objectService.getId(semanticElement);

            // Workaround to avoid java.lang.IllegalStateException: Duplicate key problem -
            // https://github.com/eclipse-sirius/sirius-components/issues/1317
            List<Node> matchingNodes = this.getAllNode(this.diagramContext.getDiagram(), (parent, node) -> this.matchExistingNode(parent, node, semanticId, nodeDescriptionId, selectedNode));

            if (semanticId == null || matchingNodes.isEmpty()) {

                this.diagramOperationsService.createView(this.diagramContext, semanticElement, Optional.ofNullable(selectedNode), nodeDescription, containmentKind);
                return true;
            } else {
                LOGGER.warn("A representation of this element alredy exist in the diagram");
            }

        }
        return false;
    }

    @Override
    public boolean deleteView(Node droppedNode) {
        this.diagramOperationsService.deleteView(this.diagramContext, droppedNode);
        return true;
    }

    @Override
    public Node createFakeNode(EObject semanticElement, Node selectedNode) {
        String targetObjectId = this.objectService.getId(semanticElement);
        String parentElementId = null;
        org.eclipse.sirius.components.view.diagram.NodeDescription childrenType = null;
        if (selectedNode == null) {
            // case diagram
            parentElementId = this.diagramContext.getDiagram().getId();
            childrenType = this.getChildrenNodeDescriptionsOfType(null, semanticElement.eClass());
        } else {
            parentElementId = selectedNode.getId();
            org.eclipse.sirius.components.view.diagram.NodeDescription targetNodeDescription = this.getViewNodeDescription(selectedNode.getDescriptionId()).orElse(null);
            childrenType = this.getChildrenNodeDescriptionsOfType(targetNodeDescription, semanticElement.eClass());
        }

        var isBorderedNode = childrenType.eContainingFeature() == DiagramPackage.eINSTANCE.getNodeDescription_BorderNodesDescriptions();
        final NodeContainmentKind containmentKind;
        if (isBorderedNode) {
            containmentKind = NodeContainmentKind.BORDER_NODE;
        } else {
            containmentKind = NodeContainmentKind.CHILD_NODE;
        }

        org.eclipse.sirius.components.diagrams.description.NodeDescription nodeDescription = this.capturedNodeDescriptions.get(childrenType);

        var targetObjectKind = this.objectService.getKind(semanticElement);
        var targetObjectLabel = this.objectService.getLabel(semanticElement);
        String nodeId = new NodeIdProvider().getNodeId(parentElementId, nodeDescription.getId().toString(), containmentKind, targetObjectId);

        var labelStyle = LabelStyle.newLabelStyle().color("").fontSize(14).iconURL(List.of()).build();

        var insideLabel = InsideLabel.newLabel("").type("").text("").insideLabelLocation(InsideLabelLocation.TOP_CENTER).position(Position.UNDEFINED).size(Size.UNDEFINED).alignment(Position.UNDEFINED)
                .style(labelStyle).isHeader(false).build();

        var nodeStyle = RectangularNodeStyle.newRectangularNodeStyle().color("").borderColor("").borderStyle(LineStyle.Solid).build();

        return Node.newNode(nodeId)//
                .type("")//
                .targetObjectId(targetObjectId)//
                .targetObjectKind(targetObjectKind)//
                .targetObjectLabel(targetObjectLabel)//
                .descriptionId(nodeDescription.getId())//
                .borderNode(containmentKind == NodeContainmentKind.BORDER_NODE)//
                .modifiers(Set.of())//
                .state(ViewModifier.Normal)//
                .collapsingState(CollapsingState.EXPANDED)//
                .insideLabel(insideLabel)//
                .style(nodeStyle)//
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())//
                .position(Position.UNDEFINED)//
                .size(Size.UNDEFINED)//
                .userResizable(true)//
                .borderNodes(List.of())//
                .childNodes(List.of())//
                .customizedProperties(Set.of())//
                .build();
    }

    private boolean matchExistingNode(Node inspectedParent, Node inspectedNode, String searchedSemanticElementID, String searchNodeDescription, Node selectedParent) {
        boolean parentCheck;
        if (selectedParent == null) {
            parentCheck = inspectedParent == null;
        } else {
            String parentId = selectedParent.getId();
            parentCheck = parentId != null && inspectedParent != null && parentId.equals(inspectedParent.getId());
        }
        return parentCheck && searchedSemanticElementID.equals(inspectedNode.getTargetObjectId()) && inspectedNode.getDescriptionId().equals(searchNodeDescription);
    }

    private List<Node> getAllNode(Diagram diagram, BiPredicate<Node, Node> filter) {
        Set<Node> visitedNode = new HashSet<>();
        List<Node> nodes = new ArrayList<>();
        for (Node c : diagram.getNodes()) {
            this.getAllNode(null, c, visitedNode, nodes, filter);
        }

        return nodes;

    }

    private void getAllNode(Node parent, Node node, Set<Node> visitedNode, List<Node> collector, BiPredicate<Node, Node> filter) {
        if (!visitedNode.contains(node)) {
            if (filter.test(parent, node)) {
                collector.add(node);
            }
            for (Node child : node.getChildNodes()) {
                this.getAllNode(node, child, visitedNode, collector, filter);
            }
        }
    }

    private org.eclipse.sirius.components.view.diagram.NodeDescription getChildrenNodeDescriptionsOfType(org.eclipse.sirius.components.view.diagram.NodeDescription parent, EClass eClass) {

        final List<org.eclipse.sirius.components.view.diagram.NodeDescription> descriptions = new ArrayList<>();
        final String parentName;
        if (parent == null) {
            parentName = this.diagramDescription.getName();
            descriptions.addAll(this.diagramDescription.getNodeDescriptions());
        } else {
            parentName = parent.getName();
            descriptions.addAll(parent.getChildrenDescriptions());
            descriptions.addAll(parent.getBorderNodesDescriptions());
            descriptions.addAll(parent.getReusedBorderNodeDescriptions());
            descriptions.addAll(parent.getReusedChildNodeDescriptions());

        }

        List<org.eclipse.sirius.components.view.diagram.NodeDescription> candidates = descriptions.stream()//
                .distinct()//
                .filter(c -> this.isCompliant(UMLHelper.toEClass(c.getDomainType()), eClass))//
                .filter(c -> !AbstractRepresentationDescriptionBuilder.SHARED_DESCRIPTIONS.equals(c.getName())) //
                // We want to keep the more specialized description type first
                .sorted(Comparator.comparingInt(n -> -1 * this.computeDistanceToElement(UMLHelper.toEClass(n.getDomainType())))).collect(toList());
        Optional<org.eclipse.sirius.components.view.diagram.NodeDescription> perfectCandidate = candidates.stream().filter(c -> UMLHelper.toEClass(c.getDomainType()) == eClass).findFirst();
        if (candidates.isEmpty()) {
            LOGGER.error(MessageFormat.format("No candidate for children of type {0} on {1}", eClass.getName(), parentName));
            return null;
        } else {
            org.eclipse.sirius.components.view.diagram.NodeDescription byDefault = null;
            if (perfectCandidate.isPresent()) {
                byDefault = perfectCandidate.get();
            } else {
                byDefault = candidates.get(0);
            }
            if (candidates.size() > 1) {
                LOGGER.info(
                        MessageFormat.format("More than one candidate for children of type {0} on {1}. By default use the more specific type {2}", eClass.getName(), parentName, byDefault.getName()));
            }
            return byDefault;

        }
    }

    private org.eclipse.sirius.components.view.diagram.NodeDescription getChildrenNodeDescriptionsWithName(org.eclipse.sirius.components.view.diagram.NodeDescription parent, String mappingName) {
        final List<org.eclipse.sirius.components.view.diagram.NodeDescription> descriptions = new ArrayList<>();
        final String parentName;
        if (parent == null) {
            parentName = this.diagramDescription.getName();
            descriptions.addAll(this.diagramDescription.getNodeDescriptions());
        } else {
            parentName = parent.getName();
            descriptions.addAll(parent.getChildrenDescriptions());
            descriptions.addAll(parent.getBorderNodesDescriptions());
            descriptions.addAll(parent.getReusedBorderNodeDescriptions());
            descriptions.addAll(parent.getReusedChildNodeDescriptions());

        }

        List<org.eclipse.sirius.components.view.diagram.NodeDescription> candidates = descriptions.stream()//
                .distinct()//
                .filter(c -> c.getName().equals(mappingName)).toList();
        if (candidates.isEmpty()) {
            LOGGER.error(MessageFormat.format("No candidate for children with mapping name {0} on {1}", mappingName, parentName));
            return null;
        } else {
            org.eclipse.sirius.components.view.diagram.NodeDescription byDefault = candidates.get(0);
            if (candidates.size() > 1) {
                LOGGER.info(MessageFormat.format("More than one candidate for children with mapping name {0} on {1}. By default use the first one", mappingName, parentName));
            }
            return byDefault;
        }
    }

    private int computeDistanceToElement(EClassifier source) {
        return this.computeDistanceToElement(source, 0);
    }

    private int computeDistanceToElement(EClassifier source, int current) {
        if (source == UMLPackage.eINSTANCE.getElement()) {
            return current;
        } else {
            int distance = Integer.MAX_VALUE;
            if (source instanceof EClass) {
                EClass sourceEClass = (EClass) source;
                for (EClass superType : sourceEClass.getESuperTypes()) {
                    distance = Math.min(distance, this.computeDistanceToElement(superType, current + 1));
                }

            }
            return distance;

        }
    }

    private boolean isCompliant(EClassifier expected, EClass toTest) {
        return toTest == expected || toTest.getEAllSuperTypes().contains(expected);
    }

    private Optional<org.eclipse.sirius.components.view.diagram.NodeDescription> getViewNodeDescription(String descriptionId) {
        return EMFUtils.allContainedObjectOfType(this.diagramDescription, org.eclipse.sirius.components.view.diagram.NodeDescription.class).filter(n -> {
            NodeDescription nd = this.capturedNodeDescriptions.get(n);
            return nd != null && descriptionId.equals(nd.getId());
        }).findFirst();

    }

}
