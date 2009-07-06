/*******************************************************************************
 * Copyright (c) 2008-2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.widgets;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import static org.hamcrest.Matchers.any;

import javax.swing.text.View;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IViewReference;
import org.hamcrest.SelfDescribing;

/**
 * This represents the eclipse {@link View} item.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @version $Id$
 */
public class SWTBotView extends SWTBotWorkbenchPart<IViewReference> {

	private Widget	widget;

	/**
	 * Creates an instance of a view part.
	 * 
	 * @param partReference the view reference representing this view.
	 * @param bot the bot that's used to find controls within this view.
	 * @since 2.0
	 */
	public SWTBotView(IViewReference partReference, SWTWorkbenchBot bot) {
		super(partReference, bot);
	}

	/**
	 * Creates an instance of a view part.
	 * 
	 * @param partReference the part reference.
	 * @param bot the helper bot.
	 * @param description the description of the workbench part.
	 */
	public SWTBotView(IViewReference partReference, SWTWorkbenchBot bot, SelfDescribing description) {
		super(partReference, bot, description);
	}

	public void setFocus() {
		syncExec(new VoidResult() {
			public void run() {
				((Control) getWidget()).setFocus();
			}
		});
	}

	/**
	 * @return the view reference for this view.
	 */
	public IViewReference getViewReference() {
		return partReference;
	}

	/**
	 * The parent widget inside the partReference, that is the tabFolder for all controls within the view. If you want
	 * to look for a particular widget within the part, this is a good place to start searching for the widget.
	 * <p>
	 * <b>NOTE:</b> Clients must ensure that the view is active at the time of making this call. If the view is not
	 * active, then this method will throw a {@link WidgetNotFoundException}.
	 * </p>
	 * 
	 * @return the parent widget in the view.
	 * @see #findWidget(org.hamcrest.Matcher)
	 * @see #assertActive()
	 * @see #show()
	 */
	public Widget getWidget() {
		show();
		if (widget == null)
			widget = findWidget(any(Widget.class));
		return widget;
	}

	public boolean isActive() {
		return partReference.getPage().getActivePartReference() == partReference;
	}

}
