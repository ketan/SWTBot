/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.matchers;

import static org.hamcrest.Matchers.equalTo;

import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @version $Id$
 * @since 2.0
 */
public class WithPerspectiveId extends AbstractMatcher<IPerspectiveDescriptor> {

	private final Matcher<String>	idMatcher;

	/**
	 * @param idMatcher the perspective id matcher.
	 */
	WithPerspectiveId(Matcher<String> idMatcher) {
		this.idMatcher = idMatcher;
	}

	@Override
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
	 * @param idMatcher the matcher that matches the id of the perspective.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static WithPerspectiveId withPerspectiveId(Matcher<String> idMatcher) {
		return new WithPerspectiveId(idMatcher);
	}
}
