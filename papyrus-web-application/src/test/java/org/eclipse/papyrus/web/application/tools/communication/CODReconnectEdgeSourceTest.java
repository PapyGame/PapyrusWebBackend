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
package org.eclipse.papyrus.web.application.tools.communication;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.uml.CODDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.Checker;
import org.eclipse.papyrus.web.application.tools.checker.EdgeSourceGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.test.ReconnectEdgeSourceTest;
import org.eclipse.papyrus.web.application.tools.utils.CreationTool;
import org.eclipse.papyrus.web.application.tools.utils.ToolSections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests reconnect edge source tools in the Communication Diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CODReconnectEdgeSourceTest extends ReconnectEdgeSourceTest {

    private static final String ROOT_INTERACTION = "rootInteraction";

    private static final String LIFELINE_EDGE_SOURCE = "Lifeline" + OLD_SOURCE;

    private static final String LIFELINE_EDGE_TARGET = "Lifeline" + TARGET;

    public CODReconnectEdgeSourceTest() {
        super(DEFAULT_DOCUMENT, CODDiagramDescriptionBuilder.COD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> messageParameters() {
        return Stream.of(//
                Arguments.of(UML.getLifeline()));
    }

    @Override
    @BeforeEach
    public void setUp() {
        this.setUpWithIntermediateRoot(ROOT_INTERACTION, UML.getInteraction());
        this.createSourceAndTargetNodes(this.getDiagram().getNodes().get(0).getId(), new CreationTool(ToolSections.NODES, UML.getLifeline()));
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @ParameterizedTest
    @MethodSource("messageParameters")
    public void testReconnectMessageSource(EClass newSourceType) {
        String messageId = this.createEdge(LIFELINE_EDGE_SOURCE, LIFELINE_EDGE_TARGET, new CreationTool(ToolSections.EDGES, UML.getMessage()));
        String newSourceLabel = newSourceType.getName() + NEW_SOURCE;
        Checker graphicalChecker = new EdgeSourceGraphicalChecker(() -> this.findGraphicalElementByLabel(newSourceLabel));
        this.reconnectEdgeSource(messageId, newSourceLabel, graphicalChecker);
    }
}
