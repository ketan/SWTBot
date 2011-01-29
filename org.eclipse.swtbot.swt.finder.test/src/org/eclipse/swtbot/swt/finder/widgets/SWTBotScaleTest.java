/*******************************************************************************
 * Copyright (c) 2011 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     John Cortell - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

public class SWTBotScaleTest extends AbstractSWTTestCase {

	@Test
	public void findsScale() throws Exception {
		assertNotNull(bot.scale().widget);
	}

	@Test
	public void findsScaleInGroup() throws Exception {
		assertNotNull(bot.scaleInGroup("Scale").widget);
	}

	@Test
	public void shouldGetValue() throws Exception {
		bot.spinnerInGroup("Selection").setSelection(42);
		assertEquals(42, bot.scaleInGroup("Scale").getValue());
	}

	@Test
	public void shouldSetValue() throws Exception {
		bot.scaleInGroup("Scale").setValue(24);
		assertEquals(24, bot.scaleInGroup("Scale").getValue());
	}

	@Test
	public void shouldGetMinimumAndMaximum() throws Exception {
		bot.spinnerInGroup("Minimum").setSelection(5);
		bot.spinnerInGroup("Maximum").setSelection(45);

		assertEquals(5, bot.scaleInGroup("Scale").getMinimum());
		assertEquals(45, bot.scaleInGroup("Scale").getMaximum());
	}

	@Test
	public void shouldGetIncrementAndPageIncrement() throws Exception {
		bot.spinnerInGroup("Increment").setSelection(7);
		bot.spinnerInGroup("Page Increment").setSelection(15);

		assertEquals(7, bot.scaleInGroup("Scale").getIncrement());
		assertEquals(15, bot.scaleInGroup("Scale").getPageIncrement());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Scale").activate();
		bot.spinnerInGroup("Selection").setSelection(0);
		bot.spinnerInGroup("Minimum").setSelection(0);
		bot.spinnerInGroup("Maximum").setSelection(100);

		bot.spinnerInGroup("Increment").setSelection(1);
		bot.spinnerInGroup("Page Increment").setSelection(10);
	}

}
