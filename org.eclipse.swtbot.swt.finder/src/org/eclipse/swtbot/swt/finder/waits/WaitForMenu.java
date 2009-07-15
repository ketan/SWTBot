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
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.hamcrest.Matcher;

/**
 * Condition that waits for a shell with the specified text to appear.
 *
 * @see Conditions
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class WaitForMenu extends WaitForObjectCondition<MenuItem> {

	private final SWTBotShell	shell;

	/**
	 * @param shell the shell to search for the menu.
	 * @param matcher the matcher used for matching the menu items.
	 */
	public WaitForMenu(SWTBotShell shell, Matcher<MenuItem> matcher) {
		super(matcher);
		this.shell = shell;
	}

	public String getFailureMessage() {
		return "Could not find a menu within the shell '" + shell + "' matching '" + matcher + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected List<MenuItem> findMatches() {
		return new MenuFinder().findMenus(shell.widget, matcher, true);
	}

}
