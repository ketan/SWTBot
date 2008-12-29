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

import static org.hamcrest.Matchers.instanceOf;

import java.util.List;


import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotCheckBoxTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testClicksCheckBox() throws Exception {
		try {
			List findControls = new ControlFinder().findControls(instanceOf(Text.class));
			SWTBotText text = new SWTBotText((Text) findControls.get(0));
			text.setText("");
			assertFalse(bot.checkBox("Listen").isChecked());
			bot.checkBox("Listen").click();
			assertTrue(bot.checkBox("Listen").isChecked());
			bot.button("One").click();
			assertTextContains("Selection [13]: SelectionEvent{Button {One}", text);
		} finally {
			bot.checkBox("Listen").click();
			bot.button("Clear").click();
		}
	}

	public void testDoesNotMatchRadioButtons() throws Exception {

		try {
			assertNull(bot.checkBox("SWT.PUSH").widget);
			fail("Expecting WidgetNotFoundException");
		} catch (WidgetNotFoundException e) {
			pass();
		}
		try {
			assertNull(bot.checkBox("Preferred").widget);
			fail("Expecting WidgetNotFoundException");
		} catch (WidgetNotFoundException e) {
			pass();
		}
	}

	public void testDoesNotMatchRegularButtons() throws Exception {
		try {
			assertNull(bot.checkBox("One").widget);
			fail("Expecting WidgetNotFoundException");
		} catch (WidgetNotFoundException e) {
			pass();
		}
		try {
			assertNull(bot.checkBox("Change").widget);
			fail("Expecting WidgetNotFoundException");
		} catch (WidgetNotFoundException e) {
			pass();
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("Button").activate();
	}
}
