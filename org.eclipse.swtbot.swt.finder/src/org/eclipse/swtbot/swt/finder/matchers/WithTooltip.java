/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;


/**
 * Matches widgets if the getToolTipText() method of the widget matches the specified text.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class WithTooltip<T extends Widget> extends AbstractMatcher<T> {

	/** The text */
	protected String	text;

	/**
	 * A flag to denote if this should ignore case.
	 *
	 * @since 1.2
	 */
	protected boolean	ignoreCase	= false;

	/**
	 * Constructs this matcher with the given text.
	 *
	 * @param text the text to match on the {@link org.eclipse.swt.widgets.Widget}
	 */
	public WithTooltip(String text) {
		this(text, false);
	}

	/**
	 * Constructs this matcher with the given text.
	 *
	 * @param text the text to match on the {@link org.eclipse.swt.widgets.Widget}
	 * @param ignoreCase Determines if this should ignore case during the comparison.
	 * @since 1.2
	 */
	public WithTooltip(String text, boolean ignoreCase) {
		text = text.replaceAll("\\r\\n", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		text = text.replaceAll("\\r", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		this.text = text;
		this.ignoreCase = ignoreCase;
	}

	// FIXME: optimize the if() code block, use strategy or something else.
	protected boolean doMatch(Object obj) {
		try {
			boolean result = false;
			if (ignoreCase)
				result = getToolTip(obj).equalsIgnoreCase(text);
			else
				result = getToolTip(obj).equals(text);
			return result;
		} catch (Exception e) {
			// do nothing
		}
		return false;
	}

	/**
	 * Gets the text of the object using the getText method. If the object doesn't contain a get text method an
	 * exception is thrown.
	 *
	 * @param obj any object to get the text from.
	 * @return the return value of obj#getText()
	 * @throws NoSuchMethodException if the method "getToolTipText" does not exist on the object.
	 * @throws IllegalAccessException if the java access control does not allow invocation.
	 * @throws InvocationTargetException if the method "getText" throws an exception.
	 * @see Method#invoke(Object, Object[])
	 */
	private static String getToolTip(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return ((String) SWTUtils.invokeMethod(obj, "getToolTipText")).replaceAll(Text.DELIMITER, "\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void describeTo(Description description) {
		description.appendText("with tooltip '").appendText(text).appendText("'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Matches a widget that has the specified exact tooltip.
	 *
	 * @param text the label.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> withTooltip(String text) {
		return new WithTooltip<Widget>(text);
	}

	/**
	 * Matches a widget that has the specified tooltip, ignoring case considerations.
	 *
	 * @param text the label.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> withTooltipIgoringCase(String text) {
		return new WithTooltip<Widget>(text, true);
	}

}
