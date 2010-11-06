/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Libor Zoubek, Red Hat - initial API and implementation
 *     Ketan Padegaonkar - cleanup to conform to SWTBot standards
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertContains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.alltests.Controls;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Libor Zoubek &lt;lzoubek [at] redhat [dot] com&gt;
 */
@Ignore
public class SWTBotBrowserTest extends AbstractSWTTestCase {
	private SWTBot	bot;

	@Override
	protected Shell getFocusShell() {
		return Controls.getInstance().browserShell;
	}

	@Test
	public void findBrowser() throws Exception {
		SWTBotBrowser browser = bot.browser();
		assertNotNull(browser);
	}

	@Test
	public void navigation() throws Exception {
		SWTBotBrowser browser = bot.browser();
		assertNotNull(browser);
		assertFalse(browser.isBackEnabled());
		assertFalse(browser.isForwardEnabled());
		browser.setUrl("http://www.eclipse.org");
		assertContains("Eclipse", browser.getText());
		browser.setUrl("http://www.eclipse.org/swtbot/");
		assertContains("swtbot", browser.getUrl());
		assertTrue(browser.isBackEnabled());
		assertFalse(browser.isForwardEnabled());
		browser.back();
		assertContains("eclipse", browser.getUrl());
		assertTrue(browser.isForwardEnabled());
		assertFalse(browser.isBackEnabled());
	}

	@Test(expected = TimeoutException.class)
	public void smallTimeout() throws Exception {
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 10;
			SWTBotBrowser browser = bot.browser();
			assertNotNull(browser);
			browser.setUrl("http://www.eclipse.org");
			assertContains("Eclipse", browser.getText());
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
	}

	@Test
	public void executeJavaScript() throws Exception {
		SWTBotBrowser browser = bot.browser();
		assertNotNull(browser);
		browser.setUrl("http://www.eclipse.org");
		assertContains("Eclipse", browser.getText());
		browser.execute("document.getElementsByTagName('body')[0].innerHTML='<p>JavaScript works</p>';");
		assertContains("JavaScript works", browser.getText());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
	}
}
