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
package org.rcpmail;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTBotEclipseTestCase;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ChildrenControlFinder;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Matcher;
import org.junit.Test;

public class MessageCreateTest extends SWTBotEclipseTestCase {

	@Test public void CreatesAnotherMessageWindow() throws Exception {
		assertEquals(2, viewCount());
		bot.menu("File").menu("Open Another Message View").click();

		assertEquals(3, viewCount());
	}

	@Test public void ClosesAllMessageWindows() throws Exception {
		bot.view("Message").close();
		bot.view("Message").close();

		assertEquals(1, viewCount());
	}

	@Test public void MyMailBoxContainsDrafts() throws Exception {
		SWTBotTree mailbox = mailBox();
		SWTBotTreeItem myMailBox = mailbox.expandNode("me@this.com");
		assertTrue(myMailBox.getNodes().contains("Drafts"));
	}

	// oops this fails
	@Test public void OtherMailBoxContainsDrafts() throws Exception {
		SWTBotTree mailbox = mailBox();
		SWTBotTreeItem otherMailBox = mailbox.expandNode("other@aol.com");
		assertTrue(otherMailBox.getNodes().contains("Drafts"));
	}

	private SWTBotTree mailBox() throws WidgetNotFoundException {
		Widget widget = bot.view("Mailboxes").getWidget();
		// find the tree
		ChildrenControlFinder finder = new ChildrenControlFinder(widget);
		List findControls = finder.findControls(treeMatcher());
		return new SWTBotTree((Tree) findControls.get(0));
	}

	private Matcher treeMatcher() {
		return widgetOfType(Tree.class);
	}

	private int viewCount() throws WidgetNotFoundException {
		return bot.views().size();
	}

}
