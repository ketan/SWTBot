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
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.pass;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotCheckBoxTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void clicksCheckBox() throws Exception {
		try {
			List<Text> findControls = new ControlFinder().findControls(widgetOfType(Text.class));
			SWTBotText text = new SWTBotText(findControls.get(0));
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

	@Test
	public void doesNotMatchRadioButtons() throws Exception {

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

	@Test
	public void doesNotMatchRegularButtons() throws Exception {
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

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("Button").activate();
	}
}
