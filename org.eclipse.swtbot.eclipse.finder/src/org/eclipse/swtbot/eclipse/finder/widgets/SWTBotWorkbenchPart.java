/*******************************************************************************
 * Copyright (c) 2008-2009 Ketan Padegaonkar and others.
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.finders.CommandFinder;
import org.eclipse.swtbot.eclipse.finder.finders.ViewMenuFinder;
import org.eclipse.swtbot.eclipse.finder.widgets.utils.PartLabelDescription;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarRadioButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarToggleButton;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.ViewPane;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

/**
 * This represents the eclipse {@link IWorkbenchPartReference} item, subclasses must extend this to implement support
 * for various {@link IWorkbenchPartReference}s.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @version $Id$
 * @since 2.0
 */
public abstract class SWTBotWorkbenchPart<T extends IWorkbenchPartReference> {

	/** The IWorkbenchPartReference reference that this part encapsulates. */
	protected final T				partReference;
	/** The logger. */
	protected final Logger			log;
	/** A helper swtbot instance. */
	protected final SWTWorkbenchBot	bot;
	private final ViewMenuFinder	menuFinder;
	private final SelfDescribing	description;

	/**
	 * Creates an instance of a workbench part.
	 * 
	 * @param partReference the part reference.
	 * @param bot the helper bot.
	 */
	public SWTBotWorkbenchPart(T partReference, SWTWorkbenchBot bot) {
		this(partReference, bot, new PartLabelDescription<T>(partReference));
	}

