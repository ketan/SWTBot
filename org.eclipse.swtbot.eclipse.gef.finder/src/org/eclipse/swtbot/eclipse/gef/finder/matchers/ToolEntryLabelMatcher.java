/*******************************************************************************
 * Copyright (c) 2004, 2009 MAKE Technologies Inc and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   MAKE Technologies Inc - initial API and implementation
 *   Mariot Chauvin <mariot.chauvin@obeo.fr> - refactoring
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.matchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.eclipse.gef.palette.ToolEntry;
import org.hamcrest.Description;

/**
 * match the label of a tool entry
 * 
 * @author David Green
 */
public class ToolEntryLabelMatcher extends AbstractToolEntryMatcher {

	private final Pattern pattern;

	//TODO comment
	public ToolEntryLabelMatcher(String name) {
		this(Pattern.compile(Pattern.quote(name)));
	}
	
	//TODO comment
	public ToolEntryLabelMatcher(Pattern pattern) {
		this.pattern = pattern;
	}

	/*
	 * {@inheritDoc}
	 *
	 * @see net.sf.swtbot.eclipse.gef.matchers.AbstractToolEntryMatcher#matches(org.eclipse.gef.palette.ToolEntry)
	 */
	@Override
	protected boolean matches(final ToolEntry toolEntry) {
		final String label = toolEntry.getLabel();
		if (label == null) {
			return false;
		}
		final Matcher matcher = pattern.matcher(label);
		return matcher.matches();
	}

	/*
	 * {@inheritDoc}
	 *
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
    public void describeTo(final Description description) {
        description.appendText("");
    }

}
