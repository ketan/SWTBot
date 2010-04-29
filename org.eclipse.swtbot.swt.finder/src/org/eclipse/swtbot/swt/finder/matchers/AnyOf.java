/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import java.util.Arrays;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that evaluates to <code>true</code> if any the matchers evaluate to <code>true</code>.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AnyOf<T> extends AbstractMatcher<T> {
	private final Iterable<Matcher<? extends T>>	matchers;

	AnyOf(Iterable<Matcher<? extends T>> matchers) {
		this.matchers = matchers;
	}

	protected boolean doMatch(Object o) {
		for (Matcher<? extends T> matcher : matchers) {
			if (matcher.matches(o)) {
				return true;
			}
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendList("(", " or ", ")", matchers); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Evaluates to true only if ANY of the passed in matchers evaluate to true.
	 *
	 * @return a matcher.
	 */
	@Factory
	public static <T extends Widget> Matcher<T> anyOf(Matcher<? extends T>... matchers) {
		return new AnyOf<T>(Arrays.asList(matchers));
	}

	/**
	 * Evaluates to true only if ANY of the passed in matchers evaluate to true.
	 *
	 * @return a matcher.
	 */
	@Factory
	public static <T extends Widget> Matcher<T> anyOf(Iterable<Matcher<? extends T>> matchers) {
		return new AnyOf<T>(matchers);
	}

}
