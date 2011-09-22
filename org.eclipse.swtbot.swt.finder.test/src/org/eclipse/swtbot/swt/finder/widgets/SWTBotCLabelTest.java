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
import org.eclipse.swtbot.swt.finder.test.AbstractCustomControlExampleTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotCLabelTest extends AbstractCustomControlExampleTest {

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

	@Before
	public void setUp() throws Exception {
		bot.tabItem("CLabel").activate();
	}

}
