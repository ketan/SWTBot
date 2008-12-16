/*******************************************************************************
 * Copyright (c) 2008 http://www.inria.fr/ and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     http://www.inria.fr/ - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Vincent MAHE &lt;vmahe [at] free [dot]fr&gt;
 * @version $Id$
 */
public class SWTBotTableItemTest extends AbstractSWTTestCase {

	private SWTBotTable	table;
	private SWTBotText	listeners;

	public void testCanRightClickOnALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		bot.button("Clear").click();
		line.contextMenu("getItem(Point) on mouse coordinates").click();
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
		assertTextContains("MenuDetect [35]: Event {type=35 Table {}", listeners);
		assertTextContains("getItem(Point(Point {", listeners);
	}

	public void testCanFindALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		assertText("Index:2", line);
	}

	public void testCanGetLineText() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		assertEquals("Index:2", line.getText());
	}

	public void testCheckingATableThatDoesNotHaveCheckStyleBitsThrowsException() throws Exception {
		try {
			table.getTableItem("Index:2").check();
			fail("Expecting an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("The table does not have the style SWT.CHECK", e.getMessage());
		}
	}

	public void testCanCheckALine() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		table = bot.table();
		SWTBotTableItem item = table.getTableItem("Index:2");
		item.check();
		assertTrue(table.getTableItem("Index:2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
		assertTextContains("data=null item=TableItem {Index:2} detail=32", listeners);
	}

	public void testCanUnCheckALine() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		table = bot.table();
		SWTBotTableItem item = table.getTableItem("Index:2");
		item.uncheck();
		assertFalse(table.getTableItem("Index:2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
		assertTextContains("data=null item=TableItem {Index:2} detail=32", listeners);
	}

	public void testCanToggleALine() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		table = bot.table();
		SWTBotTableItem line = table.getTableItem("Index:2");
		assertFalse(line.isChecked());
		line.toggleCheck();
		assertTrue(line.isChecked());
		line.toggleCheck();
		assertFalse(line.isChecked());
	}

	public void testCanSelectALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		bot.button("Clear").click();
		line.select();
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
	}

	public void testCanClickALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		bot.button("Clear").click();
		line.click();
		assertTextContains("MouseDown [3]: MouseEvent{Table {} ", listeners);
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Table").activate();
		bot.checkBox("Horizontal Fill").select();
		bot.checkBox("Vertical Fill").select();
		bot.checkBox("Popup Menu").select();
		bot.checkBox("Listen").deselect();
		bot.checkBox("SWT.CHECK").deselect();
		bot.checkBox("Listen").select();
		table = bot.table();
		listeners = bot.textInGroup("Listeners");
	}
}
