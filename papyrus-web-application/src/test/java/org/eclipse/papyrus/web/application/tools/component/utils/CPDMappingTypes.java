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
package org.eclipse.papyrus.web.application.tools.component.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The mapping types for the Component Diagram.
 * <p>
 * This class can be used to retrieve both node mapping types and edge mapping types.
 * </p>
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public final class CPDMappingTypes {

    private static final String CPD_PREFIX = "CPD_";

    private CPDMappingTypes() {
        // private to prevent instantiation.
    }

    public static String getMappingType(EClass eClass) {
        String result = CPD_PREFIX + eClass.getName();
        if (UMLPackage.eINSTANCE.getRelationship().isSuperTypeOf(eClass)) {
            result = CPD_PREFIX + eClass.getName() + "_DomainEdge";
        }
        return result;
    }

    public static String getMappingTypeAsSubNode(EClass eClass) {
        String result = CPD_PREFIX + eClass.getName() + "_SHARED";
        if (UMLPackage.eINSTANCE.getOperation().isSuperTypeOf(eClass) || UMLPackage.eINSTANCE.getReception().isSuperTypeOf(eClass)) {
            // Operations/Receptions aren't suffixed with "_SHARED" even if they are sub-nodes, because they aren't
            // shared
            result = getMappingType(eClass);
        }
        return result;
    }
}
