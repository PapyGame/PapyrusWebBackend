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
package org.eclipse.papyrus.web.application.utils.mutations;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * Service used to edit a label.
 * <p>
 * This class instantiates and runs the {@code editLabel} mutation. Note that the mutation takes a graphical label
 * identifier as input, but performs both graphical and semantic label edition.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class EditLabelMutationRunner {

    private static String query = """
                        mutation editLabel($input: EditLabelInput!) {
              editLabel(input: $input) {
                __typename
                ... on EditLabelSuccessPayload {
                  diagram {
                    id
                    __typename
                  }
                  __typename
                }
                ... on ErrorPayload {
                  message
                  __typename
                }
              }
            }
                        """;

    private GraphQL graphQL;

    private ObjectMapper objectMapper;

    /**
     * Initializes the runner with the provided {@code graphQL} and {@code objectMapper}.
     *
     * @param graphQL
     *            the GraphQL execution engine
     * @param objectMapper
     *            the object mapper
     */
    public EditLabelMutationRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    /**
     * Sets the text of text of the {@code labelId} label with the provided {@code newLabel}.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the label to edit
     * @param representationId
     *            the representation containing the label
     * @param labelId
     *            the identifier of the label to edit
     * @param newLabel
     *            the new value to set for the edited label
     */
    public void editLabel(String projectId, String representationId, String labelId, String newLabel) {
        EditLabelInput editLabelInput = new EditLabelInput(UUID.randomUUID(), projectId, representationId, labelId, newLabel);
        ExecutionInput executionInput = ExecutionInput.newExecutionInput(query)//
                .variables(Map.of("input", this.objectMapper.convertValue(editLabelInput, new TypeReference<Map<String, Object>>() { /**/ }))) //
                .build();
        ExecutionResult executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();
    }

}
