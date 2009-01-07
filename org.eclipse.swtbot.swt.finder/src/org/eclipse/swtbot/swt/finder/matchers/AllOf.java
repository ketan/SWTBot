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

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that evaluates to <code>true</code> if and only if all the matchers evaluate to <code>true</code>.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AllOf<T> extends AbstractMatcher<T> {
	private final Matcher<? extends T>[]	matchers;

	public AllOf(Matcher<? extends T>... matchers) {
		this.matchers = matchers;
	}

	protected boolean doMatch(Object o) {
		for (Matcher<? extends T> matcher : matchers) {
			if (!matcher.matches(o)) {
				return false;
			}
		}
		return true;
	}

	public void describeTo(Description description) {
		description.appendList("(", " and ", ")", Arrays.asList(matchers)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Evaluates to true only if ALL of the passed in matchers evaluate to true.
	 *
	 * @return a matcher.
	 */
	@Factory
	public static <T> Matcher<T> allOf(Matcher<? extends T>... matchers) {
		return new AllOf(matchers);
	}

}
