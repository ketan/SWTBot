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

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Vincent MAHE &lt;vmahe [at] free [dot]fr&gt;
 * @version $Id$
 */
public class SWTBotTableItemTest extends AbstractSWTTestCase {

	private SWTBotTable	table;
	private SWTBotText	listeners;

	@Test
	public void canRightClickOnALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		bot.button("Clear").click();
		line.contextMenu("getItem(Point) on mouse coordinates").click();
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
		assertTextContains("MenuDetect [35]: Event {type=35 Table {}", listeners);
		assertTextContains("getItem(Point(Point {", listeners);
	}

	@Test
	public void canFindALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		assertText("Index:2", line);
	}

	@Test
	public void canGetLineText() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		assertEquals("Index:2", line.getText());
	}

	@Test
	public void checkingATableThatDoesNotHaveCheckStyleBitsThrowsException() throws Exception {
		try {
			table.getTableItem("Index:2").check();
			fail("Expecting an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("The table does not have the style SWT.CHECK", e.getMessage());
		}
	}

	@Test
	public void canCheckALine() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		table = bot.table();
		SWTBotTableItem item = table.getTableItem("Index:2");
		item.check();
		assertTrue(table.getTableItem("Index:2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
		assertTextContains("data=null item=TableItem {Index:2} detail=32", listeners);
	}

	@Test
	public void canUnCheckALine() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		table = bot.table();
		SWTBotTableItem item = table.getTableItem("Index:2");
		item.uncheck();
		assertFalse(table.getTableItem("Index:2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
		assertTextContains("data=null item=TableItem {Index:2} detail=32", listeners);
	}

	@Test
	public void canToggleALine() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		table = bot.table();
		SWTBotTableItem line = table.getTableItem("Index:2");
		assertFalse(line.isChecked());
		line.toggleCheck();
		assertTrue(line.isChecked());
		line.toggleCheck();
		assertFalse(line.isChecked());
	}

	@Test
	public void canSelectALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		bot.button("Clear").click();
		line.select();
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
	}

	@Test
	public void canClickALine() throws Exception {
		SWTBotTableItem line = table.getTableItem("Index:2");
		bot.button("Clear").click();
		line.click();
		assertTextContains("MouseDown [3]: MouseEvent{Table {} ", listeners);
		assertTextContains("Selection [13]: SelectionEvent{Table {} ", listeners);
	}

	public void setUp() throws Exception {
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
