/*******************************************************************************
 * Copyright (c) 2011 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
public class SWTBotExpandBarTest extends AbstractSWTTestCase {

	@Test
	public void shouldGetExpandItemCount() throws Exception {
		assertEquals(2, bot.expandBar().itemCount());
	}

	@Test
	public void shouldGetExpandedAndCollapsedItemCount() throws Exception {
		SWTBotExpandBar expandBar = bot.expandBar();
		assertEquals(1, expandBar.expandedItemCount());
		assertEquals(1, expandBar.collapsedItemCount());
	}

	@Test
	public void shouldExpandAnItem() throws Exception {
		SWTBotExpandBar expandBar = bot.expandBar();
		SWTBotExpandItem item1 = expandBar.expandItem("What is your favorite button?");
		SWTBotExpandItem item2 = expandBar.expandItem("What is your favorite icon?");
		assertEquals(2, expandBar.expandedItemCount());
		assertEquals(0, expandBar.collapsedItemCount());

		assertTrue(item1.isExpanded());
		assertTrue(item2.isExpanded());

		assertFalse(item1.isCollapsed());
		assertFalse(item2.isCollapsed());
	}

	@Test
	public void shouldCollapseAnItem() throws Exception {
		SWTBotExpandBar expandBar = bot.expandBar();
		SWTBotExpandItem item1 = expandBar.collapseItem("What is your favorite button?");
		SWTBotExpandItem item2 = expandBar.collapseItem("What is your favorite icon?");
		assertEquals(0, expandBar.expandedItemCount());
		assertEquals(2, expandBar.collapsedItemCount());

		assertFalse(item1.isExpanded());
		assertFalse(item2.isExpanded());

		assertTrue(item1.isCollapsed());
		assertTrue(item2.isCollapsed());
	}

	@Test
	public void shouldGetAllExpandBarItems() throws Exception {
		List<SWTBotExpandItem> items = bot.expandBar().getAllItems();
		assertEquals(2, items.size());
		assertEquals("What is your favorite button?", items.get(0).getText());
		assertEquals("What is your favorite icon?", items.get(1).getText());
	}

	@Test
	public void shouldThrowExceptionWhenItemIsNotFound() throws Exception {
		try {
			bot.expandBar().getExpandItem(withText("some text"));
			fail("Expected WNFE");
		} catch (WidgetNotFoundException e) {
			assertEquals("Could not find widget.", e.getMessage());
			assertEquals("Timeout after: 5000 ms.: Could not find widget matching: (of type 'ExpandItem' and with text 'some text')", e
					.getCause().getMessage());
		}
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("ExpandBar").activate();
	}

}
