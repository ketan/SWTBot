/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
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


import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotShellTest extends AbstractSWTTestCase {

	private SWTBot	bot;
	private Shell	shell3;
	private Shell	shell2;
	private Shell	shell4;

	public void testFindsShell() throws Exception {
		assertSameWidget(menuShell, bot.shell("Address Book - Untitled").widget);
	}

	public void testBringsShellToFront() throws Exception {
		SWTBotShell shell2 = bot.shell("shell2");
		shell2.activate();
		Shell activeShell = new ControlFinder().activeShell();
		assertSameWidget(activeShell, shell2.widget);
	}

	public void testNoShellReturnsNull() throws Exception {
		try {
			new  SWTBot().shell("non existing");
			fail("Was expecting an exception");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	public void testClosesShell() throws Exception {
		UIThreadRunnable.asyncExec(display, new VoidResult() {
			public void run() {
				Shell shell = new Shell(controlShell);
				shell.setText("some shell");
				shell.open();
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

	public void testGetsActiveShell() throws Exception {
		SWTBotShell shell2 = bot.shell("shell2");
		shell2.activate();

		SWTBotShell activeShell = bot.activeShell();
		assertSameWidget(shell2.widget, activeShell.widget);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		UIThreadRunnable.asyncExec(display, new VoidResult() {
			public void run() {
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
		});
	}

	protected void tearDown() throws Exception {
		new SWTBotShell(shell2).close();
		new SWTBotShell(shell3).close();
		new SWTBotShell(shell4).close();
		UIThreadRunnable.syncExec(display, new VoidResult() {
			public void run() {
				shell2.dispose();
				shell3.dispose();
				shell4.dispose();
			}
		});
		super.tearDown();
	}

}
