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
public class WithPerspectiveId extends AbstractMatcher<IPerspectiveDescriptor> {

	private final Matcher<String> idMatcher;

	/**
	 * @param idMatcher the perspective id matcher.
	 */
	public WithPerspectiveId(Matcher<String> idMatcher) {
		this.idMatcher = idMatcher;
	}

	public boolean doMatch(Object item) {
		if (item instanceof IPerspectiveDescriptor) {
			IPerspectiveDescriptor perspective = (IPerspectiveDescriptor) item;
			return idMatcher.matches(perspective.getId());
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("with id '").appendDescriptionOf(idMatcher).appendText("'");
	}

	/**
	 * Matches a perspective with the specified id.
	 *
	 * @param id the id of the perspective.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static WithPerspectiveId withPerspectiveId(String id) {
		return withPerspectiveId(equalTo(id));
	}

	/**
	 * Matches a perspective with the specified id.
	 *
	 * @param id the id of the perspective.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static WithPerspectiveId withPerspectiveId(Matcher<String> idMatcher) {
		return new WithPerspectiveId(idMatcher);
	}
}
