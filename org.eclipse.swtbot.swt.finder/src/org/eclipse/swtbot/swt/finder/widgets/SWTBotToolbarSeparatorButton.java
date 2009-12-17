/*******************************************************************************
 * Copyright (c) 2009 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.Style;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * This represents a toolbar item that is a separator.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotToolbarDropDownButton.java 431 2009-11-06 07:11:15Z kpadegaonka $
 * @since 2.0
 */
@SWTBotWidget(clasz = ToolItem.class, preferredName = "toolbarSeparatorButton", style = @Style(name = "SWT.SEPARATOR", value = SWT.SEPARATOR), referenceBy = {
		ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })
public class SWTBotToolbarSeparatorButton extends SWTBotToolbarButton {

	/**
	 * Constructs an new instance of this item.
	 *
	 * @param w the tool item.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotToolbarSeparatorButton(ToolItem w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an new instance of this item.
	 * 
	 * @param w the tool item.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotToolbarSeparatorButton(ToolItem w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
		Assert.isTrue(SWTUtils.hasStyle(w, SWT.SEPARATOR), "Expecting a separator button."); //$NON-NLS-1$

	}

	/**
	 * Click on the tool item.
	 *
	 * @since 2.0
	 */
	public SWTBotToolbarSeparatorButton click() {
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		assertEnabled();
		sendNotifications();
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
		return this;
	}
}
