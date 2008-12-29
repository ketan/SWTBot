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
import org.eclipse.core.runtime.AssertionFailedException;
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
public class SWTBotCommand extends SWTBotViewMenu {
	private IHandlerService	handlerService	= null;

	/**
	 * Constructs a SWTBot command item.
	 *
	 * @param command The command item.
	 * @throws WidgetNotFoundException Thrown if both values are <code>null</code>.
	 * @throws AssertionFailedException If the contribution item is <code>null</code>.
	 */
	public SWTBotCommand(Command command) throws WidgetNotFoundException {
		super(command);

		handlerService = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);
	}

	/**
	 * Simulates the click action of the menu.
	 *
	 * @throws WidgetNotFoundException Thrown if the action or command id are not valid.
	 */
	public void click() throws WidgetNotFoundException {
		if (commandID != null) {
			menuClickResult = null;

			UIThreadRunnable.asyncExec(new VoidResult() {
				public void run() {
					try {
						menuClickResult = handlerService.executeCommand(commandID, null);
					} catch (Exception e) {
						throw new RuntimeException("Failed to execute the command - " + commandID, e); //$NON-NLS-1$
					}
				}
			});
		} else
			throw new WidgetNotFoundException("The command to could not be execute due to the lack of an ID.");
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
	 * Gets the text name for this item.
	 *
	 * @return The text name of this item.
	 * @throws WidgetNotFoundException Thrown if the command name has not been defined.
	 */
	public String getText() throws WidgetNotFoundException {
		try {
			return cmdItem.getName();
		} catch (NotDefinedException e) {
			throw new WidgetNotFoundException(e.getMessage());
		}
	}

	/**
	 * Gets if the command is enabled.
	 *
	 * @return <code>true</code> if enabled. Otherwise <code>false</code>.
	 */
	public boolean isEnabled() {
		return cmdItem.isEnabled();
	}
}
