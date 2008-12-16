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
package org.eclipse.swtbot.swt.finder.waits;

import java.util.List;


import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.hamcrest.Matcher;

/**
 * Condition that waits for a shell with the specified text to appear.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForMenu extends DefaultCondition {

	private final SWTBotShell				shell;
	private final Matcher<? extends Widget>	matcher;
	private List<MenuItem>					allMenuItems;

	/**
	 * @param shell the shell to search for the menu.
	 * @param matcher the matcher used for matching the menu items.
	 */
	public WaitForMenu(SWTBotShell shell, Matcher<? extends Widget> matcher) {
		this.shell = shell;
		this.matcher = matcher;
	}

	public String getFailureMessage() {
		return "Could not find a menu within the shell '" + shell + "' matching '" + matcher + "'";
	}

	public boolean test() throws Exception {
		allMenuItems = new MenuFinder().findMenus(shell.widget, matcher, true);
		return !allMenuItems.isEmpty();
	}

	public List<MenuItem> getAllMenuItems() {
		return allMenuItems;
	}

	public MenuItem get(int index) {
		return allMenuItems.get(index);
	}

}
