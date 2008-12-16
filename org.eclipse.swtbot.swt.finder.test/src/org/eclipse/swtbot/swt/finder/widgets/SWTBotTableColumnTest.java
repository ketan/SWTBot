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


import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotTableColumnTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotTableColumnTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotTable	table;

	public void testFindsTableColumn() throws Exception {
		SWTBotTableColumn header = table.header("Name");
		assertText("Name", header.widget);
		assertEquals(TableColumn.class, header.widget.getClass());
	}

	public void testClicksTableColumn() throws Exception {
		SWTBotTableColumn header = table.header("Name");
		header.click();

		Text text = bot.textInGroup("Listeners").widget;

		assertTextContains("Selection [13]: SelectionEvent{TableColumn {Name}", text);
		assertTextContains("MouseUp [4]: MouseEvent{Table {}", text);
		assertTextContains("data=null button=1 stateMask=524288 x=0 y=0 count=1}", text);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("Table").activate();
		bot.radio("SWT.MULTI").click();
		bot.checkBox("Header Visible").select();
		bot.checkBox("Listen").select();
		bot.button("Clear").click();
		table = bot.tableInGroup("Table");
	}

	protected void tearDown() throws Exception {
		bot.checkBox("Listen").deselect();
		super.tearDown();
	}
}
