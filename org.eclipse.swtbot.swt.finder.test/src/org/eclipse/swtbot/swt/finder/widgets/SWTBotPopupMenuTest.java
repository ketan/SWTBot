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

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertNotSameWidget;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertSameWidget;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotPopupMenuTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotShell	popupShell;
	private SWTBotShell	activeShell;

	@Test
	public void findsMenus() throws Exception {
		assertNotNull(popupShell.contextMenu("Push").widget);
		assertSameWidget(popupShell.contextMenu("Push").widget, popupShell.contextMenu("Push").widget);
	}

	@Test
	public void findsSubMenus() throws Exception {
		SWTBotMenu cascade = popupShell.contextMenu("Cascade");
		assertNotSameWidget(cascade.menu("Push").widget, popupShell.contextMenu("Push").widget);
		assertNotSameWidget(cascade.menu("Cascade").menu("Push").widget, popupShell.contextMenu("Push").widget);
	}

	@Test
	public void clicksMenu() throws Exception {
		tearDown();
		activeShell.activate();
		bot.checkBox("Listen").select();
		setUp();
		popupShell.contextMenu("Push").click();
		activeShell.activate();
		String text = bot.textInGroup("Listeners").getText();
		// FIXME https://bugs.eclipse.org/bugs/show_bug.cgi?id=209752
		// FIXED > 071114
		String expectedLinux = "Show [22]: MenuEvent{Menu {Push, , Check, Radio1, Radio2, Cascade} time=";
		String expectedWindows = "Show [22]: MenuEvent{Menu {Push, |, Check, Radio1, Radio2, Cascade} time=";
		assertThat(text, anyOf(containsString(expectedWindows), containsString(expectedLinux)));
		assertTextContains("Selection [13]: SelectionEvent{MenuItem {Push} time=", bot.textInGroup("Listeners").widget);
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		activeShell = bot.activeShell();
		bot.tabItem("Menu").activate();

		bot.checkBox("Listen").select();

		bot.checkBox("SWT.CASCADE").select();
		bot.checkBox("SWT.CHECK").select();
		bot.checkBox("SWT.PUSH").select();
		bot.checkBox("SWT.RADIO").select();
		bot.checkBox("SWT.SEPARATOR").select();

		bot.checkBox("Sub-Menu").select();
		bot.checkBox("Sub-Sub-Menu").select();
		bot.checkBox("SWT.BAR").select();

		bot.button("Create Shell").click();
		popupShell = bot.shell("Title:0");
		popupShell.activate();
	}

	public void tearDown() throws Exception {
		popupShell.close();
		bot.button("Close All Shells").click();
		bot.checkBox("Listen").deselect();

		bot.checkBox("Listen").deselect();

		bot.checkBox("SWT.CASCADE").deselect();
		bot.checkBox("SWT.CHECK").deselect();
		bot.checkBox("SWT.PUSH").deselect();
		bot.checkBox("SWT.RADIO").deselect();
		bot.checkBox("SWT.SEPARATOR").deselect();

		bot.checkBox("Sub-Sub-Menu").deselect();
		bot.checkBox("Sub-Menu").deselect();
		bot.checkBox("SWT.BAR").deselect();

		super.tearDown();
	}
}
