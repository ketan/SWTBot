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

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.List;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotListTest extends AbstractSWTTestCase {

	@Test
	public void findsAListWithoutLabel() throws Exception {
		SWTBotList list = bot.list();
		assertNotNull(list.widget);
		assertEquals(List.class, list.widget.getClass());
	}

	@Test
	public void setsAndGetsSingleSelectionByText() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select("Bananas");

		assertEquals(1, list.selectionCount());
		assertEquals("Bananas", list.selection()[0]);
	}

	@Test
	public void setsAndGetsSingleSelectionByIndex() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select(3);

		assertEquals(1, list.selectionCount());
		assertEquals("Grapefruit", list.selection()[0]);
	}

	@Test
	public void setsAndGetsMultipleSelectionByText() throws Exception {
		bot.radio("SWT.MULTI").click();
		SWTBotList list = bot.listInGroup("List");
		list.select(new String[] { "Grapefruit", "Peaches", "Apricots" });

		assertEquals(3, list.selectionCount());
		assertEquals("Grapefruit", list.selection()[0]);
		assertEquals("Peaches", list.selection()[1]);
		assertEquals("Apricots", list.selection()[2]);
	}

	@Test
	public void setsAndGetsMultipleSelectionByIndex() throws Exception {
		bot.radio("SWT.MULTI").click();
		SWTBotList list = bot.listInGroup("List");
		list.select(new int[] { 2, 4, 6 });

		assertEquals(3, list.selectionCount());
		assertEquals("Bananas", list.selection()[0]);
		assertEquals("Peaches", list.selection()[1]);
		assertEquals("Apricots", list.selection()[2]);
	}

	@Test
	public void unSelectsSelection() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select(3);
		assertEquals(1, list.selectionCount());
		list.unselect();
		assertEquals(0, list.selectionCount());
	}

	@Test
	public void getsIndexOfItem() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		assertEquals(2, list.indexOf("Bananas"));
	}

	@Test
	public void getsItemAtIndex() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		assertEquals("Bananas", list.itemAt(2));
	}

	@Test
	public void selectionNotifiesListeners() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		bot.checkBox("Listen").select();
		bot.button("Clear").click();

		list = bot.listInGroup("List");
		SWTBotText text = bot.textInGroup("Listeners");
		assertText("", text);

		list.select(3);
		assertTextContains("Selection [13]: SelectionEvent{List {} ", text.widget);
		assertTextContains("MouseUp [4]: MouseEvent{List {} ", text.widget);
		assertTextContains("MouseDown [3]: MouseEvent{List {} ", text.widget);
	}

	@Test
	public void deSelectNotifiesListeners() throws Exception {
		SWTBotList list = bot.listInGroup("List");
		list.select(3);
		bot.checkBox("Listen").select();
		bot.button("Clear").click();

		list = bot.listInGroup("List");
		SWTBotText text = bot.textInGroup("Listeners");
		assertText("", text);

		list.unselect();
		assertTextContains("Selection [13]: SelectionEvent{List {} ", text.widget);
		assertTextContains("MouseUp [4]: MouseEvent{List {} ", text.widget);
		assertTextContains("MouseDown [3]: MouseEvent{List {} ", text.widget);
	}

	@Test
	public void throwsExceptionInCaseOfInvalidIndexBasedSelection() throws Exception {
		SWTBot bot = new SWTBot();
		SWTBotList list = bot.listInGroup("List");
		try {
			list.select(100);
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("assertion failed: The index (100) is more than the number of items (9) in the list.", e.getMessage());
		}
	}

	@Test
	public void throwsExceptionInCaseOfInvalidTextBasedSelection() throws Exception {
		SWTBot bot = new SWTBot();
		SWTBotList list = bot.listInGroup("List");
		try {
			list.select("non existent item");
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("assertion failed: Item `non existent item' not found in list.", e.getMessage());
		}
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("List").activate();
	}

}
