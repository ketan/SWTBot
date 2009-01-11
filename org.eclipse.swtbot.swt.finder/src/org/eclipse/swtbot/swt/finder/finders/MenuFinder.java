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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.collections.OrderedSet;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Matcher;

/**
 * Finds menus matching a particular matcher.
 *
 * @see UIThreadRunnable
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MenuFinder {

	/**
	 * The logging instance for this class.
	 */
	private static final Logger	log	= Logger.getLogger(MenuFinder.class);

	/** The display */
	protected final Display		display;

	/**
	 * Creates a MenuFinder.
	 */
	public MenuFinder() {
		display = SWTUtils.display();
	}

	/**
	 * Finds a menu matching the given item in all available shells.
	 *
	 * @param matcher the matcher that can match menus and menu items.
	 * @return all menus in all shells that match the matcher.
	 */
	public List<MenuItem> findMenus(Matcher<?> matcher) {
		return findMenus(getShells(), matcher, false);
	}

	/**
	 * Finds all the menus using the given matcher in the set of shells provided. If recursive is set, it will attempt
	 * to find the controls recursively in each of the menus it that is found.
	 *
	 * @param shells the shells to probe for menus.
	 * @param matcher the matcher that can match menus and menu items.
	 * @param recursive if set to true, will find sub-menus as well.
	 * @return all menus in the specified shells that match the matcher.
	 */
	public List<MenuItem> findMenus(Shell[] shells, Matcher<?> matcher, boolean recursive) {
		OrderedSet<MenuItem> result = new OrderedSet<MenuItem>();
		for (Shell shell : shells)
			result.addAll(findMenus(shell, matcher, recursive));
		return new ArrayList<MenuItem>(result);
	}

	/**
	 * Finds the menus in the given shell using the given matcher. If recursive is set, it will attempt to find the
	 * controls recursively in each of the menus it that is found.
	 *
	 * @param shell the shell to probe for menus.
	 * @param matcher the matcher that can match menus and menu items.
	 * @param recursive if set to true, will find sub-menus as well.
	 * @return all menus in the specified shell that match the matcher.
	 */
	public List<MenuItem> findMenus(final Shell shell, Matcher<?> matcher, boolean recursive) {
		OrderedSet<MenuItem> result = new OrderedSet<MenuItem>();
		result.addAll(findMenus(menuBar(shell), matcher, recursive));
		return new ArrayList<MenuItem>(result);
	}

	/**
	 * Gets the menu bar in the given shell.
	 *
	 * @param shell the shell.
	 * @return the menu in the shell.
	 * @see Shell#getMenuBar()
	 */
	protected Menu menuBar(final Shell shell) {
		return UIThreadRunnable.syncExec(display, new WidgetResult<Menu>() {
			public Menu run() {
				return shell.getMenuBar();
			}
		});

	}

	/**
	 * Finds all the menus in the given menu bar matching the given matcher. If recursive is set, it will attempt to
	 * find the controls recursively in each of the menus it that is found.
	 *
	 * @param bar the menu bar
	 * @param matcher the matcher that can match menus and menu items.
	 * @param recursive if set to true, will find sub-menus as well.
	 * @return all menus in the specified menubar that match the matcher.
	 */
	public List<MenuItem> findMenus(final Menu bar, final Matcher<?> matcher, final boolean recursive) {
		return UIThreadRunnable.syncExec(display, new ListResult<MenuItem>() {
			public List<MenuItem> run() {
				return findMenusInternal(bar, matcher, recursive);
			}
		});
	}

	/**
	 * Gets all of the shells in the current display.
	 *
	 * @return all shells in the display.
	 * @see Display#getShells()
	 */
	protected Shell[] getShells() {
		return UIThreadRunnable.syncExec(display, new ArrayResult<Shell>() {
			public Shell[] run() {
				return display.getShells();
			}
		});
	}

	/**
	 * @param bar
	 * @param matcher
	 * @param recursive
	 * @return
	 */
	private List<MenuItem> findMenusInternal(final Menu bar, final Matcher<?> matcher, final boolean recursive) {
		OrderedSet<MenuItem> result = new OrderedSet<MenuItem>();
		if (bar != null) {
			bar.notifyListeners(SWT.Show, new Event());
			MenuItem[] items = bar.getItems();
			for (MenuItem menuItem : items) {
				if (isSeparator(menuItem)) {
					continue;
				}
				if (matcher.matches(menuItem))
					result.add(menuItem);
				if (recursive)
					result.addAll(findMenusInternal(menuItem.getMenu(), matcher, recursive));
			}
			bar.notifyListeners(SWT.Hide, new Event());
		}
		return new ArrayList<MenuItem>(result);
	}

	private boolean isSeparator(MenuItem menuItem) {
		// FIXME see https://bugs.eclipse.org/bugs/show_bug.cgi?id=208188
		// FIXED > 20071101 https://bugs.eclipse.org/bugs/show_bug.cgi?id=208188#c2
		return (menuItem.getStyle() & SWT.SEPARATOR) != 0;
	}

}
