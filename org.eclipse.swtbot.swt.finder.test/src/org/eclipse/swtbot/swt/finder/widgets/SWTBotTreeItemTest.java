/*******************************************************************************
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

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTreeItemTest extends AbstractSWTTestCase {

	private SWTBotTree	tree;
	private SWTBotText	listeners;

	public void testCanRightClickOnANode() throws Exception {
		SWTBotTreeItem node = tree.expandNode("Node 2").expandNode("Node 2.2").expandNode("Node 2.2.1");
		bot.button("Clear").click();
		node.contextMenu("getItem(Point) on mouse coordinates").click();
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("MenuDetect [35]: Event {type=35 Tree {}", listeners);
		assertTextContains("getItem(Point(Point {", listeners);
	}

	public void testCanFindANode() throws Exception {
		SWTBotTreeItem node = tree.expandNode("Node 2");
		assertText("Node 2.2", node.getNode("Node 2.2"));
	}

	public void testCheckingATreeThatDoesNotHaveCheckStyleBitsThrowsException() throws Exception {
		try {
			tree.getTreeItem("Node 2").check();
			fail("Expecting an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("The tree does not have the style SWT.CHECK", e.getMessage());
		}
	}

	public void testCanSelectAListOfNodes() throws Exception {
		bot.radio("SWT.MULTI").click();
		tree = bot.tree();
		SWTBotTreeItem node2 = tree.getTreeItem("Node 2").expand();
		bot.button("Clear").click();
		node2.select("Node 2.1", "Node 2.2");
		assertTrue(node2.getNode("Node 2.1").isSelected());
		assertTrue(node2.getNode("Node 2.2").isSelected());
	}

	public void testCanCheckANode() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		tree = bot.tree();
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		item.check();
		assertTrue(tree.getTreeItem("Node 2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("data=null item=TreeItem {Node 2} detail=32", listeners);
	}

	public void testCanUnCheckANode() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		tree = bot.tree();
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		item.uncheck();
		assertFalse(tree.getTreeItem("Node 2").isChecked());
		assertTextContains("Selection [13]: SelectionEvent{Tree {} ", listeners);
		assertTextContains("data=null item=TreeItem {Node 2} detail=32", listeners);
	}

	public void testCanToggleANode() throws Exception {
		bot.checkBox("SWT.CHECK").select();
		tree = bot.tree();
		SWTBotTreeItem item = tree.getTreeItem("Node 2");
		assertFalse(item.isChecked());
		item.toggleCheck();
		assertTrue(item.isChecked());
		item.toggleCheck();
		assertFalse(item.isChecked());
	}

	protected void setUp() throws Exception {
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
