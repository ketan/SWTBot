/*******************************************************************************
<<<<<<< .working
 * Copyright 2007 new SWTBot, http://swtbot.org/
=======
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
>>>>>>> .merge-right.r1100
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;


import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotLabelTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsLabel() throws Exception {
		bot.label("One");
	}

	public void testFindsAlignment() throws Exception {
		assertEquals(SWT.LEFT, bot.label("One").alignment());
		bot.radio("Center").click();
		assertEquals(SWT.CENTER, bot.label("One").alignment());
		bot.radio("Right").click();
		assertEquals(SWT.RIGHT, bot.label("One").alignment());
	}

	public void testGetsImage() throws Exception {
		assertNotNull(bot.label("").image());
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("Label").activate();
		bot.checkBox("Horizontal Fill").select();
	}

}
