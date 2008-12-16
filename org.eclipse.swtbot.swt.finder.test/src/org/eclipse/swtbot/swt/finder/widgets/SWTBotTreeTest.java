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
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=10
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.ArrayList;


import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.utils.TableRow;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotTreeTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotTreeTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotTree	tree;

	public void testFindsTable() throws Exception {
		assertEquals(Tree.class, tree.widget.getClass());
	}

	public void testGetsRowCount() throws Exception {
		assertEquals(4, tree.rowCount());
		assertTrue(tree.hasItems());
	}

	public void testGetsColumnCount() throws Exception {
		assertEquals(0, tree.columnCount());
	}

	public void testGetsColumnCountOnMultiColumnTree() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		assertEquals(4, tree.columnCount());
	}

	public void testGetsAllColumnHeadings() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Name");
		arrayList.add("Type");
		arrayList.add("Size");
		arrayList.add("Modified");

		assertEquals(arrayList, tree.columns());
	}

	public void testGetsColumnTextBasedOnRowColumnNumbers() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");

		assertEquals("2556", tree.cell(1, 2));
		assertEquals("Node 2", tree.cell(1, 0));
		assertEquals("today", tree.cell(3, 3));
	}

	public void testGetsTextCellBasedOnRowNumberColumnText() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");

		assertEquals("2556", tree.cell(1, "Size"));
		assertEquals("Node 2", tree.cell(1, "Name"));
		assertEquals("today", tree.cell(3, "Modified"));
	}

	public void testGetsSelectionCount() throws Exception {
		assertEquals(0, tree.selection().columnCount());
		assertEquals(0, tree.selection().rowCount());
		assertEquals(0, tree.selectionCount());
	}

	public void testSetsSelection() throws Exception {
		tree.select(3);
		assertEquals(1, tree.selectionCount());
	}

	public void testSetsMultipleSelection() throws Exception {
		bot.radio("SWT.MULTI").click();
		tree = bot.treeInGroup("Tree");
		tree.select(new String[] { "Node 2", "Node 4" });
		assertEquals(2, tree.selectionCount());
		TableCollection selection = tree.selection();
		assertEquals("Node 2", selection.get(0, 0));
		assertEquals("Node 4", selection.get(1, 0));
	}

	public void testUnselectsSelection() throws Exception {
		tree.unselect();
		assertEquals(0, tree.selectionCount());
	}

	public void testGetsSingleSelection() throws Exception {
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		tree.select(2);
		TableCollection selection = tree.selection();
		assertEquals(1, selection.rowCount());
		assertEquals(4, selection.columnCount());
		assertEquals(new TableCollection().add(new TableRow(new String[] { "Node 3", "images", "91571", "yesterday" })), selection);
	}

	public void testFindsAnyTree() throws Exception {
		assertSameWidget(tree.widget, bot.tree().widget);
	}

	public void testGetsMultipleSingleSelection() throws Exception {
		bot.radio("SWT.MULTI").click();
		bot.checkBox("Multiple Columns").select();
		tree = bot.treeInGroup("Tree");
		tree.select(new int[] { 1, 2 });

		TableCollection selection = tree.selection();
		assertEquals("Node 2", selection.get(0, 0));
		assertEquals("databases", selection.get(0, 1));
		assertEquals("2556", selection.get(0, 2));
		assertEquals("tomorrow", selection.get(0, 3));

		assertEquals(new TableRow(new String[] { "Node 3", "images", "91571", "yesterday" }), selection.get(1));
	}

	public void testExpandsNode() throws Exception {
		bot.checkBox("Multiple Columns").select();
		bot.checkBox("Listen").select();
		Widget notifications = bot.textInGroup("Listeners").widget;
		tree = bot.treeInGroup("Tree");
		tree.expandNode("Node 2");
		assertEquals(6, tree.visibleRowCount());
		assertTextContains("Expand [17]: TreeEvent{Tree {} time=", notifications);
		assertTextContains("data=null item=TreeItem {Node 2} detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=null doit=true}",
				notifications);
	}

	public void testExpandsNodeToMax() throws Exception {
		bot.checkBox("Multiple Columns").select();
		bot.checkBox("Listen").select();
		tree = bot.treeInGroup("Tree");
		tree.expandNode("Node 2", true);
		assertEquals(7, tree.visibleRowCount());
		assertTextContains("data=null item=TreeItem {Node 2} detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=null doit=true}", bot
				.textInGroup("Listeners").widget);
		assertTextContains("data=null item=TreeItem {Node 2.2} detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=null doit=true}", bot
				.textInGroup("Listeners").widget);
	}

	public void testThrowExceptionIfTreeNotFound() throws Exception {
		bot.tabItem("Button").activate();
		try {
			bot.tree();
			fail("Expected a WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			pass();
		}
	}

	public void testGetsAllTreeItems() throws Exception {
		SWTBotTreeItem[] items = tree.getAllItems();
		for (int i = 0; i < items.length; i++)
			assertEquals("Node " + (i + 1), items[i].getText());
	}

	public void testClicksOnANode() throws Exception {
		bot.checkBox("Listen").select();
		SWTBotTreeItem node = bot.tree().expandNode("Node 3").expandNode("Node 3.1");
		bot.button("Clear").click();
		node.click();
		SWTBotText listener = bot.textInGroup("Listeners");
		assertTextContains("MouseDown [3]: MouseEvent{Tree {}", listener);
		assertTextContains("Selection [13]: SelectionEvent{Tree {}", listener);
		assertTextContains("item=TreeItem {Node 3.1}", listener);
		assertTextContains("MouseUp [4]: MouseEvent{Tree {}", listener);
	}

	public void testDoubleClicksOnANode() throws Exception {
		bot.checkBox("Listen").select();
		SWTBotTreeItem node = bot.tree().expandNode("Node 3").expandNode("Node 3.1");
		bot.button("Clear").click();
		node.doubleClick();
		SWTBotText listener = bot.textInGroup("Listeners");

		assertTextContains("MouseDown [3]: MouseEvent{Tree {}", listener);
		assertTextContains("Selection [13]: SelectionEvent{Tree {}", listener);
		assertTextContains("item=TreeItem {Node 3.1}", listener);
		assertTextContains("MouseUp [4]: MouseEvent{Tree {}", listener);

		assertTextContains("MouseDoubleClick [8]: MouseEvent{Tree {} ", listener);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("Tree").activate();
		bot.checkBox("Listen").deselect();
		bot.button("Clear").click();
		bot.checkBox("Horizontal Fill").select();
		bot.checkBox("Vertical Fill").select();
		bot.checkBox("Header Visible").select();
		bot.checkBox("Multiple Columns").deselect();
		tree = bot.treeInGroup("Tree");
	}
}
