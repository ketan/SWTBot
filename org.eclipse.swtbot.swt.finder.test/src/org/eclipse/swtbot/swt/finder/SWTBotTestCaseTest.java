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
package org.eclipse.swtbot.swt.finder;

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertContains;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertDoesNotContain;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertEnabled;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertMatchesRegex;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertNotEnabled;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import junit.framework.TestCase;

import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBotTest;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.junit.Test;

public class SWTBotTestCaseTest extends AbstractSWTBotTest {

	@Test
	public void assertContainsWorks() throws Exception {
		assertContains("needle", "some haystack needle. foo bar");
	}

	@Test
	public void assertContainsThrowsExceptionWhenHaystackDoesNotContainNeedle() throws Exception {
		try {
			assertContains("this does not exist", "some haystack needle. foo bar");
			fail("Was expecting an exception");
		} catch (AssertionError e) {
			assertEquals("\n" + "Expected: a string containing \"this does not exist\"\n" + "     got: \"some haystack needle. foo bar\"\n"
					+ "", e.getMessage());
		}
	}

	@Test
	public void assertDoesNotContainWorks() throws Exception {
		assertDoesNotContain("this does not exist", "some haystack needle. foo bar");
	}

	@Test
	public void assertDoesNotContainThrowsExceptionWhenHaystackDoesNotContainNeedle() throws Exception {
		try {
			assertDoesNotContain("needle", "some haystack needle. foo bar");
			fail("Was expecting an exception");
		} catch (AssertionError e) {
			assertEquals("\n" + "Expected: not a string containing \"needle\"\n" + "     got: \"some haystack needle. foo bar\"\n" + "", e
					.getMessage());
		}
	}

	@Test
	public void assertsIfWidgetIsEnabled() throws Exception {
		assertEnabled(bot.button("One"));
	}

	@Test
	public void assertEnabledThrowsExceptionWhenWidgetIsDisable() throws Exception {
		final SWTBotButton button = bot.button("One");
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				button.widget.setEnabled(false);
			}
		});
		try {
			assertEnabled(button);
			fail("Expecting an exception");
		} catch (AssertionError e) {
			assertEquals("Expected widget (of type 'Button' and with mnemonic 'One' and with style 'SWT.PUSH') to be enabled.", e
					.getMessage());
		} finally {
			UIThreadRunnable.syncExec(new VoidResult() {
				public void run() {
					button.widget.setEnabled(true);
				}
			});
		}
	}

	@Test
	public void assertNotEnabledThrowsExceptionWhenWidgetIsEnabled() throws Exception {
		try {
			assertNotEnabled(bot.button("One"));
			fail("Expecting an exception");
		} catch (AssertionError e) {
			assertEquals("Expected widget (of type 'Button' and with mnemonic 'One' and with style 'SWT.PUSH') to be disabled.", e
					.getMessage());
		}
	}

	@Test
	public void capturesScreenshotOnError() throws Exception {
		TestCase testCase = new DummyTestDoNotExecuteInAnt();
		String fileName = "screenshots/screenshot-DummyTestDoNotExecuteInAnt.null.jpeg";
		new File(fileName).delete();
		assertFalse(new File("screenshots/screenshot-DummyTestDoNotExecuteInAnt.null.jpeg").exists());
		try {
			testCase.runBare();
		} catch (Throwable e) {
			assertTrue(new File("screenshots/screenshot-DummyTestDoNotExecuteInAnt.null.jpeg").exists());
		}
	}

	@Test
	public void assertRegexMatchesThrowsExceptionWhenRegexDoesNotMatch() throws Exception {
		try {
			assertMatchesRegex("foo", bot.button("One"));
			fail("Expecting an exception");
		} catch (AssertionError e) {
			assertEquals("\nExpected: matching regex (<([\r\n]|.)*foo([\r\n]|.)*>)\n     got: \"One\"\n", e.getMessage());
		}
	}

	@Test
	public void assertRegexMatchesDoesNotThrowExceptionWhenRegexMatches() throws Exception {
		assertMatchesRegex("One", bot.button("One"));
	}

	@Test
	public void assertRegexMatchesDoesNotThrowExceptionWhenRegexMatches2() throws Exception {
		assertMatchesRegex("n", bot.button("One"));
	}

	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Button").activate();
	}
}
