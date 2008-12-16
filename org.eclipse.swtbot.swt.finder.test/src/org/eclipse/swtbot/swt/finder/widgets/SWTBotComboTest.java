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
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=17
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;


/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotComboTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsCombo() throws Exception {
		SWTBotCombo combo = bot.comboBoxInGroup("Background Mode on Parent");
		assertNotNull(combo.widget);
	}

	public void testGetItems() throws Exception {
		bot.tabItem("Combo").activate();
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		assertEquals(9, comboBox.itemCount());
		List items = Arrays.asList(comboBox.items());
		assertEquals(9, items.size());
		for(int i = 1; i < 9; i++)
			assertTrue("Expected to contain: " + "Line "+i, items.contains("Line " + i));
		assertTrue("Expected to contain: " + "Longest Line In List", items.contains("Longest Line In List"));
	}

	public void testChangeAndVerifiesSelectionInComboByText() throws Exception {
		bot.tabItem("Combo").activate();
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		comboBox.setSelection("Line 7");
		assertEquals("Line 7", comboBox.selection());
	}

	public void testChangeAndVerifiesSelectionInComboByIndex() throws Exception {
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		comboBox.setSelection(2);
		assertEquals(2, comboBox.selectionIndex());
		assertEquals("Line 3", comboBox.selection());
	}

	public void testThrowsExceptionInCaseOfInvalidIndexBasedSelection() throws Exception {
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		try {
			comboBox.setSelection(100);
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("The index (100) is more than the number of items (9) in the combo.", e.getMessage());
		}
	}

	public void testThrowsExceptionInCaseOfInvalidTextBasedSelection() throws Exception {
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		try {
			comboBox.setSelection("non existent item");
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("Item `non existent item' not found in combo box.", e.getMessage());
		}
	}

	public void testChangeAndVerifiesTextInCombo() throws Exception {
		bot.checkBox("SWT.READ_ONLY").deselect();
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		comboBox.setText("New Text");
		assertEquals("New Text", comboBox.getText());
	}

	public void testThrowsExceptionInCaseOfChangeTextOfReadOnlyCombo() throws Exception {
		bot.checkBox("SWT.READ_ONLY").select();
		SWTBotCombo comboBox = bot.comboBoxInGroup("Combo");
		try {
			comboBox.setText("Forbidden Text");
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("This combo box is read-only.", e.getMessage());
		}finally{
			bot.checkBox("SWT.READ_ONLY").deselect();
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("Combo").activate();
	}

}
