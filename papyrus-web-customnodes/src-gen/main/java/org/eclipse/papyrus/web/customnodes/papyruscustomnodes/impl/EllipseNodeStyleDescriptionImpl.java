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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.EllipseNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesPackage;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.impl.StyleImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Ellipse Node Style Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getFontSize
 * <em>Font Size</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#isItalic
 * <em>Italic</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#isBold
 * <em>Bold</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#isUnderline
 * <em>Underline</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#isStrikeThrough
 * <em>Strike Through</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getBorderColor
 * <em>Border Color</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getBorderRadius
 * <em>Border Radius</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getBorderSize
 * <em>Border Size</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getBorderLineStyle
 * <em>Border Line Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getLabelColor
 * <em>Label Color</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#isShowIcon
 * <em>Show Icon</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.impl.EllipseNodeStyleDescriptionImpl#getLabelIcon
 * <em>Label Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EllipseNodeStyleDescriptionImpl extends StyleImpl implements EllipseNodeStyleDescription {
    /**
     * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFontSize()
     * @generated
     * @ordered
     */
    protected static final int FONT_SIZE_EDEFAULT = 14;

    /**
     * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFontSize()
     * @generated
     * @ordered
     */
    protected int fontSize = FONT_SIZE_EDEFAULT;

    /**
     * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected static final boolean ITALIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected boolean italic = ITALIC_EDEFAULT;

    /**
     * The default value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected static final boolean BOLD_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected boolean bold = BOLD_EDEFAULT;

    /**
     * The default value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isUnderline()
     * @generated
     * @ordered
     */
    protected static final boolean UNDERLINE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isUnderline()
     * @generated
     * @ordered
     */
    protected boolean underline = UNDERLINE_EDEFAULT;

    /**
     * The default value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isStrikeThrough()
     * @generated
     * @ordered
     */
    protected static final boolean STRIKE_THROUGH_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isStrikeThrough()
     * @generated
     * @ordered
     */
    protected boolean strikeThrough = STRIKE_THROUGH_EDEFAULT;

    /**
     * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected UserColor borderColor;

    /**
     * The default value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBorderRadius()
     * @generated
     * @ordered
     */
    protected static final int BORDER_RADIUS_EDEFAULT = 3;

    /**
     * The cached value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBorderRadius()
     * @generated
     * @ordered
     */
    protected int borderRadius = BORDER_RADIUS_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderSize()
     * @generated
     * @ordered
     */
    protected static final int BORDER_SIZE_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderSize()
     * @generated
     * @ordered
     */
    protected int borderSize = BORDER_SIZE_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
     */
    protected static final LineStyle BORDER_LINE_STYLE_EDEFAULT = LineStyle.SOLID;

    /**
     * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
     */
    protected LineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;

    /**
     * The cached value of the '{@link #getLabelColor() <em>Label Color</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelColor()
     * @generated
     * @ordered
     */
    protected UserColor labelColor;

    /**
     * The default value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isShowIcon()
     * @generated
     * @ordered
     */
    protected static final boolean SHOW_ICON_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isShowIcon()
     * @generated
     * @ordered
     */
    protected boolean showIcon = SHOW_ICON_EDEFAULT;

    /**
     * The default value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelIcon()
     * @generated
     * @ordered
     */
    protected static final String LABEL_ICON_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelIcon()
     * @generated
     * @ordered
     */
    protected String labelIcon = LABEL_ICON_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EllipseNodeStyleDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusCustomNodesPackage.Literals.ELLIPSE_NODE_STYLE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getFontSize() {
        return this.fontSize;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFontSize(int newFontSize) {
        int oldFontSize = this.fontSize;
        this.fontSize = newFontSize;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE, oldFontSize, this.fontSize));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isItalic() {
        return this.italic;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setItalic(boolean newItalic) {
        boolean oldItalic = this.italic;
        this.italic = newItalic;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC, oldItalic, this.italic));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isBold() {
        return this.bold;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBold(boolean newBold) {
        boolean oldBold = this.bold;
        this.bold = newBold;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD, oldBold, this.bold));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isUnderline() {
        return this.underline;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUnderline(boolean newUnderline) {
        boolean oldUnderline = this.underline;
        this.underline = newUnderline;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE, oldUnderline, this.underline));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isStrikeThrough() {
        return this.strikeThrough;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStrikeThrough(boolean newStrikeThrough) {
        boolean oldStrikeThrough = this.strikeThrough;
        this.strikeThrough = newStrikeThrough;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBorderColor() {
        if (this.borderColor != null && this.borderColor.eIsProxy()) {
            InternalEObject oldBorderColor = (InternalEObject) this.borderColor;
            this.borderColor = (UserColor) this.eResolveProxy(oldBorderColor);
            if (this.borderColor != oldBorderColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, this.borderColor));
            }
        }
        return this.borderColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetBorderColor() {
        return this.borderColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderColor(UserColor newBorderColor) {
        UserColor oldBorderColor = this.borderColor;
        this.borderColor = newBorderColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, this.borderColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getBorderRadius() {
        return this.borderRadius;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderRadius(int newBorderRadius) {
        int oldBorderRadius = this.borderRadius;
        this.borderRadius = newBorderRadius;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS, oldBorderRadius, this.borderRadius));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getBorderSize() {
        return this.borderSize;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderSize(int newBorderSize) {
        int oldBorderSize = this.borderSize;
        this.borderSize = newBorderSize;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE, oldBorderSize, this.borderSize));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LineStyle getBorderLineStyle() {
        return this.borderLineStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderLineStyle(LineStyle newBorderLineStyle) {
        LineStyle oldBorderLineStyle = this.borderLineStyle;
        this.borderLineStyle = newBorderLineStyle == null ? BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE, oldBorderLineStyle, this.borderLineStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getLabelColor() {
        if (this.labelColor != null && this.labelColor.eIsProxy()) {
            InternalEObject oldLabelColor = (InternalEObject) this.labelColor;
            this.labelColor = (UserColor) this.eResolveProxy(oldLabelColor);
            if (this.labelColor != oldLabelColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR, oldLabelColor, this.labelColor));
            }
        }
        return this.labelColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetLabelColor() {
        return this.labelColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelColor(UserColor newLabelColor) {
        UserColor oldLabelColor = this.labelColor;
        this.labelColor = newLabelColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR, oldLabelColor, this.labelColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isShowIcon() {
        return this.showIcon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setShowIcon(boolean newShowIcon) {
        boolean oldShowIcon = this.showIcon;
        this.showIcon = newShowIcon;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__SHOW_ICON, oldShowIcon, this.showIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelIcon() {
        return this.labelIcon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelIcon(String newLabelIcon) {
        String oldLabelIcon = this.labelIcon;
        this.labelIcon = newLabelIcon;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_ICON, oldLabelIcon, this.labelIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE:
                return this.getFontSize();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC:
                return this.isItalic();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD:
                return this.isBold();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE:
                return this.isUnderline();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
                return this.isStrikeThrough();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                if (resolve)
                    return this.getBorderColor();
                return this.basicGetBorderColor();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                return this.getBorderRadius();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                return this.getBorderSize();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                return this.getBorderLineStyle();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
                if (resolve)
                    return this.getLabelColor();
                return this.basicGetLabelColor();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__SHOW_ICON:
                return this.isShowIcon();
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_ICON:
                return this.getLabelIcon();
            default:
                return super.eGet(featureID, resolve, coreType);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE:
                this.setFontSize((Integer) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD:
                this.setBold((Boolean) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE:
                this.setUnderline((Boolean) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
                this.setStrikeThrough((Boolean) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                this.setBorderColor((UserColor) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                this.setBorderRadius((Integer) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                this.setBorderSize((Integer) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                this.setBorderLineStyle((LineStyle) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
                this.setLabelColor((UserColor) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__SHOW_ICON:
                this.setShowIcon((Boolean) newValue);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_ICON:
                this.setLabelIcon((String) newValue);
                return;
            default:
                super.eSet(featureID, newValue);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE:
                this.setFontSize(FONT_SIZE_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD:
                this.setBold(BOLD_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE:
                this.setUnderline(UNDERLINE_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
                this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                this.setBorderColor((UserColor) null);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                this.setBorderRadius(BORDER_RADIUS_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                this.setBorderSize(BORDER_SIZE_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                this.setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
                this.setLabelColor((UserColor) null);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__SHOW_ICON:
                this.setShowIcon(SHOW_ICON_EDEFAULT);
                return;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_ICON:
                this.setLabelIcon(LABEL_ICON_EDEFAULT);
                return;
            default:
                super.eUnset(featureID);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE:
                return this.fontSize != FONT_SIZE_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD:
                return this.bold != BOLD_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE:
                return this.underline != UNDERLINE_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
                return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                return this.borderColor != null;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                return this.borderRadius != BORDER_RADIUS_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                return this.borderSize != BORDER_SIZE_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                return this.borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
                return this.labelColor != null;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__SHOW_ICON:
                return this.showIcon != SHOW_ICON_EDEFAULT;
            case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__LABEL_ICON:
                return LABEL_ICON_EDEFAULT == null ? this.labelIcon != null : !LABEL_ICON_EDEFAULT.equals(this.labelIcon);
            default:
                return super.eIsSet(featureID);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == LabelStyle.class) {
            switch (derivedFeatureID) {
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE:
                    return ViewPackage.LABEL_STYLE__FONT_SIZE;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC:
                    return ViewPackage.LABEL_STYLE__ITALIC;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD:
                    return ViewPackage.LABEL_STYLE__BOLD;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE:
                    return ViewPackage.LABEL_STYLE__UNDERLINE;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
                    return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == BorderStyle.class) {
            switch (derivedFeatureID) {
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                    return DiagramPackage.BORDER_STYLE__BORDER_COLOR;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                    return DiagramPackage.BORDER_STYLE__BORDER_RADIUS;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                    return DiagramPackage.BORDER_STYLE__BORDER_SIZE;
                case PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                    return DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == LabelStyle.class) {
            switch (baseFeatureID) {
                case ViewPackage.LABEL_STYLE__FONT_SIZE:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__FONT_SIZE;
                case ViewPackage.LABEL_STYLE__ITALIC:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__ITALIC;
                case ViewPackage.LABEL_STYLE__BOLD:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BOLD;
                case ViewPackage.LABEL_STYLE__UNDERLINE:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__UNDERLINE;
                case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == BorderStyle.class) {
            switch (baseFeatureID) {
                case DiagramPackage.BORDER_STYLE__BORDER_COLOR:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR;
                case DiagramPackage.BORDER_STYLE__BORDER_RADIUS:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS;
                case DiagramPackage.BORDER_STYLE__BORDER_SIZE:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE;
                case DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE:
                    return PapyrusCustomNodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (fontSize: ");
        result.append(this.fontSize);
        result.append(", italic: ");
        result.append(this.italic);
        result.append(", bold: ");
        result.append(this.bold);
        result.append(", underline: ");
        result.append(this.underline);
        result.append(", strikeThrough: ");
        result.append(this.strikeThrough);
        result.append(", borderRadius: ");
        result.append(this.borderRadius);
        result.append(", borderSize: ");
        result.append(this.borderSize);
        result.append(", borderLineStyle: ");
        result.append(this.borderLineStyle);
        result.append(", showIcon: ");
        result.append(this.showIcon);
        result.append(", labelIcon: ");
        result.append(this.labelIcon);
        result.append(')');
        return result.toString();
    }

} // EllipseNodeStyleDescriptionImpl