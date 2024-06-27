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

public class StateUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public StateUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createStateUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("state_uml_page", "uml::State", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::FinalState)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createStateUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("state_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addStateInvariant(group);
        addEntry(group);
        addDoActivity(group);
        addExit(group);
        addSubmachine(group);
        addDeferrableTrigger(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addStateInvariant(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("stateInvariant") //
                .label("aql:'State invariant'") //
                .help("aql:self.getFeatureDescription('stateInvariant')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('stateInvariant').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('stateInvariant')") //
                .value("feature:stateInvariant") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('stateInvariant')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'stateInvariant')") //
                .unsetOperation("aql:item.delete(self, 'stateInvariant'))") //
                .clearOperation("aql:self.clearReference('stateInvariant')"); //
        group.getChildren().add(builder.build());
    }

    protected void addEntry(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("entry") //
                .label("aql:'Entry'") //
                .help("aql:self.getFeatureDescription('entry')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('entry').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('entry')") //
                .isMany(false) //
                .value("feature:entry") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'entry'))");
        group.getChildren().add(builder.build());
    }

    protected void addDoActivity(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("doActivity") //
                .label("aql:'Do activity'") //
                .help("aql:self.getFeatureDescription('doActivity')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('doActivity').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('doActivity')") //
                .isMany(false) //
                .value("feature:doActivity") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'doActivity'))");
        group.getChildren().add(builder.build());
    }

    protected void addExit(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("exit") //
                .label("aql:'Exit'") //
                .help("aql:self.getFeatureDescription('exit')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('exit').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('exit')") //
                .isMany(false) //
                .value("feature:exit") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'exit'))");
        group.getChildren().add(builder.build());
    }

    protected void addSubmachine(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("submachine") //
                .label("aql:'Submachine'") //
                .help("aql:self.getFeatureDescription('submachine')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('submachine').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('submachine')") //
                .value("feature:submachine") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('submachine')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'submachine')") //
                .unsetOperation("aql:item.delete(self, 'submachine'))") //
                .clearOperation("aql:self.clearReference('submachine')"); //
        group.getChildren().add(builder.build());
    }

    protected void addDeferrableTrigger(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("deferrableTrigger") //
                .label("aql:'Deferrable trigger'") //
                .help("aql:self.getFeatureDescription('deferrableTrigger')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('deferrableTrigger').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('deferrableTrigger')") //
                .isMany(true) //
                .value("feature:deferrableTrigger") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'deferrableTrigger'))") //
                .reorderOperation("aql:self.moveReferenceElement('deferrableTrigger', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}