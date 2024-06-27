/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.sirius.contributions;

/**
 * Severity.
 *
 * @author Arthur Daussy
 */
public enum Severity {

    /**
     * OK.
     */
    OK,
    /**
     * OK but I want to report something.
     */
    INFO,
    /**
     * Warning.
     */
    WARNING,
    /**
     * Error.
     */
    ERROR
}
