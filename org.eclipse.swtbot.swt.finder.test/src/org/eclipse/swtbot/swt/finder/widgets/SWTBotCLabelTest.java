/*******************************************************************************
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotCLabelTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void findsCLabel() throws Exception {
		bot.clabel("One");
	}

	@Test
	public void findsCLabel1() throws Exception {
		bot.clabel("The quick brown fox jumps over the lazy dog.\nOne Two Three");
	}

	@Test
	public void findsAlignment() throws Exception {
		assertEquals(SWT.LEFT, bot.clabel("One").alignment());
		bot.radio("Center").click();
		assertEquals(SWT.CENTER, bot.clabel("One").alignment());
		bot.radio("Right").click();
		assertEquals(SWT.RIGHT, bot.clabel("One").alignment());
	}

	@Test
	public void getsImage() throws Exception {
		assertNotNull(bot.clabel("One").image());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("CLabel").activate();
	}

	protected Shell getFocusShell() {
		return customControlShell;
	}

}
