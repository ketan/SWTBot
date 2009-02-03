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
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=88
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertNotSameWidget;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.pass;
import static org.junit.Assert.fail;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotToolbarDropDownButtonTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void findsToolBarButtonWithIndex() throws Exception {
		SWTBotToolbarButton button0 = bot.toolbarDropDownButton("Drop Down");
		SWTBotToolbarButton button1 = bot.toolbarDropDownButton("Drop Down", 1);
		assertNotSameWidget(button0.widget, button1.widget);
	}

	@Test
	public void clicksToolBarButton() throws Exception {
		try {
			bot.checkBox("Listen").select();
			SWTBotToolbarButton button = bot.toolbarDropDownButton("Drop Down");
			button.click();
			assertTextContains("Selection [13]: SelectionEvent{ToolItem ", bot.textInGroup("Listeners").widget);
		} finally {
			bot.checkBox("Listen").deselect();
		}
	}

	@Test
	public void clicksADropDownMenuItem() throws Exception {
		SWTBotToolbarDropDownButton button = bot.toolbarDropDownButton("Drop Down");
		try {
			bot.menu("Kiwi");
			fail("The menu item should not exist");
		} catch (WidgetNotFoundException e) {
			pass();
		}
		bot.shell("SWT Controls").activate();
		button.menuItem("Kiwi").click();
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("ToolBar").activate();
	}
}
