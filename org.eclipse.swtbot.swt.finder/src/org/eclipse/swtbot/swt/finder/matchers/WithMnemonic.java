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

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Factory;

/**
 * Matches {@link org.eclipse.swt.widgets.Widget}s with the specified text. Skips mnemonics, so "Username" will match
 * items with text "&amp;Username" and "User&amp;name"
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class WithMnemonic<T extends Widget> extends AbstractMatcher<T> {
	private final String	text;
	private final boolean	ignoreCase;

	/**
	 * Constructs the Mnemonic text matcher with the given text.
	 * 
	 * @param text the mnemonic to match on the {@link org.eclipse.swt.widgets.Widget}
	 */
	WithMnemonic(String text) {
		this(text, false);
	}

	/**
	 * Constructs the Mnemonic text matcher with the given text.
	 * 
	 * @param text the mnemonic to match on the {@link org.eclipse.swt.widgets.Widget}
	 * @param ignoreCase Determines if this should ignore case during the comparison.
	 */
	WithMnemonic(String text, boolean ignoreCase) {
		this.text = text.replaceAll("&", ""); //$NON-NLS-1$ //$NON-NLS-2$
		this.ignoreCase = ignoreCase;
	}

	/**
	 * Extends the behaviour of WithText my removing the mnemonics "&amp;" that are used as keyboard accessors from the
	 * text.
	 * 
	 * @see org.eclipse.swtbot.swt.finder.matchers.WithText#getText(java.lang.Object)
	 * @param obj The object to get the text from.
	 * @return The newly formated string.
	 * @throws NoSuchMethodException if the method "getText" does not exist on the object.
	 * @throws IllegalAccessException if the java access control does not allow invocation.
	 * @throws InvocationTargetException if the method "getText" throws an exception.
	 * @see Method#invoke(Object, Object[])
	 */
	String getText(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return WithText.getText(obj).replaceAll("&", "").split("\t")[0]; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void describeTo(Description description) {
		description.appendText("with mnemonic '").appendText(text).appendText("'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected boolean doMatch(Object obj) {
		try {
			boolean result = false;
			if (ignoreCase)
				result = getText(obj).equalsIgnoreCase(text);
			else
				result = getText(obj).equals(text);
			return result;
		} catch (Exception e) {
			// do nothing
		}
		return false;
	}

	/**
	 * Matches a widget that has the specified text, after striping the mnemonics "&"
	 * 
	 * @param text the text.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> withMnemonic(String text) {
		return new WithMnemonic<Widget>(text);
	}

}
