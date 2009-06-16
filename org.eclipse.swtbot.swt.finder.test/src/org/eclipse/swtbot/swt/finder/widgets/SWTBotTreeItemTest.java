/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Patel - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259720
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static junit.framework.Assert.assertEquals;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swtbot.swt.finder.exceptions.AssertionFailedException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.utils.TableRow;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ketan Patel
 * @version $Id$
 */
public class SWTBotTreeItemTest extends AbstractSWTTestCase {

	private SWTBotTree	tree;
	private SWTBotText	listeners;

	@Test
	public void canRightClickOnANode() throws Exception {
		SWTBotTreeItem node = tree.expandNode("Node 2").expandNode("Node 2.2").expandNode("Node 2.2.1");
		bot.button("Clear").click();
		node.contextMenu("getItem(Point) on mouse coordinates").click();
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("MenuDetect [35]: Event {type=35 Tree {}", listeners);
		assertTextContains("getItem(Point(Point {", listeners);
	}

	@Test
	public void canFindANode() throws Exception {
		SWTBotTreeItem node = tree.expandNode("Node 2");
		assertText("Node 2.2", node.getNode("Node 2.2"));
		assertText("Node 2.2", node.getNode("Node 2.2", 0));
	}

	@Test
	public void canCallGetItems() throws Exception {
		SWTBotTreeItem node = tree.expandNode("Node 2");
		SWTBotTreeItem[] items = node.getItems();
		assertEquals(2, items.length);
	}

	@Test
	public void cannotFindANodeWithIncorrectNodeIndex() throws Exception {
		SWTBotTreeItem node = tree.expandNode("Node 2");
		try {
			node.getNode("Node 2.2", 1);
			fail("Was expecting an AssertionFailedException");
		} catch (AssertionFailedException e) {
			assertEquals("assertion failed: The index (1) was more than the number of nodes (1) in the tree.", e.getMessage());
		}
	}

	@Test
	public void checkingATreeThatDoesNotHaveCheckStyleBitsThrowsException() throws Exception {
		try {
			tree.getTreeItem("Node 2").check();
			fail("Expecting an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("The tree does not have the style SWT.CHECK", e.getMessage());
		}
	}

	@Test
	public void canSelectAListOfNodes() throws Exception {
		bot.radio("SWT.MULTI").click();
		tree = bot.tree();
		SWTBotTreeItem node2 = tree.getTreeItem("Node 2").expand();
		bot.button("Clear").click();
		node2.select("Node 2.1", "Node 2.2");
		assertTrue(node2.getNode("Node 2.1").isSelected());
		assertTrue(node2.getNode("Node 2.2").isSelected());
	}

	@Test
	public void canCheckANode() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		tree = bot.tree();
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		item.check();
		assertTrue(tree.getTreeItem("Node 2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("data=null item=TreeItem {Node 2} detail=32", listeners);
	}

	@Test
	public void canUnCheckANode() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		tree = bot.tree();
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		item.uncheck();
		assertFalse(tree.getTreeItem("Node 2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("data=null item=TreeItem {Node 2} detail=32", listeners);
	}

	@Test
	public void canToggleANode() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		tree = bot.tree();
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		assertFalse(item.isChecked());
		item.toggleCheck();
		assertTrue(item.isChecked());
		item.toggleCheck();
		assertFalse(item.isChecked());
	}

	@Test
	public void getsRowCount() throws Exception {
		assertEquals(2, tree.getTreeItem("Node 2").rowCount());
		assertEquals(1, tree.getTreeItem("Node 3").rowCount());
		assertEquals(0, tree.getTreeItem("Node 4").rowCount());
		assertTrue(tree.hasItems());
	}

	@Test
	public void getsColumnTextBasedOnColumnNumbers() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		SWTBotTreeItem item = tree.getTreeItem("Node 1");

		assertEquals("Node 1", item.cell(0));
		assertEquals("classes", item.cell(1));
		assertEquals("today", item.cell(3));
		assertEquals("0", item.cell(2));
	}

	@Test
	public void getsColumnTextBasedOnRowColumnNumbers() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		SWTBotTreeItem item = tree.getTreeItem("Node 2");

		assertEquals("2556", item.cell(0, 2));
		assertEquals("Node 2.1", item.cell(0, 0));
		assertEquals("tomorrow", item.cell(1, 3));
	}

	@Test
	public void getsRow() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		SWTBotTreeItem item = tree.getTreeItem("Node 3");
		TableRow row = item.row();
		assertEquals(4, row.columnCount());
		assertEquals("Node 3", row.get(0));
		assertEquals("images", row.get(1));
		assertEquals("91571", row.get(2));
		assertEquals("yesterday", row.get(3));
	}

	@Test
	public void getNodeBasedOnIndex() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		SWTBotTreeItem testNode = item.getNode(0);
		assertEquals("Node 2.1", testNode.getText());

		testNode = item.getNode(1);
		assertEquals("Node 2.2", testNode.getText());

		testNode = testNode.getNode(0);
		assertEquals("Node 2.2.1", testNode.getText());

		try {
			assertEquals(0, testNode.rowCount());
			testNode.getNode(1);
			fail("Expected IllegalArgumentException since 'Node 2.2.1' does not have row at index (1)");
		} catch (IllegalArgumentException e) {
			String expected = "The row number (1) is more than the number of rows(0) in the tree.";
			assertEquals(expected, e.getMessage());
		}
	}

	@Test
	public void cellOutOfRangeWithMultipleColumnsTree() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");

		assertEquals(4, tree.columnCount());
		runCellOutOfRangeTest(tree.getTreeItem("Node 1"), 4);
	}

	private void runCellOutOfRangeTest(SWTBotTreeItem testNode, int columnNumber) throws Exception {
		try {
			testNode.cell(columnNumber);
			fail("Expected IllegalArgumentException since '" + testNode.getText() + "' does not have column at index (" + columnNumber
					+ ")");
		} catch (IllegalArgumentException e) {
			String expected = "The column index (" + columnNumber + ") is more than the number of column(" + tree.columnCount()
					+ ") in the tree.";
			assertEquals(expected, e.getMessage());
		}
	}

	@Test
	public void cellOutOfRangeWithSingleColumnsTree() throws Exception {
		bot.checkBox("Multiple Columns").deselect();
		tree = bot.treeInGroup("Tree");

		assertEquals(0, tree.columnCount());
		runCellOutOfRangeTest(tree.getTreeItem("Node 1"), 1);
	}
	
	@Test
	public void canDoubleClickOnANode() throws Exception {
		SWTBotTreeItem treeItem = tree.getTreeItem("Node 2");

		treeItem.doubleClick();

		assertTextContains("MouseDown [3]: MouseEvent{Tree {} ", listeners);
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("item=TreeItem {Node 2}", listeners);
		assertTextContains("MouseDoubleClick [8]: MouseEvent{Tree {} ", listeners);
		assertTextContains("DefaultSelection [14]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("MouseUp [4]: MouseEvent{Tree {} ", listeners);
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Tree").activate();
		bot.checkBox("Horizontal Fill").select();
		bot.checkBox("Vertical Fill").select();
		bot.checkBox("Popup Menu").select();
		bot.checkBox("Listen").deselect();
		bot.checkBox("SWT.CHECK").deselect();
		bot.radio("SWT.SINGLE").click();
		bot.checkBox("Listen").select();
		tree = bot.tree();
		listeners = bot.textInGroup("Listeners");
		bot.button("Clear").click();
	}
}
