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
import org.eclipse.ui.IPerspectiveDescriptor;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author Ralf Ebert
 * @version $Id$
 * @since 2.0
 */
public class WithPerspectiveLabel extends AbstractMatcher<IPerspectiveDescriptor> {

	private final Matcher<String> labelMatcher;

	/**
	 * @param labelMatcher the perspective label matcher.
	 */
	public WithPerspectiveLabel(Matcher<String> labelMatcher) {
		this.labelMatcher = labelMatcher;
	}

	public boolean doMatch(Object item) {
		if (item instanceof IPerspectiveDescriptor) {
			IPerspectiveDescriptor perspective = (IPerspectiveDescriptor) item;
			return labelMatcher.matches(perspective.getLabel());
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("with label '").appendDescriptionOf(labelMatcher).appendText("'");
	}

	/**
	 * Matches a perspective with the specified label.
	 *
	 * @param label the label of the perspective.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static WithPerspectiveLabel withPerspectiveLabel(String label) {
		return withPerspectiveLabel(equalTo(label));
	}

	/**
	 * Matches a perspective with the specified label.
	 *
	 * @param label the label of the perspective.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static WithPerspectiveLabel withPerspectiveLabel(Matcher<String> labelMatcher) {
		return new WithPerspectiveLabel(labelMatcher);
	}
}