	/**
	 * Creates an instance of a workbench part.
	 * 
	 * @param partReference the part reference.
	 * @param bot the helper bot.
	 * @param description the description of the workbench part.
	 */
	public SWTBotWorkbenchPart(T partReference, SWTWorkbenchBot bot, SelfDescribing description) {
		this.bot = bot;
		if (description == null )
			description = new PartLabelDescription<T>(partReference);
		this.description = description;
		Assert.isNotNull(partReference, "The part reference cannot be null"); //$NON-NLS-1$
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
					throw new RuntimeException("Could not show partReference - " + partReference.getPartName(), e); //$NON-NLS-1$
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
	 * @return The list of menus
	 */
	public List<SWTBotViewMenu> menus() {
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
			throw new WidgetNotFoundException("Could not find view menu with label " + label + " at index " + index, e); //$NON-NLS-1$ //$NON-NLS-2$
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
				final List<SWTBotToolbarButton> l = new ArrayList<SWTBotToolbarButton>();

				if (toolbar == null)
					return l;

				ToolItem[] items = toolbar.getItems();
				log.debug("number of items : " + items.length);
				for (int i = 0; i < items.length; i++) {
					try {
						if (SWTUtils.hasStyle(items[i], SWT.PUSH))
							l.add(new SWTBotToolbarPushButton(items[i]));
						else if(SWTUtils.hasStyle(items[i], SWT.CHECK))
							l.add(new SWTBotToolbarToggleButton(items[i]));
						else if(SWTUtils.hasStyle(items[i], SWT.RADIO))
							l.add(new SWTBotToolbarRadioButton(items[i]));
						else if(SWTUtils.hasStyle(items[i], SWT.DROP_DOWN))
							l.add(new SWTBotToolbarDropDownButton(items[i]));
					} catch (WidgetNotFoundException e) {
						log.warn("Failed to find widget " + items[i].getText(), e); //$NON-NLS-1$
					}
				}

				return l;

			}
		});
	}

	/**
	 * Gets the toolbar drop down button matching the given toolbar button.
	 *
	 * @param tooltip The tooltip to use to find the button to return.
	 * @return The toolbar button.
	 * @throws WidgetNotFoundException Thrown if the widget was not found matching the given tooltip.
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton(String tooltip) throws WidgetNotFoundException {
		SWTBotToolbarButton abstractButton = toolbarButton(tooltip);
		if (abstractButton instanceof SWTBotToolbarDropDownButton)
			return (SWTBotToolbarDropDownButton) abstractButton;

		throw new WidgetNotFoundException("Unable to find toolitem with the given tooltip '" + tooltip + "'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the toolbar radio button matching the given toolbar button.
	 *
	 * @param tooltip The tooltip to use to find the button to return.
	 * @return The toolbar button.
	 * @throws WidgetNotFoundException Thrown if the widget was not found matching the given tooltip.
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton(String tooltip) throws WidgetNotFoundException {
		SWTBotToolbarButton abstractButton = toolbarButton(tooltip);
		if (abstractButton instanceof SWTBotToolbarRadioButton)
			return (SWTBotToolbarRadioButton) abstractButton;

		throw new WidgetNotFoundException("Unable to find toolitem with the given tooltip '" + tooltip + "'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the toolbar push button matching the given toolbar button.
	 *
	 * @param tooltip The tooltip to use to find the button to return.
	 * @return The toolbar button.
	 * @throws WidgetNotFoundException Thrown if the widget was not found matching the given tooltip.
	 */
	public SWTBotToolbarPushButton toolbarPushButton(String tooltip) throws WidgetNotFoundException {
		SWTBotToolbarButton abstractButton = toolbarButton(tooltip);
		if (abstractButton instanceof SWTBotToolbarPushButton)
			return (SWTBotToolbarPushButton) abstractButton;

		throw new WidgetNotFoundException("Unable to find toolitem with the given tooltip '" + tooltip + "'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the toggle toolbar button matching the given toolbar button.
	 *
	 * @param tooltip The tooltip to use to find the button to return.
	 * @return The toolbar button.
	 * @throws WidgetNotFoundException Thrown if the widget was not found matching the given tooltip.
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton(String tooltip) throws WidgetNotFoundException {
		SWTBotToolbarButton abstractButton = toolbarButton(tooltip);
		if (abstractButton instanceof SWTBotToolbarToggleButton)
			return (SWTBotToolbarToggleButton) abstractButton;

		throw new WidgetNotFoundException("Unable to find toolitem with the given tooltip '" + tooltip + "'"); //$NON-NLS-1$ //$NON-NLS-2$
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

		throw new WidgetNotFoundException("Unable to find toolitem with the given tooltip '" + tooltip + "'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @param matcher a matcher.
	 * @return a widget within the parent widget that matches the specified matcher.
	 */
	protected <S extends Widget> S findWidget(Matcher<S> matcher) {
		return findWidgets(matcher).get(0);
	}

	/**
	 * @param matcher a matcher.
	 * @return a widget within the parent widget that matches the specified matcher.
	 */
	protected <S extends Widget> List<? extends S> findWidgets(Matcher<S> matcher) {
		Finder finder = bot.getFinder();
		Control control = getControl();
		boolean shouldFindInvisibleControls = finder.shouldFindInvisibleControls();
		finder.setShouldFindInvisibleControls(true);
		try {
			return bot.widgets(matcher, control);
		} catch (Exception e) {
			throw new WidgetNotFoundException("Could not find any control inside the view " + partReference.getPartName(), e); //$NON-NLS-1$
		} finally {
			finder.setShouldFindInvisibleControls(shouldFindInvisibleControls);
		}
	}

	/**
	 * Returns the workbench pane control.
	 * 
	 * @return returns the workbench pane control.
	 */
	private Control getControl() {
		return ((WorkbenchPartReference) partReference).getPane().getControl();
	}

	/**
	 * Returns a SWTBot instance that matches the contents of this workbench part.
	 * 
	 * @return SWTBot
	 */
	public SWTBot bot() {
		return new SWTBot(getControl());
	}

	/**
	 * Asserts that the viewpart is active.
	 * 
	 */
	protected void assertActive() {
		Assert.isLegal(isActive(), MessageFormat.format("The workbench part {0}is not active", description));
	}

	/**
	 * @return <code>true</code> if the part is currently active.
	 */
	public abstract boolean isActive();

	/**
	 * Sets focus on the current part.
	 */
	public abstract void setFocus();

}
