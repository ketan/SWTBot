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

import javax.swing.text.View;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
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

	public boolean isActive() {
		return partReference.getPage().getActivePartReference() == partReference;
	}

}
