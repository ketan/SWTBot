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
 * This object is used to find the next widget.
 * <p>
 * <b>NOTE: This finds all the siblings and finds the index of the next widget among the siblings. This does not use
 * SWTUtils to find siblings and index for the widget that this instance wraps for performance reasons.</b>
 * </p>
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: NextWidgetFinder.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 * @see PreviousWidgetFinder
 * @see WidgetIndexFinder
 */
public class NextWidgetFinder implements WidgetResult<Widget> {

	/**
	 * The widget to use.
	 */
	private final Widget	w;

	/**
	 * Constructs the next widget finder.
	 *
	 * @param w the widget
	 */
	public NextWidgetFinder(Widget w) {
		this.w = w;
	}

	/**
	 * Runs the processing to find the next widget.
	 *
	 * @see org.eclipse.swtbot.swt.finder.results.WidgetResult#run()
	 * @return The next widget or <code>null</code> if not found.
	 */
	public Widget run() {
		Widget[] siblings = new SiblingFinder(w).run();
		int widgetIndex = new WidgetIndexFinder(w).run();
		if (widgetIndex < siblings.length - 1)
			return siblings[widgetIndex + 1];
		return null;
	}
}
