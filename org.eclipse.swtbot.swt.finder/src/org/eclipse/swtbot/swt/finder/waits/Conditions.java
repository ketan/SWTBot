/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Patel - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259837
 *******************************************************************************/

package org.eclipse.swtbot.swt.finder.waits;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.hamcrest.Matcher;

/**
 * This is a factory class to create some conditions provided with SWTBot.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class Conditions {

	/**
	 * Gets the condition for checking tables have the proper number of rows. Useful in cases where the table is
	 * populated continuously from a non UI thread.
	 *
	 * @param table the table
	 * @param rowCount the number of rows that the table must have, in order for {@link ICondition#test()} to evaluate
	 *            to <code>true</code>.
	 * @return <code>true</code> if the table has the number of rows specified. Otherwise <code>false</code>.
	 * @throws IllegalArgumentException Thrown if the row count is less then 1.
	 * @since 1.2
	 */
	public static ICondition tableHasRows(SWTBotTable table, int rowCount) {
		return new TableHasRows(table, rowCount);
	}

	/**
	 * Gets the condition for checking if shells have closed. Useful in cases where a shell takes long to close.
	 *
	 * @param shell the shell to monitor.
	 * @return a condition that evaluates to false until the shell is closed or invisible.
	 * @since 1.2
	 */
	public static ICondition shellCloses(SWTBotShell shell) {
		return new ShellCloses(shell);
	}

	/**
	 * Gets the condition for checking if an awaited shell is visible and has focus
	 *
	 * @param shellText the text of the shell.
	 * @return a condition that evaluates to false until the shell is visible and has focus.
	 * @since 1.3
	 */
	public static ICondition shellIsActive(String shellText) {
		return new ShellIsActive(shellText);
	}

	/**
	 * @param matcher a matcher.
	 * @return a condition that waits until the matcher evaluates to true.
	 * @since 2.0
	 */
	public static <T extends Widget> WaitForWidget<T> waitForWidget(Matcher<T> matcher) {
		return new WaitForWidget<T>(matcher);
	}

	/**
	 * @param matcher a matcher.
	 * @param parent the parent under which a widget will be found.
	 * @return a condition that waits until the matcher evaluates to true.
	 * @since 2.0
	 */
	public static <T extends Widget> WaitForWidgetInParent<T> waitForWidget(Matcher<T> matcher, Widget parent) {
		return new WaitForWidgetInParent<T>(matcher, parent);
	}

	/**
	 * @param matcher the matcher.
	 * @return a condition that waits until the matcher evaluates to true.
	 * @since 2.0
	 */
	public static WaitForShell waitForShell(Matcher<Shell> matcher) {
		return new WaitForShell(matcher);
	}

	/**
	 * @param matcher the matcher.
	 * @param parent the parent under which a shell will be found or <code>null</code> to search all shells.
	 * @return a condition that waits until the matcher evaluates to true.
	 * @since 2.0
	 */
	public static WaitForShell waitForShell(Matcher<Shell> matcher, Shell parent) {
		return new WaitForShellInParent(parent, matcher);
	}

	/**
	 * @param shell the shell to search for the menu.
	 * @param matcher the matcher.
	 * @return a condition that waits until the matcher evaluates to true.
	 * @since 2.0
	 */
	public static WaitForMenu waitForMenu(SWTBotShell shell, Matcher<MenuItem> matcher) {
		return new WaitForMenu(shell, matcher);
	}

}
