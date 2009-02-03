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
package org.eclipse.swtbot.eclipse.finder.finders;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotViewMenu;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.internal.ViewPane;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.Matcher;

/**
 * Finds the menu items within a view.
 *
 * @author @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
public class ViewMenuFinder {
	/**
	 * The logging instance for this class.
	 */
	private static final Logger	log	= Logger.getLogger(ViewMenuFinder.class);

	/**
	 * Creates a MenuFinder.
	 */
	public ViewMenuFinder() {
		// Do nothing.
	}

	/**
	 * Gets a list of all menus within the view.
	 *
	 * @param view the view to probe for menus.
	 * @param matcher the matcher that can match menus and menu items.
	 * @param recursive if set to <code>true</code>, will find sub-menus as well.
	 * @return The list of menus (IContributionItems) that match the matcher.
	 * @since 2.0
	 */
	public List<SWTBotViewMenu> findMenus(final IViewReference view, final Matcher<?> matcher, final boolean recursive) {
		return UIThreadRunnable.syncExec(new ListResult<SWTBotViewMenu>() {

			public List<SWTBotViewMenu> run() {
				ViewPane viewPane = (ViewPane) ((WorkbenchPartReference) view).getPane();
				MenuManager mgr = viewPane.getMenuManager();
				List<SWTBotViewMenu> l = new ArrayList<SWTBotViewMenu>();

				l.addAll(getMenuItemsInternal(mgr.getItems(), matcher, recursive));

				return l;
			}
		});
	}

	// This is expected to be called from within the UI thread. If not it will throw
	// exceptions based on invalid thread access.
	private List<SWTBotViewMenu> getMenuItemsInternal(IContributionItem[] items, Matcher<?> matcher, boolean recursive) {
		List<SWTBotViewMenu> l = new ArrayList<SWTBotViewMenu>();

		for (int i = 0; i < items.length; i++) {
			IContributionItem item = items[i];

			try {
				if ((item instanceof MenuManager) && recursive) {
					// Sub menus
					MenuManager menuManager = (MenuManager) item;

					l.addAll(getMenuItemsInternal(menuManager.getItems(), matcher, recursive));
				} else if (item instanceof ActionContributionItem) {
					// Menus
					ActionContributionItem actionContribution = (ActionContributionItem) item;

					if (matcher.matches(actionContribution.getAction()))
						l.add(new SWTBotViewMenu(actionContribution));
				}
			} catch (WidgetNotFoundException e) {
				log.warn(e);
			}
		}

		return l;
	}
}
