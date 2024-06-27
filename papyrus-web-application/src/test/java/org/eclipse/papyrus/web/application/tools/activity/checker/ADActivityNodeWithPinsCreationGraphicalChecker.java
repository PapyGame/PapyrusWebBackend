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
package org.eclipse.papyrus.web.application.tools.activity.checker;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.tools.checker.NodeCreationGraphicalChecker;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * A specific graphical checker for {@link ActivityNode}s creation with pins in the Activity diagram.
 * <p>
 * {@link ActivityNode}s can have up to 3 pins. This changes the expected number of created element in the diagram. The
 * number of expected pins is provided in the constructor of this checker.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ADActivityNodeWithPinsCreationGraphicalChecker extends NodeCreationGraphicalChecker {

    private int expectedPincount;

    public ADActivityNodeWithPinsCreationGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, String mappingType,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes, int expectedPinCount) {
        super(diagramSupplier, graphicalOwnerSupplier, mappingType, convertedNodes);
        this.expectedPincount = expectedPinCount;
    }

    @Override
    protected int getExpectedNumberOfCreatedElements() {
        // The number of pins + the created ActivityNode
        return this.expectedPincount + 1;
    }

    @Override
    protected int getExpectedNumberOfGraphicalOwnerDirectChildren() {
        // ActivityNode's pins aren't direct children of the ActivityNode's graphical owner
        return 1;
    }
}
