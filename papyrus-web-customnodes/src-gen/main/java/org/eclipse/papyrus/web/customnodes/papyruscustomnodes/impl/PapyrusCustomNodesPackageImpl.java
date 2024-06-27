/**
 * Copyright (c) 2023, 2024 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.CuboidNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.EllipseNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.InnerFlagNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.NoteNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.OuterFlagNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PackageNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesFactory;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesPackage;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.RectangleWithExternalLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class PapyrusCustomNodesPackageImpl extends EPackageImpl implements PapyrusCustomNodesPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass ellipseNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass packageNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass rectangleWithExternalLabelNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass noteNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass innerFlagNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass outerFlagNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass cuboidNodeStyleDescriptionEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private PapyrusCustomNodesPackageImpl() {
        super(eNS_URI, PapyrusCustomNodesFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link PapyrusCustomNodesPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static PapyrusCustomNodesPackage init() {
        if (isInited)
            return (PapyrusCustomNodesPackage) EPackage.Registry.INSTANCE.getEPackage(PapyrusCustomNodesPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredPapyrusCustomNodesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        PapyrusCustomNodesPackageImpl thePapyrusCustomNodesPackage = registeredPapyrusCustomNodesPackage instanceof PapyrusCustomNodesPackageImpl
                ? (PapyrusCustomNodesPackageImpl) registeredPapyrusCustomNodesPackage
                : new PapyrusCustomNodesPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        DiagramPackage.eINSTANCE.eClass();
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        thePapyrusCustomNodesPackage.createPackageContents();

        // Initialize created meta-data
        thePapyrusCustomNodesPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        thePapyrusCustomNodesPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(PapyrusCustomNodesPackage.eNS_URI, thePapyrusCustomNodesPackage);
        return thePapyrusCustomNodesPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEllipseNodeStyleDescription() {
        return this.ellipseNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPackageNodeStyleDescription() {
        return this.packageNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRectangleWithExternalLabelNodeStyleDescription() {
        return this.rectangleWithExternalLabelNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNoteNodeStyleDescription() {
        return this.noteNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInnerFlagNodeStyleDescription() {
        return this.innerFlagNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOuterFlagNodeStyleDescription() {
        return this.outerFlagNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCuboidNodeStyleDescription() {
        return this.cuboidNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PapyrusCustomNodesFactory getPapyrusCustomNodesFactory() {
        return (PapyrusCustomNodesFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.ellipseNodeStyleDescriptionEClass = this.createEClass(ELLIPSE_NODE_STYLE_DESCRIPTION);

        this.packageNodeStyleDescriptionEClass = this.createEClass(PACKAGE_NODE_STYLE_DESCRIPTION);

        this.rectangleWithExternalLabelNodeStyleDescriptionEClass = this.createEClass(RECTANGLE_WITH_EXTERNAL_LABEL_NODE_STYLE_DESCRIPTION);

        this.noteNodeStyleDescriptionEClass = this.createEClass(NOTE_NODE_STYLE_DESCRIPTION);

        this.innerFlagNodeStyleDescriptionEClass = this.createEClass(INNER_FLAG_NODE_STYLE_DESCRIPTION);

        this.outerFlagNodeStyleDescriptionEClass = this.createEClass(OUTER_FLAG_NODE_STYLE_DESCRIPTION);

        this.cuboidNodeStyleDescriptionEClass = this.createEClass(CUBOID_NODE_STYLE_DESCRIPTION);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        DiagramPackage theDiagramPackage = (DiagramPackage) EPackage.Registry.INSTANCE.getEPackage(DiagramPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.ellipseNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());
        this.packageNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());
        this.rectangleWithExternalLabelNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());
        this.noteNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());
        this.innerFlagNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());
        this.outerFlagNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());
        this.cuboidNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.ellipseNodeStyleDescriptionEClass, EllipseNodeStyleDescription.class, "EllipseNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.packageNodeStyleDescriptionEClass, PackageNodeStyleDescription.class, "PackageNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.rectangleWithExternalLabelNodeStyleDescriptionEClass, RectangleWithExternalLabelNodeStyleDescription.class, "RectangleWithExternalLabelNodeStyleDescription", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.noteNodeStyleDescriptionEClass, NoteNodeStyleDescription.class, "NoteNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.innerFlagNodeStyleDescriptionEClass, InnerFlagNodeStyleDescription.class, "InnerFlagNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.outerFlagNodeStyleDescriptionEClass, OuterFlagNodeStyleDescription.class, "OuterFlagNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.cuboidNodeStyleDescriptionEClass, CuboidNodeStyleDescription.class, "CuboidNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // PapyrusCustomNodesPackageImpl
