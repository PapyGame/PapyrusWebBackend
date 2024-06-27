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

package org.eclipse.papyrus.web.application.properties.pages;

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.ContainmentReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ManifestationUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ManifestationUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createManifestationUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("manifestation_uml_page", "uml::Manifestation", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createManifestationUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("manifestation_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addMapping(group);
        addUtilizedElement(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addMapping(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("mapping") //
                .label("aql:'Mapping'") //
                .help("aql:self.getFeatureDescription('mapping')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('mapping').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('mapping')") //
                .isMany(false) //
                .value("feature:mapping") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'mapping'))");
        group.getChildren().add(builder.build());
    }

    protected void addUtilizedElement(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("utilizedElement") //
                .label("aql:'Utilized element'") //
                .help("aql:self.getFeatureDescription('utilizedElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('utilizedElement').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('utilizedElement')") //
                .value("feature:utilizedElement") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('utilizedElement')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'utilizedElement')") //
                .unsetOperation("aql:item.delete(self, 'utilizedElement'))") //
                .clearOperation("aql:self.clearReference('utilizedElement')"); //
        group.getChildren().add(builder.build());
    }

}
