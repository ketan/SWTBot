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

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

/**
 * A SWTBotViewMenu represents a menu item within a view's menu.
 *
 * @author @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
public class SWTBotViewMenu {
	private IAction					action			= null;
	private ActionContributionItem	actionItem		= null;
	private String					text			= null;
	/**
	 * Holds the results of the click action.
	 */
	protected Object				menuClickResult	= null;
	/**
	 * Holds command if setup.
	 */
	protected Command				cmdItem			= null;
	/**
	 * Holds the id of the command if one exists.
	 */
	protected String				commandID		= null;

	/**
	 * Constructs a SWTBot View Menu item.
	 *
	 * @param commandItem The command contribution item.
	 * @throws WidgetNotFoundException Thrown if both values are <code>null</code>.
	 * @throws AssertionFailedException If the contribution item is <code>null</code>.
	 */
	public SWTBotViewMenu(Command commandItem) throws WidgetNotFoundException {
		cmdItem = commandItem;

		commandID = cmdItem.getId();
		try {
			text = cmdItem.getName();
		} catch (NotDefinedException e) {
			text = "";
		}
	}

	/**
	 * Constructs a SWTBot View Menu item.
	 *
	 * @param contributionItem The action contribution item.
	 * @throws WidgetNotFoundException Thrown if both values are <code>null</code>.
	 * @throws AssertionFailedException If the contribution item is <code>null</code>.
	 */
	public SWTBotViewMenu(ActionContributionItem contributionItem) throws WidgetNotFoundException {
		Assert.isNotNull(contributionItem);
		Assert.isNotNull(contributionItem.getAction());

		actionItem = contributionItem;
		action = actionItem.getAction();
		commandID = actionItem.getId();
		text = actionItem.getAction().getText();

		if (commandID == null)
			commandID = actionItem.getAction().getActionDefinitionId();
	}

	/**
	 * Simulates the click action of the menu.
	 *
	 * @throws WidgetNotFoundException Thrown if the action or command id are not valid.
	 */
	public void click() throws WidgetNotFoundException {
		if (commandID != null) {
			menuClickResult = null;
			final IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);

			UIThreadRunnable.asyncExec(new VoidResult() {
				public void run() {
					try {
						menuClickResult = handlerService.executeCommand(commandID, null);
					} catch (Exception e) {
						throw new RuntimeException("Failed to execute the command - " + commandID, e); //$NON-NLS-1$
					}
				}
			});
		} else if (action != null)
			UIThreadRunnable.asyncExec(new VoidResult() {
				public void run() {
					action.run();
				}
			});
		else
			throw new WidgetNotFoundException("There is no action or contribution id to execute.");
	}

	/**
	 * After a click completes, this may be use to access the results returned by the command. If a click had not
	 * previously been done then this value will be <code>null</code>.
	 *
	 * @return The object data from the click or <code>null</code> if a click never occurred.
	 */
	public Object getClickResult() {
		return menuClickResult;
	}

	/**
	 * GEts the text label for the menu item.
	 *
	 * @return The text label.
	 * @throws WidgetNotFoundException Thrown if the action is <code>null</code>.
	 */
	public String getText() throws WidgetNotFoundException {
		return text;
	}

	/**
	 * Gets if the menu item is checked (has a check mark next to it).
	 *
	 * @return <code>true</code> if checked. Otherwise <code>false</code>.
	 */
	public boolean isChecked() {
		if (action != null)
			return action.isChecked();

		// FIXME This needs to find if a contribution item (Command) has been checked...
		return false;
	}
}
