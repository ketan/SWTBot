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
import org.eclipse.core.commands.Command;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotCommand;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.hamcrest.Matcher;

/**
 * Finds all the contribution items within the application.
 *
 * @author @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
public class CommandFinder {
	/**
	 * The logging instance for this class.
	 */
	private static final Logger	log	= Logger.getLogger(CommandFinder.class);

	/**
	 * Creates a CommandFinder.
	 */
	public CommandFinder() {
		// Do nothing.
	}

	/**
	 * Finds a command matching the given item.
	 *
	 * @param matcher the matcher that can match commands.
	 * @return all command that match the matcher.
	 */
	public List<SWTBotCommand> findCommand(Matcher<?> matcher) {
		return findCommand(getCommandService(), matcher);
	}

	/**
	 * Gets a list of all command matching the matcher.
	 *
	 * @param service the {@link ICommandService} instance to use.
	 * @param matcher the matcher that can match the command item.
	 * @return The list of {@link Command}s that match the matcher.
	 */
	public List<SWTBotCommand> findCommand(final ICommandService service, final Matcher<?> matcher) {
		return UIThreadRunnable.syncExec(new ListResult<SWTBotCommand>() {

			public List<SWTBotCommand> run() {
				List<SWTBotCommand> l = new ArrayList<SWTBotCommand>();
				Command[] commands = service.getDefinedCommands();

				for (int i = 0; i < commands.length; i++)
					try {
						String name = commands[i].getName();
						if (matcher.matches(name))
							l.add(new SWTBotCommand(commands[i]));
					} catch (Exception e) {
						log.error("Failed with an exception on the command: " + commands[i].toString(), e);
					}

				return l;
			}
		});
	}

	/**
	 * Gets the command service.
	 *
	 * @return The {@link ICommandService}.
	 */
	protected ICommandService getCommandService() {
		return (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
	}
}
