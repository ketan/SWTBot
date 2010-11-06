/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=17
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 */
public class SWTBotCComboTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void findCCombo() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		assertNotNull(ccombo.widget);
	}

	@Test
	public void getItems() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		assertEquals(9, ccombo.itemCount());
		assertEquals(9, ccombo.items().length);
	}

	@Test
	public void changeAndVerifiesSelectionInComboByText() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		ccombo.setSelection("Grapefruit");
		assertEquals("Grapefruit", ccombo.selection());
		bot.ccomboBoxInGroup("Custom Combo");
	}

	@Test
	public void changeAndVerifiesSelectionInComboByIndex() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		ccombo.setSelection(4);
		assertEquals(4, ccombo.selectionIndex());
		assertEquals("Peaches", ccombo.selection());
	}

	@Test
	public void throwsExceptionInCaseOfInvalidIndexBasedSelection() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		try {
			ccombo.setSelection(100);
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("The index (100) is more than the number of items (9) in the combo.", e.getMessage());
		}
	}

	@Test
	public void throwsExceptionInCaseOfInvalidTextBasedSelection() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		try {
			ccombo.setSelection("non existent item");
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("Item `non existent item' not found in combo box.", e.getMessage());
		}
	}

	@Test
	public void setText() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		ccombo.setText("Apricots");
		assertEquals(6, ccombo.selectionIndex());
		assertEquals("Apricots", ccombo.selection());
		ccombo.setText("Raspberries");
		assertEquals(-1, ccombo.selectionIndex());
		assertEquals(null, ccombo.selection());
	}

	@Test
	public void throwsExceptionInCaseOfChangeTextOfReadOnlyCombo() throws Exception {
		bot.checkBox("SWT.READ_ONLY").select();
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		try {
			ccombo.setText("Forbidden Text");
			fail("Was expecting an exception");
		} catch (RuntimeException e) {
			assertEquals("This combo box is read-only.", e.getMessage());
		}
	}

	public void _testGetsLimits() throws Exception {
		SWTBotCCombo ccombo = bot.ccomboBoxInGroup("Custom Combo");
		assertEquals(Combo.LIMIT, ccombo.textLimit());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("CCombo").activate();
		bot.checkBox("SWT.FLAT").deselect();
		bot.checkBox("SWT.READ_ONLY").deselect();
	}

	protected Shell getFocusShell() {
		return customControlShell;
	}

}
