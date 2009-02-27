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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotCommand;
import org.junit.Test;

/**
 * Tests the command finder.
 *
 * @author @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 */
public class CommandFinderTest {

	private SWTEclipseBot bot = new SWTEclipseBot();

	@Test
	public void findCommandIMatcher() throws Exception {
		CommandFinder finder = new CommandFinder();

		List l = finder.findCommand(equalTo("Contribution Item Command"));

		assertNotNull(l);
		assertEquals(1, l.size());

		SWTBotCommand command = (SWTBotCommand) l.get(0);
		assertEquals("Contribution Item Command", command.getText());

		command.click();

		bot.button("OK").click();
	}

	@Test
	public void findCommandNoMatch() throws Exception {
		CommandFinder finder = new CommandFinder();

		List l = finder.findCommand(withMnemonic("BadMatchName"));

		assertNotNull(l);
		assertEquals(0, l.size());
	}
}
