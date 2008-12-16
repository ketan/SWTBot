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
package org.eclipse.swtbot.swt.finder.finders;



import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;

/**
 * Finds context menus for a given control.
 *
 * @see UIThreadRunnable
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ContextMenuFinder extends MenuFinder {

	/**
	 * The control to find context menus.
	 */
	private final Control	control;

	/**
	 * Constructs the context menu finder for the given control to be searched.
	 *
	 * @param control the control that has a context menu.
	 */
	public ContextMenuFinder(Control control) {
		super();
		Assert.isNotNull(control, "The control cannot be null");
		this.control = control;
	}

	/**
	 * Gets the menubar for the given shell.
	 *
	 * @see org.eclipse.swtbot.swt.finder.finders.MenuFinder#menuBar(org.eclipse.swt.widgets.Shell)
	 * @param shell The shell to find the menu bar for.
	 * @return The menu bar found.
	 */
	@Override
	protected Menu menuBar(final Shell shell) {
		return UIThreadRunnable.syncExec(display, new WidgetResult<Menu>() {
			public Menu run() {
				return control.getMenu();
			}
		});
	}
}
