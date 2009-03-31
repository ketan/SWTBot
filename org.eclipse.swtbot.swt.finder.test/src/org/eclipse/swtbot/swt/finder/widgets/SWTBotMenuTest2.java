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

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

public class SWTBotMenuTest2 extends AbstractSWTTestCase {

	private SWTBotText	listeners;

	@Test
	public void clicksCheckMenus() throws Exception {
		SWTBotMenu menu = bot.menu("Cascade").menu("Check");
		assertFalse(menu.isChecked());
		menu.click();
		assertTrue(menu.isChecked());
		assertTextContains("Selection [13]: SelectionEvent{MenuItem {&Check	Ctrl+Shift+C}", listeners);
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Menu").activate();
		listeners = bot.textInGroup("Listeners");

		bot.checkBox("SWT.BAR").select();
		bot.checkBox("SWT.DROP_DOWN").select();

		bot.checkBox("SWT.CASCADE").select();
		bot.checkBox("SWT.CHECK").select();
		bot.checkBox("SWT.PUSH").select();
		bot.checkBox("SWT.RADIO").select();
		bot.checkBox("SWT.SEPARATOR").select();

		bot.checkBox("Images").select();
		bot.checkBox("Accelerators").select();
		bot.checkBox("Mnemonics").select();
		bot.checkBox("Sub-Menu").select();
		bot.checkBox("Sub-Sub-Menu").select();

		bot.checkBox("Listen").select();
		bot.button("Clear").click();
		bot.button("Create Shell").click();
		bot.shell("Title:0").activate();
	}

	public void tearDown() throws Exception {
		bot.shell("SWT Controls").activate();
		bot.button("Close All Shells").click();

		bot.checkBox("SWT.BAR").deselect();
		bot.checkBox("SWT.DROP_DOWN").deselect();

		bot.checkBox("SWT.CASCADE").deselect();
		bot.checkBox("SWT.CHECK").deselect();
		bot.checkBox("SWT.PUSH").deselect();
		bot.checkBox("SWT.RADIO").deselect();
		bot.checkBox("SWT.SEPARATOR").deselect();

		bot.checkBox("Images").deselect();
		bot.checkBox("Accelerators").deselect();
		bot.checkBox("Mnemonics").deselect();
		bot.checkBox("Sub-Sub-Menu").deselect();
		bot.checkBox("Sub-Menu").deselect();

		super.tearDown();
	}
}
