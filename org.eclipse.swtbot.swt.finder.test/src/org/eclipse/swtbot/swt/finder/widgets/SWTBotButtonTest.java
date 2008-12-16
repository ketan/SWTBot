/*******************************************************************************
<<<<<<< .working
 * Copyright 2007 new SWTBot, http://swtbot.org/
=======
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
>>>>>>> .merge-right.r1100
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotButtonTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotButtonTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsButtons() throws Exception {
		assertEquals("One", bot.button("One").getText());
		assertEquals("Select Listeners", bot.button("Select Listeners").getText());
	}

	public void testClicksButtons() throws Exception {
		SWTBotShell shell2 = null;
		try {
			SWTBotButton button = bot.button("Set/Get API");
			button.click();
			shell2 = bot.shell("Button Set/Get API");
			assertNotNull(shell2.widget);
			assertEquals("Button Set/Get API", shell2.getText());
		} finally {
			if ((shell2 != null) && (shell2.widget != null))
				shell2.close();
		}
	}

	public void testFindsBackgroundColor() throws Exception {
		assertNotNull(bot.button("One").backgroundColor());
	}

	public void testFindsForegroundColor() throws Exception {
		assertNotNull(bot.button("One").foregroundColor());
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("Button").activate();
	}

}
