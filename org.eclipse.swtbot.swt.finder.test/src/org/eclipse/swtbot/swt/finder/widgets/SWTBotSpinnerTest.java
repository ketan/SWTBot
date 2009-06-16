/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Yann N. Dauphin - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Yann N. Dauphin
 * @version $Id$
 */
public class SWTBotSpinnerTest extends AbstractSWTTestCase {

	SWTBot	bot	= new SWTBot();

	@Test
	public void findsSpinner() throws Exception {
		assertNotNull(bot.spinner().widget);
	}
	
	@Test
	public void findsSpinnerInGroup() throws Exception {
		assertNotNull(bot.spinnerInGroup("Spinner").widget);
	}
	
	@Test
	public void selectionWorks() throws Exception {
		bot.spinner().setSelection(11);
		assertEquals(11, bot.spinner().getSelection());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Spinner").activate();
	}

}
