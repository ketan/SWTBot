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

import java.util.ArrayList;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.utils.TableRow;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTableTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotTable	table;

	public void testFindsTable() throws Exception {
		assertEquals(Table.class, table.widget.getClass());
	}

	public void testGetsRowCount() throws Exception {
		assertEquals(16, table.rowCount());
	}

	public void testGetsColumnCount() throws Exception {
		assertEquals(4, table.columnCount());
	}

	public void testGetsAllColumnHeadings() throws Exception {
		bot.checkBox("Header Visible").click();
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Name");
		arrayList.add("Type");
		arrayList.add("Size");
		arrayList.add("Modified");
		assertEquals(arrayList, table.columns());
	}

	public void testGetsColumnTextBasedOnRowColumnNumbers() throws Exception {
		assertEquals("2556", table.cell(1, 2));
		assertEquals("2556", table.cell(4, 2));
		assertEquals("2556", table.cell(7, 2));

		assertEquals("Index:5", table.cell(5, 0));
		assertEquals("tomorrow", table.cell(7, 3));
	}

	public void testGetsTextCellBasedOnRowNumberColumnText() throws Exception {
		assertEquals("2556", table.cell(1, "Size"));
		assertEquals("2556", table.cell(4, "Size"));
		assertEquals("2556", table.cell(7, "Size"));

		assertEquals("Index:5", table.cell(5, "Name"));
		assertEquals("tomorrow", table.cell(7, "Modified"));
	}

	public void testGetsSelectionCount() throws Exception {
		TableCollection selection = table.selection();
		assertEquals(new TableCollection(), selection);
		assertEquals(0, selection.columnCount());
		assertEquals(0, selection.rowCount());
		assertEquals(0, table.selectionCount());
	}

	public void testSetsSelection() throws Exception {
		table.select(5);
		assertEquals(1, table.selectionCount());
	}

	public void testSettingSelectionOnInvalidRowTextThrowsException() throws Exception {
		table.select("Index:15");
		try {
			table.select("Index:16");
			fail("Expecting an exception");
		} catch (Exception e) {
			assertEquals("Could not find item:Index:16 in table", e.getMessage());
		}
	}

	public void testSettingSelectionOnInvalidRowNumberThrowsException() throws Exception {
		table.select(15);
		try {
			table.select(16);
			fail("Expecting an exception");
		} catch (Exception e) {
			assertEquals("The row number: 16 does not exist in the table", e.getMessage());
		}
	}

	public void testUnselectsSelection() throws Exception {
		table.unselect();
		assertEquals(0, table.selectionCount());
	}

	public void testGetsSingleSelection() throws Exception {
		table.select(10);
		TableCollection selection = table.selection();
		assertEquals(1, selection.rowCount());
		assertEquals(4, selection.columnCount());
		assertEquals(new TableCollection().add(new TableRow(
				new String[] { "Index:10", "databases", "2556", "tomorrow" })), selection);
	}

	public void testFindsAnyTable() throws Exception {
		assertSameWidget(table.widget, bot.table().widget);
	}

	public void testGetsMultipleSingleSelection() throws Exception {
		bot.radio("SWT.MULTI").click();
		table = bot.tableInGroup("Table");
		table.select(new int[] { 5, 10 });

		TableCollection selection = table.selection();
		assertEquals("Index:5", selection.get(0, 0));
		assertEquals("images", selection.get(0, 1));
		assertEquals("91571", selection.get(0, 2));
		assertEquals("yesterday", selection.get(0, 3));

		assertEquals(new TableRow(new String[] { "Index:10", "databases", "2556", "tomorrow" }), selection.get(1));
	}

	public void testSettingMultipleSelectionOfInvalidIndicesThrowsException() throws Exception {
		bot.radio("SWT.MULTI").click();
		table = bot.tableInGroup("Table");
		table.select(new int[] { 4, 15, 6 });
		try {
			table.select(new int[] { 4, 16, 6 });
			fail("Expecting an exception");
		} catch (Exception e) {
			assertEquals("The row number: 16 does not exist in the table", e.getMessage());
		}
	}

	public void testThrowsExceptionIfTheRowNumberIsIllegal() throws Exception {
		try {
			table.cell(100, 1);
			fail("Was expecting an exception");
		} catch (Exception e) {
			assertEquals("The row number (100) is more than the number of rows(16) in the table.", e.getMessage());
		}
	}

	public void testThrowsExceptionIfTheRowNumberIsIllegal2() throws Exception {
		try {
			table.cell(100, "Size");
			fail("Was expecting an exception");
		} catch (Exception e) {
			assertEquals("The row number (100) is more than the number of rows(16) in the table.", e.getMessage());
		}
	}

	public void testThrowsExceptionIfTheColumnNumberIsIllegal() throws Exception {
		try {
			table.cell(1, 100);
			fail("Was expecting an exception");
		} catch (Exception e) {
			assertEquals("The column number (100) is more than the number of column(4) in the table.", e.getMessage());
		}
	}

	public void testThrowsExceptionIfTheColumnNameIsIllegal() throws Exception {
		try {
			table.cell(1, "some column");
			fail("Was expecting an exception");
		} catch (Exception e) {
			assertEquals("The column `some column' is not found.", e.getMessage());
		}
	}

	public void testGetsIndexOfItem() throws Exception {
		assertEquals(7, table.indexOf("Index:7"));
		assertEquals(-1, table.indexOf("non existent item"));
	}

	public void testGetsIndexOfItemInAColumnWithIndex() throws Exception {
		assertEquals(7, table.indexOf("Index:7", 0));
		assertEquals(1, table.indexOf("2556", 2));
		assertEquals(-1, table.indexOf("non existent item", 3));

	}

	public void testGetsIndexOfItemInAColumnWithText() throws Exception {
		bot.radio("SWT.MULTI").click();
		table = bot.tableInGroup("Table");

		assertEquals(7, table.indexOf("Index:7", "Name"));
		assertEquals(1, table.indexOf("2556", "Size"));
		assertEquals(-1, table.indexOf("non existent item", "foo"));
	}

	public void testFindsIndexOfColumn() throws Exception {
		bot.radio("SWT.MULTI").click();
		table = bot.tableInGroup("Table");

		assertEquals(0, table.indexOfColumn("Name"));
		assertEquals(2, table.indexOfColumn("Size"));
	}

	public void testSelectsItemsBasedOnString() throws Exception {
		table.select("Index:9");
		TableCollection selection = table.selection();
		assertEquals("Index:9", selection.get(0, 0));
	}

	public void testFindsItemsBasedOnIndex() throws Exception {
		SWTBotTableItem tableItem = table.getTableItem(3);
		assertEquals("Index:3", tableItem.getText());
	}

	public void testFindsItemsBasedOnText() throws Exception {
		SWTBotTableItem tableItem = table.getTableItem("Index:3");
		assertEquals("Index:3", tableItem.getText());
	}
	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("Table").activate();
		table = bot.tableInGroup("Table");
	}
}
