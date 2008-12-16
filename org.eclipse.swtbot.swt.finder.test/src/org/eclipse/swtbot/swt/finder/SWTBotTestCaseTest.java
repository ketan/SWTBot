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

import java.io.File;

import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBotTest;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class SWTBotTestCaseTest extends AbstractSWTBotTest {

	public void testAssertContainsWorks() throws Exception {
		assertContains("needle", "some haystack needle. foo bar");
	}

	public void testAssertContainsThrowsExceptionWhenHaystackDoesNotContainNeedle() throws Exception {
		try {
			assertContains("this does not exist", "some haystack needle. foo bar");
			fail("Was expecting an exception");
		} catch (AssertionError e) {
			assertEquals("\n" + 
					"Expected: a string containing \"this does not exist\"\n" + 
					"     got: \"some haystack needle. foo bar\"\n" + 
					"", e.getMessage());
		}
	}

	public void testAssertDoesNotContainWorks() throws Exception {
		assertDoesNotContain("this does not exist", "some haystack needle. foo bar");
	}

	public void testAssertDoesNotContainThrowsExceptionWhenHaystackDoesNotContainNeedle() throws Exception {
		try {
			assertDoesNotContain("needle", "some haystack needle. foo bar");
			fail("Was expecting an exception");
		} catch (AssertionError e) {
			assertEquals("\n" + 
					"Expected: not a string containing \"needle\"\n" + 
					"     got: \"some haystack needle. foo bar\"\n" + 
					"", e.getMessage());
		}
	}

	public void testAssertsIfWidgetIsEnabled() throws Exception {
		assertEnabled(bot.button("One"));
	}

	public void testAssertEnabledThrowsExceptionWhenWidgetIsDisable() throws Exception {
		final SWTBotButton button = bot.button("One");
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				button.widget.setEnabled(false);
			}
		});
		try {
			assertEnabled(button);
			fail("Expecting an exception");
		} catch (AssertionFailedError e) {
			assertEquals("Expected widget SWTBotButton Button {One} to be enabled.", e.getMessage());
		} finally {
			UIThreadRunnable.syncExec(new VoidResult() {
				public void run() {
					button.widget.setEnabled(true);
				}
			});
		}
	}

	public void testAssertNotEnabledThrowsExceptionWhenWidgetIsEnabled() throws Exception {
		try {
			assertNotEnabled(bot.button("One"));
			fail("Expecting an exception");
		} catch (AssertionFailedError e) {
			assertEquals("Expected widget SWTBotButton Button {One} to be disabled.", e.getMessage());
		}
	}

	public void testCapturesScreenshotOnError() throws Exception {
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

	protected void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Button").activate();
	}
}
