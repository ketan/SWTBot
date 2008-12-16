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
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotToolbarButtonTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotToolbarButtonTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsToolBarPushButtons() throws Exception {
		bot.toolbarButton("Push");
	}

	public void testFindsToolBarButtonWithIndex() throws Exception {
		SWTBotToolbarButton button0 = bot.toolbarButton("Push");
		SWTBotToolbarButton button1 = bot.toolbarButton("Push", 1);
		assertNotSameWidget(button0.widget, button1.widget);
	}

	public void testFindsToolBarRadioButtons() throws Exception {
		bot.toolbarButton("Radio");
	}

	public void testFindsToolBarCheckButtons() throws Exception {
		bot.toolbarButton("Check");
	}

	public void testClicksToolBarButton() throws Exception {
		try{
			bot.checkBox("Listen").select();
			SWTBotToolbarButton button = bot.toolbarButtonWithTooltip("SWT.PUSH");
			button.click();
			assertTextContains("Selection [13]: SelectionEvent{ToolItem ", bot.textInGroup("Listeners").widget);
		}finally{
			bot.checkBox("Listen").deselect();
		}
	}

	public void testIsEnabled() throws Exception {
		final SWTBotToolbarButton button = bot.toolbarButton("Radio");
		assertTrue(button.isEnabled());
		try{
			UIThreadRunnable.syncExec(display, new VoidResult(){
				public void run() {
					button.widget.setEnabled(false);
				}
			});
			assertFalse(button.isEnabled());
		}finally{
			UIThreadRunnable.syncExec(display, new VoidResult(){
				public void run() {
					button.widget.setEnabled(true);
				}
			});
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("ToolBar").activate();
	}
}
