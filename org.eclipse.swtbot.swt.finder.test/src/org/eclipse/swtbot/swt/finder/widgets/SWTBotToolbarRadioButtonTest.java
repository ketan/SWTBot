/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
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
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotToolbarRadioButtonTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void findsToolBarButtonWithIndex() throws Exception {
		SWTBotToolbarRadioButton button0 = bot.toolbarRadioButton("Radio");
		SWTBotToolbarRadioButton button1 = bot.toolbarRadioButton("Radio", 1);
		assertNotSameWidget(button0.widget, button1.widget);
	}

	@Test
	public void clicksRadioButton() throws Exception {
		SWTBotToolbarRadioButton button = bot.toolbarRadioButton("Radio");
		button.click();
		assertEventMatches(bot.textInGroup("Listeners"), "Selection [13]: SelectionEvent{ToolItem {Radio} time=-941780677 data=null item=null detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=null doit=true}");
	}

	@Test
	public void togglesRadioButton() throws Exception {
		SWTBotToolbarRadioButton button = bot.toolbarRadioButton("Radio");
		boolean checked = button.isChecked();
		button.toggle();
		assertTrue(checked != button.isChecked());
	}

	@Test
	public void clickingRadioButtonTogglesIt() throws Exception {
		SWTBotToolbarRadioButton button = bot.toolbarRadioButton("Radio");
		boolean checked = button.isChecked();
		button.click();
		assertTrue(checked != button.isChecked());
	}

	@Test
	public void selectsRadioButton() throws Exception {
		SWTBotToolbarRadioButton button = bot.toolbarRadioButton("Radio");
		boolean checked = button.isChecked();
		button.select();
		assertTrue(checked != button.isChecked());
	}

	@Test
	public void deselectsRadioButton() throws Exception {
		SWTBotToolbarRadioButton button = bot.toolbarRadioButton("Radio");
		button.select();
		boolean checked = button.isChecked();
		button.deselect();
		assertTrue(checked != button.isChecked());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("ToolBar").activate();
		bot.checkBox("Listen").select();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		bot.radio("SWT.HORIZONTAL").click(); // reset the ui
		bot.checkBox("Listen").deselect();
	}

}
