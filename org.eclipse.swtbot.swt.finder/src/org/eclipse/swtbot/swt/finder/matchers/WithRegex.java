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

import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches widgets if the getText() method of the widget matches the specified regex.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class WithRegex<T extends Widget> extends AbstractMatcher<T> {

	/** The regular expression string. */
	private Pattern	pattern;

	/**
	 * COnstructs the regular expression matcher with the given regular expression stirng.
	 * 
	 * @param regex the regex to match on the {@link org.eclipse.swt.widgets.Widget}
	 */
	WithRegex(String regex) {
		pattern = Pattern.compile("([\r\n]|.)*" + regex + "([\r\n]|.)*");
	}

	protected boolean doMatch(Object obj) {
		try {
			return pattern.matcher(WithText.getText(obj)).matches();
		} catch (Exception e) {
			// do nothing
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("with regex '").appendValue(pattern).appendText("'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Matches a widget that has the specified regex.
	 * 
	 * @param regex the label.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends Widget> Matcher<T> withRegex(String regex) {
		return new WithRegex<T>(regex);
	}
}
