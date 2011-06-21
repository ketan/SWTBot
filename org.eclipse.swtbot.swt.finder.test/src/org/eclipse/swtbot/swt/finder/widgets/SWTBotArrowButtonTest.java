/*******************************************************************************
 * Copyright (c) 2008,2010 Ketan Padegaonkar and others.
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
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.utils.SWTUtils.hasStyle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotArrowButtonTest extends AbstractSWTTestCase {

	private long	oldTimeout;

	@Before
	public void lowerTimeout() {
		this.oldTimeout = SWTBotPreferences.TIMEOUT;
		SWTBotPreferences.TIMEOUT = 2000;
	}

	@After
	public void resetTimeout() {
		SWTBotPreferences.TIMEOUT = oldTimeout;
	}
	
	@Test
	public void findsArrowButtons() throws Exception {
		assertEquals(Button.class, bot.arrowButton(0).widget.getClass());
		assertTrue(hasStyle(bot.arrowButton(0).widget, SWT.ARROW));
	}

	@Test
	public void clicksArrowButton() throws Exception {
		try {
			List<Text> findControls = new ControlFinder().findControls(widgetOfType(Text.class));
			SWTBotText text = new SWTBotText(findControls.get(0));
			text.setText("");
			assertFalse(bot.checkBox("Listen").isChecked());
			bot.checkBox("Listen").click();
			assertTrue(bot.checkBox("Listen").isChecked());
			bot.arrowButton(0).click();
			assertTextContains("Selection [13]: SelectionEvent{Button {}", text);
		} finally {
			bot.checkBox("Listen").click();
			bot.button("Clear").click();
		}
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Button").activate();
		bot.radio("SWT.ARROW").click();
	}

}
