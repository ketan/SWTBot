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

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

public class SWTBotToolbarButtonWithToolTipTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsToolBarButtonWithToolTipBasedOnIndex() throws Exception {
		SWTBotToolbarButton button = bot.toolbarButtonWithTooltip("SWT.PUSH", 0);
		assertEquals("", button.getText());

		button = bot.toolbarButtonWithTooltip("SWT.PUSH", 1);
		assertEquals("", button.getText());

		button = bot.toolbarButtonWithTooltip("SWT.PUSH", 2);
		assertEquals("Push", button.getText());

		button = bot.toolbarButtonWithTooltip("SWT.PUSH", 3);
		assertEquals("Push", button.getText());
	}

	public void testFindsToolBarButtonWithToolTip() throws Exception {
		for (int i = 0; i < 6; i++) {
			bot.toolbarButtonWithTooltip("SWT.PUSH", i);
		}
	}

	public void testFindsToolBarButtonWithToolTip2() throws Exception {
		for (int i = 0; i < 6; i++) {
			bot.toolbarButtonWithTooltip("SWT.RADIO", i);
		}
	}

	public void testGetsTooltipOnButton() throws Exception {
		SWTBotToolbarButton button = bot.toolbarButtonWithTooltip("SWT.RADIO");
		String toolTipText = button.getToolTipText();
		assertEquals("SWT.RADIO", toolTipText);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("ToolBar").activate();
	}

}
