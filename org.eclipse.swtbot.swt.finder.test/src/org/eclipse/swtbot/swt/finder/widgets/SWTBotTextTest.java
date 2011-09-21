/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Paulin - http://swtbot.org/bugzilla/show_bug.cgi?id=36
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;

import org.eclipse.swtbot.swt.finder.test.AbstractControlExampleTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTextTest extends AbstractControlExampleTest {

	@Test
	public void findsTextBoxInGroup() throws Exception {
		try {
			bot.checkBox("Listen").click();
			SWTBotText text = bot.textInGroup("Text");
			assertTextContains("The quick brown fox", text.widget);
			text.setText("hello world");
			assertText("hello world", text.widget);
			assertTextContains("VerifyEvent", bot.textInGroup("Listeners").widget);
			assertTextContains("hello world", bot.textInGroup("Listeners").widget);
		} finally {
			bot.checkBox("Listen").click();
			bot.button("Clear").click();
		}
	}

	@Test
	public void findsTextBoxWithText() throws Exception {
		assertText("The quick brown fox jumps over the lazy dog.\n" + "One Two Three", bot
				.text("The quick brown fox jumps over the lazy dog.\r\n" + "One Two Three").widget);
	}

	@Test
	public void typesText() throws Exception {
		final SWTBotText text = bot.textInGroup("Text");
		text.setText("");

		text.typeText("Type This 123");
		assertTextContains("Type This 123", text.widget);
	}

	@Before
	public void prepareExample() throws Exception {
		bot.tabItem("Text").activate();
	}

}
