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
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;

import java.util.List;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotMenu extends AbstractSWTBot<MenuItem> {

	/**
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotMenu(MenuItem w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Clicks on the menu item
	 */
	public void click() {
		log.debug(MessageFormat.format("Clicking on {0}", this));
		assertEnabled();
		toggleSelection();
		notify(SWT.Selection);
		log.debug(MessageFormat.format("Clicked on {0}", this));
	}

	/**
	 * Toggle the selection of the checkbox if applicable.
	 */
	private void toggleSelection() {
		syncExec(new VoidResult() {
			public void run() {
				if (hasStyle(widget, SWT.CHECK) | hasStyle(widget, SWT.RADIO))
					widget.setSelection(!widget.getSelection());
			}
		});
	}

	/**
	 * Gets the menu matching the given name.
	 *
	 * @param menuName the name of the menu item that is to be found
	 * @return the first menu that matches the menuName
	 * @throws WidgetNotFoundException if the widget is not found.
	 */
	public SWTBotMenu menu(final String menuName) throws WidgetNotFoundException {
		MenuItem menuItem = syncExec(new WidgetResult<MenuItem>() {
			public MenuItem run() {
				Menu bar = widget.getMenu();
				List menus = new MenuFinder().findMenus(bar, withMnemonic(menuName), true);
				if (!menus.isEmpty())
					return (MenuItem) menus.get(0);
				return null;
			}
		});
		return new SWTBotMenu(menuItem);
	}

	@Override
	public boolean isEnabled() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.isEnabled();
			}
		});
	}

	/**
	 * Gets if this menu item is checked.
	 *
	 * @return <code>true</code> if the menu is checked, <code>false</code> otherwise.
	 * @see MenuItem#getSelection()
	 * @since 1.2
	 */
	public boolean isChecked() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getSelection();
			}
		});
	}
}
