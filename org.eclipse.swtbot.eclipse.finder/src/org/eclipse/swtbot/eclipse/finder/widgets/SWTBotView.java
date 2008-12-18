/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.widgets;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import static org.hamcrest.Matchers.anything;

import javax.swing.text.View;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IViewReference;

/**
 * This represents the eclipse {@link View} item.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotView extends SWTBotWorkbenchPart<IViewReference> {

	/**
	 * The parent widget inside the partReference, that is the container for all controls within the view. If you want
	 * to look for a particular widget within the part, this is a good place to start searching for the widget.
	 * 
	 * @since 2.0
	 */
	public final Widget	widget;

	/**
	 * @since 2.0
	 */
	public SWTBotView(IViewReference partReference, SWTEclipseBot bot) {
		super(partReference, bot);
		this.widget = findWidget(anything());
	}

	public void setFocus() {
		syncExec(new VoidResult() {
			public void run() {
				((Control) widget).setFocus();
			}
		});
	}

	/**
	 * @return the view reference for this view.
	 */
	public IViewReference getViewReference() {
		return partReference;
	}

}
