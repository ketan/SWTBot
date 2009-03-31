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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotLabelTest extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void findsLabel() throws Exception {
		bot.label("One");
	}

	@Test
	public void findsAlignment() throws Exception {
		assertEquals(SWT.LEFT, bot.label("One").alignment());
		bot.radio("Center").click();
		assertEquals(SWT.CENTER, bot.label("One").alignment());
		bot.radio("Right").click();
		assertEquals(SWT.RIGHT, bot.label("One").alignment());
	}

	@Test
	public void getsImage() throws Exception {
		assertNotNull(bot.label("").image());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("Label").activate();
		bot.checkBox("Horizontal Fill").select();
	}

}
