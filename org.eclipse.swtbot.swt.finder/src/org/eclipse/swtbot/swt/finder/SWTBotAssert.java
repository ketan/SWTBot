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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;

/**
 * The SWTBotAssert provides extra capabilities for comparing widgets and other UI items. A set of assert methods.
 * Messages are only displayed when an assert fails. See {@link junit.framework.Assert}
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public abstract class SWTBotAssert {

	/**
	 * Asserts that two widgets do not refer to the same object.
	 * 
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
		assertTrue("Expected widget " + widget + " to be enabled.", widget.isEnabled()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Asserts that the widget is not enabled.
	 * 
	 * @param widget the widget.
	 */
	public static void assertNotEnabled(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be disabled.", !widget.isEnabled()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Asserts that the widget is visible.
	 * 
	 * @param widget the widget.
	 */
	public static void assertVisible(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be visible.", widget.isVisible()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Asserts that the widget is not visible.
	 * 
	 * @param widget the widget.
	 */
	public static void assertNotVisible(AbstractSWTBot<? extends Widget> widget) {
		assertTrue("Expected widget " + widget + " to be visible.", !widget.isVisible()); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
