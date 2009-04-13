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
import org.eclipse.ui.IWorkbenchPartReference;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @version $Id$
 * @since 2.0
 */
public class WithPartId<T extends IWorkbenchPartReference> extends AbstractMatcher<T> {

	private final Matcher<String>	idMatcher;

	WithPartId(Matcher<String> idMatcher) {
		this.idMatcher = idMatcher;
	}

	@Override
	public boolean doMatch(Object item) {
		if (item instanceof IWorkbenchPartReference) {
			IWorkbenchPartReference part = (IWorkbenchPartReference) item;
			return idMatcher.matches(part.getId());
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("with id '").appendDescriptionOf(idMatcher).appendText("'");
	}

	/**
	 * Matches a workbench part (view/editor) with the specified id.
	 * 
	 * @param id the id of the part.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends IWorkbenchPartReference> Matcher<T> withPartId(String id) {
		return withPartId(equalTo(id));
	}

	/**
	 * Matches a workbench part (view/editor) with the specified id.
	 * 
	 * @param idMatcher the part id matcher.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends IWorkbenchPartReference> Matcher<T> withPartId(Matcher<String> idMatcher) {
		return new WithPartId<T>(idMatcher);
	}
}
