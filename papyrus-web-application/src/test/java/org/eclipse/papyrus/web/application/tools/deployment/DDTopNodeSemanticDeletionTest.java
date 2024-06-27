/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.tools.deployment;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.DDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.CombinedChecker;
import org.eclipse.papyrus.web.application.tools.checker.DeletionGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.checker.NodeSemanticDeletionSemanticChecker;
import org.eclipse.papyrus.web.application.tools.test.NodeDeletionTest;
import org.eclipse.papyrus.web.application.tools.utils.CreationTool;
import org.eclipse.papyrus.web.application.tools.utils.ToolSections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests semantic deletion node tool at the root of the diagram in the Deployment Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class DDTopNodeSemanticDeletionTest extends NodeDeletionTest {

    private static final String ARTIFACT1 = "Artifact1";

    private static final String CONSTRAINT1 = "Constraint1";

    private static final String DEPLOYMENT_SPECIFICATION1 = "DeploymentSpecification1";

    private static final String DEVICE1 = "Device1";

    private static final String EXECUTION_ENVIRONMENT1 = "ExecutionEnvironment1";

    private static final String MODEL1 = "Model1";

    private static final String NODE1 = "Node1";

    private static final String PACKAGE1 = "Package1";

    public DDTopNodeSemanticDeletionTest() {
        super(DEFAULT_DOCUMENT, DDDiagramDescriptionBuilder.DD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> parameterProvider() {
        return Stream.of(//
                Arguments.of(ARTIFACT1, UML.getPackage_PackagedElement()), //
                Arguments.of(CONSTRAINT1, UML.getNamespace_OwnedRule()), //
                Arguments.of(DEPLOYMENT_SPECIFICATION1, UML.getPackage_PackagedElement()), //
                Arguments.of(DEVICE1, UML.getPackage_PackagedElement()), //
                Arguments.of(EXECUTION_ENVIRONMENT1, UML.getPackage_PackagedElement()), //
                Arguments.of(MODEL1, UML.getPackage_PackagedElement()), //
                Arguments.of(NODE1, UML.getPackage_PackagedElement()), //
                Arguments.of(PACKAGE1, UML.getPackage_PackagedElement()) //
        );
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getArtifact()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getConstraint()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getDeploymentSpecification()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getDevice()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getExecutionEnvironment()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getModel()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getNode()));
        this.applyNodeCreationTool(this.representationId, new CreationTool(ToolSections.NODES, UML.getPackage()));
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    public void testDeleteSemanticNode(String elementName, EReference containmentReference) {
        DeletionGraphicalChecker graphicalChecker = new DeletionGraphicalChecker(this::getDiagram, null);
        NodeSemanticDeletionSemanticChecker semanticChecker = new NodeSemanticDeletionSemanticChecker(this.getObjectService(), this::getEditingContext, this::getRootSemanticElement,
                containmentReference);
        this.deleteSemanticNode(elementName, new CombinedChecker(graphicalChecker, semanticChecker));
    }
}
