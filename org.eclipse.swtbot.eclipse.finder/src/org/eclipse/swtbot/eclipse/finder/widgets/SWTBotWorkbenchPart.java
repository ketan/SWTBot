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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.eclipse.finder.finders.CommandFinder;
import org.eclipse.swtbot.eclipse.finder.finders.ViewMenuFinder;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.WidgetNotFoundException;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.ViewPane;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.Matcher;

/**
 * This represents the eclipse {@link IWorkbenchPartReference} item, subclasses must extend this to implement support
 * for various {@link IWorkbenchPartReference}s.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public abstract class SWTBotWorkbenchPart<T extends IWorkbenchPartReference> {

	protected final T				partReference;
	protected final Logger			log;
	private final ViewMenuFinder	menuFinder;
	protected final SWTEclipseBot	bot;

	public SWTBotWorkbenchPart(T partReference, SWTEclipseBot bot) {
		this.bot = bot;
		Assert.isNotNull(partReference, "The part reference cannot be null");
		this.partReference = partReference;
		this.menuFinder = new ViewMenuFinder();
		log = Logger.getLogger(getClass());
	}

	/**
	 * Close the partReference.
	 */
	public void close() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				IViewReference viewReference = (IViewReference) partReference;
				viewReference.getPage().hideView(viewReference);
			}
		});
	}

	/**
	 * Shows the part if it is visible.
	 */
	public void show() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				try {
					partReference.getPage().activate(partReference.getPart(true));
					partReference.getPage().showView(partReference.getId());
				} catch (PartInitException e) {
					throw new RuntimeException("Could not show partReference - " + partReference.getPartName(), e);
				}
			}
		});
	}

	/**
	 * Gets the title of the partReference.
	 *
	 * @return the title of the part as visible in the tab
	 */
	public String getTitle() {
		return partReference.getPartName();
	}

	/**
	 * Gets a list of all menus within the partReference. This will also include sub menus.
	 *
	 * @return The list of menus (IContributionItems).
	 */
	public List menus() {
		return menuFinder.findMenus((IViewReference) partReference, anything(), true);
	}

	/**
	 * Gets a menu item matching the give label within the partReference menu if one exists.
	 *
	 * @param label The label matching name in the menu.
	 * @return The {@link SWTBotMenu} item.
	 * @throws WidgetNotFoundException Thrown if the menu can not be found or if the partReference does not contain a
	 *             menu.
	 */
	public SWTBotViewMenu menu(String label) throws WidgetNotFoundException {
		return menu(label, 0);
	}

	/**
	 * Gets a menu item matching the give label within the partReference menu if one exists.
	 *
	 * @param label The label matching name in the menu.
	 * @param index The index of the menu to choose.
	 * @return The {@link SWTBotMenu} item.
	 * @throws WidgetNotFoundException Thrown if the menu can not be found or if the partReference does not contain a
	 *             menu.
	 */
	public SWTBotViewMenu menu(String label, int index) throws WidgetNotFoundException {
		try {
			List<SWTBotViewMenu> menuItems = menuFinder.findMenus((IViewReference) partReference, withMnemonic(label), true);
			if ((menuItems == null) || (menuItems.size() < 1)) {
				CommandFinder finder = new CommandFinder();
				List<SWTBotCommand> command = finder.findCommand(equalTo(label));
				return command.get(index);
			}
			return menuItems.get(index);
		} catch (Exception e) {
			throw new WidgetNotFoundException("Could not find view menu with label " + label + " at index " + index, e);
		}
	}

	/**
	 * Gets the toolbar buttons currently visible.
	 *
	 * @return The set of toolbar buttons.
	 */
	public List<SWTBotToolbarButton> getToolbarButtons() {
		return UIThreadRunnable.syncExec(new ListResult<SWTBotToolbarButton>() {

			public List<SWTBotToolbarButton> run() {
				ViewPane obj = (ViewPane) ((WorkbenchPartReference) partReference).getPane();
				ToolBar toolbar = (ToolBar) obj.getToolBar();

				List<SWTBotToolbarButton> l = new ArrayList<SWTBotToolbarButton>();
				ToolItem[] items = toolbar.getItems();
				for (int i = 0; i < items.length; i++) {
					try {
						l.add(new SWTBotToolbarButton(items[i]));
					} catch (WidgetNotFoundException e) {
						log.warn("Failed to find widget " + items[i].getText(), e);
					}
				}

				return l;

			}
		});
	}

	/**
	 * Gets the toolbar button matching the given toolbar button.
	 *
	 * @param tooltip The tooltip to use to find the button to return.
	 * @return The toolbar button.
	 * @throws WidgetNotFoundException Thrown if the widget was not found matching the given tooltip.
	 */
	public SWTBotToolbarButton toolbarButton(String tooltip) throws WidgetNotFoundException {
		List<SWTBotToolbarButton> l = getToolbarButtons();

		for (int i = 0; i < l.size(); i++) {
			SWTBotToolbarButton item = l.get(i);
			if (item.getToolTipText().equals(tooltip)) {
				return item;
			}
		}

		throw new WidgetNotFoundException("Unable to find toolitem with the given tooltip '" + tooltip + "'");
	}

	/**
	 * @param matcher a matcher.
	 * @return a widget within the parent widget that matches the specified matcher.
	 */
	protected Widget findWidget(Matcher<?> matcher) {
		Finder finder = bot.getFinder();
		Control control = ((WorkbenchPartReference) partReference).getPane().getControl();
		boolean shouldFindInvisibleControls = finder.shouldFindInvisibleControls();
		finder.setShouldFindInvisibleControls(true);
		try {
			return finder.findControls(control, matcher, true).get(0);
		} catch (Exception e) {
			throw new WidgetNotFoundException("Could not find any control inside the view " + partReference.getPartName(), e);
		} finally {
			finder.setShouldFindInvisibleControls(shouldFindInvisibleControls);
		}
	}

	public abstract void setFocus();

}
