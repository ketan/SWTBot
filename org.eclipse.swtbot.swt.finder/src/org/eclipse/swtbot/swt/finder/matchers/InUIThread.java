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


import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * Matches another matcher in the context of the UI thread. Useful if you want to make a matcher UI thread safe.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class InUIThread<T extends Widget> extends AbstractMatcher<T> {

	private Matcher<?>	matcher;

	/**
	 * @param matcher another matcher
	 */
	public InUIThread(Matcher<?> matcher) {
		this.matcher = matcher;
	}

	protected boolean doMatch(final Object obj) {
		return UIThreadRunnable.syncExec(new BoolResult() {
			public Boolean run() {
				return matcher.matches(obj);
			}
		});
	}

	public void describeTo(Description description) {
		description.appendText("evaluates matcher [").appendDescriptionOf(matcher).appendText("] in the ui thread"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Matches another matcher in the context of the UI thread. Useful if you want to make a matcher UI thread safe.
	 *
	 * @param matcher the matcher
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static Matcher<? extends Widget> inUIThread(Matcher<?> matcher) {
		return new InUIThread<Widget>(matcher);
	}

}
