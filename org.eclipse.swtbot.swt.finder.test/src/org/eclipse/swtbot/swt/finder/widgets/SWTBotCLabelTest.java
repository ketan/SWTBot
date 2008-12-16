/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotCLabelTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotCLabelTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsCLabel() throws Exception {
		bot.clabel("One");
	}

	public void testFindsCLabel1() throws Exception {
		bot.clabel("The quick brown fox jumps over the lazy dog.\nOne Two Three");
	}

	public void testFindsAlignment() throws Exception {
		assertEquals(SWT.LEFT, bot.clabel("One").alignment());
		bot.radio("Center").click();
		assertEquals(SWT.CENTER, bot.clabel("One").alignment());
		bot.radio("Right").click();
		assertEquals(SWT.RIGHT, bot.clabel("One").alignment());
	}

	public void testGetsImage() throws Exception {
		assertNotNull(bot.clabel("One").image());
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.tabItem("CLabel").activate();
	}

	protected Shell getFocusShell() {
		return customControlShell;
	}

}
