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
package org.eclipse.papyrus.web.services.api.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the {@code createMetaclassImport} mutation.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public final class CreateMetaclassImportSuccessPayload implements IPayload {

    private final UUID id;

    public CreateMetaclassImportSuccessPayload(UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public UUID id() {
        return this.id;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

}