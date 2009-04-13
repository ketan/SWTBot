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
 *     Ketan Patel (bug 260088)
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.matchers;

import static org.hamcrest.Matchers.equalTo;

import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.ui.IWorkbenchPartReference;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ketan Patel
 * @version $Id$
 * @since 2.0
 */
public class WithPartName<T extends IWorkbenchPartReference> extends AbstractMatcher<T> {

	private final Matcher<String> nameMatcher;

	/**
	 * @param nameMatcher the part name matcher.
	 */
	public WithPartName(Matcher<String> nameMatcher) {
		this.nameMatcher = nameMatcher;
	}

	public boolean doMatch(Object item) {
		if (item instanceof IWorkbenchPartReference) {
			IWorkbenchPartReference part = (IWorkbenchPartReference) item;
			return nameMatcher.matches(part.getPartName());
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("with name '").appendDescriptionOf(nameMatcher).appendText("'");
	}

	/**
	 * Matches a workbench part (view/editor) with the specfied name.
	 *
	 * @param text the label of the part.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends IWorkbenchPartReference> Matcher<T> withPartName(String text) {
		return withPartName(equalTo(text));
	}

	/**
	 * Matches a workbench part (view/editor) with the specified name.
	 *
	 * @param nameMatcher the part name matcher.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends IWorkbenchPartReference> Matcher<T> withPartName(Matcher<String> nameMatcher) {
		return new WithPartName<T>(nameMatcher);
	}
}
