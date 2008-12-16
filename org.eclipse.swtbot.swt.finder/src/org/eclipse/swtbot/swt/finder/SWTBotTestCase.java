/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=108
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=112
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.io.File;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.ClassUtils;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;

/**
 * The SWTBotTestCase extends the JUnit TestCase to provide extra capabilities for comparing widgets and other UI items.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotTestCase.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 * @since 1.0
 */
public abstract class SWTBotTestCase extends TestCase {

	/**
	 * An instance of SWTBot that may be used by extending items.
	 * 
	 * @since 1.1
	 */
	protected SWTBot		bot					= new SWTBot();

	/** The logger. */
	private static Logger	log					= Logger.getLogger(SWTBotTestCase.class);

	/** Counts the screenshots to determine if maximum number is reached. */
	private static int		screenshotCounter	= 0;

	/**
	 * Asserts that two widgets do not refer to the same object.
	 * 
	 * @see #assertNotSame(String, Object, Object)
	 * @param expected the object you don't expect
	 * @param actual the object to compare to unexpected
	 */
	public static void assertNotSameWidget(Widget expected, Widget actual) {
		assertThat(expected, not(sameInstance(actual)));
	}

	/**
	 * Asserts the two widgets do not refer to the same object. The message will be used if the test fails.
	 * 
	 * @param message the identifying message or null for the AssertionError
	 * @param expected the object you don't expect
	 * @param actual the object to compare to unexpected
	 */
	public static void assertNotSameWidget(String message, Widget expected, Widget actual) {
		assertThat(message, expected, not(sameInstance(actual)));
	}

	/**
	 * Asserts that the <code>needle</code> is contained within the <code>hayStack</code>.
	 * 
	 * @param needle the text to search in the <code>hayStack</code>.
	 * @param hayStack the text to look within.
	 */
	public static void assertContains(String needle, String hayStack) {
		assertThat(hayStack, containsString(needle));
	}

	/**
	 * Asserts that the <code>needle</code> is not present in the <code>hayStack</code>.
	 * 
	 * @param needle the text to search in the <code>hayStack</code>.
	 * @param hayStack the text to look within.
	 */
	public static void assertDoesNotContain(String needle, String hayStack) {
		assertThat(hayStack, not(containsString(needle)));
	}

	/**
	 * Asserts that two widgets refer to the same widget.
	 * 
	 * @param expected the expected widget
	 * @param actual the widget to compare to expected
	 */
	public static void assertSameWidget(Widget expected, Widget actual) {
		assertThat(actual, sameInstance(expected));
	}

	/**
	 * Asserts that two widgets refer to the same widgets.
	 * 
	 * @param message the identifying message or <code>null</code> for the AssertionError
	 * @param expected the expected widget
	 * @param actual the widget to compare to expected
	 */
	public static void assertSameWidget(String message, Widget expected, Widget actual) {
		assertThat(message, actual, sameInstance(expected));
	}

	/**
	 * A helper to explicitly convey that the test has passed. Does nothing.
	 */
	public static void pass() {
		// Do nothing
	}

	/**
	 * Assert that the given string is the same as the widgets text.
	 * 
	 * @param expected the expected text
	 * @param widget the widget to get the text from to compare.
	 */
	public static void assertText(String expected, Widget widget) {
		assertEquals(expected, SWTUtils.getText(widget));
	}

	/**
	 * Assert that the given string is the same as the widgets text.
	 * 
	 * @param expected the expected text
	 * @param widget the widget to get the text from to compare.
	 */
	public static void assertText(String expected, AbstractSWTBot<? extends Widget> widget) {
		assertEquals(expected, widget.getText());
	}

	/**
	 * Assert that the text on the widget contains the expected text.
	 * 
	 * @param expected the expected text.
	 * @param widget the widget
	 */
	public static void assertTextContains(String expected, Widget widget) {
		assertThat(SWTUtils.getText(widget), containsString(expected));
	}

	/**
	 * Assert that the text on the widget contains the expected text.
	 * 
	 * @param expected the expected text
	 * @param widget the widget
	 */
	public static void assertTextContains(String expected, AbstractSWTBot<? extends Widget> widget) {
		assertThat(widget.getText(), containsString(expected));
	}

	/**
	 * Assert that the text on the widget does not contain the expected text.
	 * 
	 * @param expected the expected text
	 * @param widget the widget
	 */
	public static void assertTextDoesNotContain(String expected, Widget widget) {
		assertThat(SWTUtils.getText(widget), not(containsString(expected)));
	}

	/**
	 * Assert that the text on the widget does not contain the expected text.
	 * 
	 * @param expected the expected text
	 * @param widget the widget
	 */
	public static void assertTextDoesNotContain(String expected, AbstractSWTBot<? extends Widget> widget) {
		assertThat(widget.getText(), not(containsString(expected)));
	}

	/**
	 * Asserts that the widget is enabled.
	 * 
	 * @param widget the widget.
	 */
	public static void assertEnabled(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be enabled.", widget.isEnabled());
	}

	/**
	 * Asserts that the widget is not enabled.
	 * 
	 * @param widget the widget.
	 */
	public static void assertNotEnabled(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be disabled.", !widget.isEnabled());
	}

	/**
	 * Asserts that the widget is visible.
	 * 
	 * @param widget the widget.
	 */
	public static void assertVisible(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be visible.", widget.isVisible());
	}

	/**
	 * Asserts that the widget is not visible.
	 * 
	 * @param widget the widget.
	 */
	public static void assertNotVisible(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be visible.", !widget.isVisible());
	}

	/**
	 * Overides the runBare method to TestCase to add the ability to capture a screen shot when an error is thrown. The
	 * screenshot is saved to a file in the current run directory inside a folder called {@code screenshots} that gets
	 * created if it does not already exist. The screenshot will be saved with the file {@code
	 * screenshots/screenshot-<classname>.<testname>.png}
	 * 
	 * @see junit.framework.TestCase#runBare()
	 * @throws Throwable Thrown if an error occurs during running.
	 */
	@Override
	public void runBare() throws Throwable {
		Throwable exception = null;
		try {
			super.runBare();
		} catch (Throwable running) {
			exception = running;
			captureScreenshot();
		}
		if (exception != null)
			throw exception;
	}

	/**
	 * Helper used by {@link #runBare()}.
	 * 
	 * @see #runBare()
	 */
	private void captureScreenshot() {
		try {
			int maximumScreenshots = SWTBotPreferences.getMaximumScreenshots();
			String fileName = "screenshots/screenshot-" + ClassUtils.simpleClassName(getClass()) + "." + getName() + "."
					+ SWTBotPreferences.getScreenshotFormat().toLowerCase();
			if (++screenshotCounter <= maximumScreenshots) {
				new File("screenshots").mkdirs();
				captureScreenshot(fileName);
			} else {
				log.info("No screenshot captured for '" + ClassUtils.simpleClassName(getClass()) + "." + getName()
						+ "' because maximum number of screenshots reached: " + maximumScreenshots);
			}
		} catch (Exception e) {
			log.warn("Could not capture screenshot", e);
		}
	}

	/**
	 * Allows the screen shot to be captured and saved to the given file.
	 * 
	 * @see SWTUtils#captureScreenshot(String)
	 * @param fileName the filename to save screenshot to.
	 * @return <code>true</code> if the screenshot was created and saved, <code>false</code> otherwise.
	 */
	public static boolean captureScreenshot(String fileName) {
		return SWTUtils.captureScreenshot(fileName);
	}

}
