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

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertSameWidget;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.pass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.test.BaseSWTShellTest;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotShellTest extends BaseSWTShellTest {

	private static final String TITLE = "Address Book - Untitled";
	private Shell	shell3;
	private Shell	shell2;
	private Shell	shell4;

	@Override
	protected void createUI(Composite parent) {
		shell.setText(TITLE);

		shell4 = new Shell(display);
		shell4.setText("shell4");
		shell4.open();

		shell2 = new Shell(shell4);
		shell2.setText("shell2");
		shell2.open();

		shell3 = new Shell(display);
		shell3.setText("shell3");
		shell3.open();
	}

	@Test
	public void findsShell() throws Exception {
		assertSameWidget(shell, bot.shell(TITLE).widget);
	}

	@Test
	public void bringsShellToFront() throws Exception {
		SWTBotShell shell2 = bot.shell("shell2");
		shell2.activate();
		Shell activeShell = new ControlFinder().activeShell();
		assertSameWidget(activeShell, shell2.widget);
	}

	@Test
	public void noShellReturnsNull() throws Exception {
		try {
			new SWTBot().shell("non existing");
			fail("Was expecting an exception");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	@Test
	public void closesShell() throws Exception {
		UIThreadRunnable.asyncExec(display, new VoidResult() {
			public void run() {
				Shell someShell = new Shell(shell);
				someShell.setText("some shell");
				someShell.open();
			}
		});
		bot.shell("some shell").activate();
		bot.shell("some shell").close();
		try {
			bot.shell("some shell");
			fail("Was expecting a WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	@Test
	public void getsActiveShell() throws Exception {
		SWTBotShell shell2 = bot.shell("shell2");
		shell2.activate();

		SWTBotShell activeShell = bot.activeShell();
		assertSameWidget(shell2.widget, activeShell.widget);
	}

	@Test
	public void getsAllShells() throws Exception {
		assertEquals(4, bot.shells().length);
	}

	@Test
	public void findsShellsById() throws Exception {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				shell.setData("foo-shell", "bar");
			}
		});

		assertSame(shell, bot.shellWithId("foo-shell", "bar").widget);
	}

}
