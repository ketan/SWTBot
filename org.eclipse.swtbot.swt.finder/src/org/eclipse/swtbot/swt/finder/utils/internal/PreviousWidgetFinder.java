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
package org.eclipse.swtbot.swt.finder.utils.internal;


import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;

/**
 * This object finds the previous widget.
 * <p>
 * <b>NOTE: This finds all the siblings and finds the index of the previous widget among the siblings. This does not use
 * SWTUtils to find siblings and index for the widget that this instance wraps for performance reasons.</b>
 * </p>
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @see NextWidgetFinder
 * @see WidgetIndexFinder
 */
public final class PreviousWidgetFinder implements WidgetResult<Widget> {
	/**
	 * The widget to use.
	 */
	private final Widget	w;

	/**
	 * Constructs the previous widget finder.
	 *
	 * @param w the widget
	 */
	public PreviousWidgetFinder(Widget w) {
		this.w = w;
	}

	/**
	 * Runs the processing to find the previous widget.
	 *
	 * @see org.eclipse.swtbot.swt.finder.results.WidgetResult#run()
	 * @return The widget found or <code>null</code> if not found.
	 */
	public Widget run() {
		Widget[] siblings = new SiblingFinder(w).run();
		int myIndex = new WidgetIndexFinder(w).run();
		return myIndex > 0 ? siblings[myIndex - 1] : null;
	}
}
