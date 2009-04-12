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
public class WithPerspectiveLabel extends AbstractMatcher<IPerspectiveDescriptor> {

	private final Matcher<String>	labelMatcher;

	/**
	 * @param labelMatcher the perspective label matcher.
	 */
	WithPerspectiveLabel(Matcher<String> labelMatcher) {
		this.labelMatcher = labelMatcher;
	}

	@Override
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
	 * @param labelMatcher the matcher that matches the perspective label.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static WithPerspectiveLabel withPerspectiveLabel(Matcher<String> labelMatcher) {
		return new WithPerspectiveLabel(labelMatcher);
	}
}
