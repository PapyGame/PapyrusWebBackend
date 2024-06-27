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
package org.eclipse.papyrus.web.services.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Service in charge of providing a way to override some image in the {@link IObjectService#getImagePath(Object)}.
 *
 * @author Arthur Daussy
 */
public interface IImageOverrideService {

    /**
     * Get an optional image to override the given onne.
     *
     * @param baseImagePath
     *            the base EMF icon URL.
     * @return an optional new path
     */
    Optional<String> getOverrideImage(String baseImagePath);

}
