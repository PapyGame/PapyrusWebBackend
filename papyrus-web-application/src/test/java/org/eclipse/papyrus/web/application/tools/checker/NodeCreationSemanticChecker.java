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
package org.eclipse.papyrus.web.application.tools.checker;

import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Utility class to check that a semantic node has been created in the semantic model.
 * <p>
 * This checker validates that the expected semantic node has been created in the expected semantic container. It also
 * validates that the semantic model contains the appropriate number of elements after the creation.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class NodeCreationSemanticChecker extends CreationSemanticChecker {

    /**
     * Initializes the checker with the provided parameters.
     *
     * @param objectService
     *            the object service used to retrieve and compute identifiers
     * @param editingContextSupplier
     *            a supplier to access and reload the editing context
     * @param expectedType
     *            the expected type of the checked node
     * @param expectedOwnerSupplier
     *            a supplier to access and reload the expected semantic owner of the checked node
     * @param containmentFeature
     *            the expected containment feature of the checked node
     */
    public NodeCreationSemanticChecker(IObjectService objectService, Supplier<IEditingContext> editingContextSupplier, EClass expectedType, Supplier<EObject> expectedOwnerSupplier,
            EReference containmentFeature) {
        super(objectService, editingContextSupplier, expectedType, expectedOwnerSupplier, containmentFeature);
    }
}
