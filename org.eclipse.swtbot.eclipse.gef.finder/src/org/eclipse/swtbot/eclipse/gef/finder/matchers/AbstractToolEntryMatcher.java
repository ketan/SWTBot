/*******************************************************************************
 * Copyright (c) 2004, 2009 MAKE Technologies Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     MAKE Technologies Inc - initial API and implementation
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - refactoring
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.matchers;
import org.eclipse.gef.palette.ToolEntry;
import org.hamcrest.BaseMatcher;

/**
 * an abstract matcher for matching GEF tool entries.
 * 
 * @see ToolEntry
 * 
 * @author David Green
 */
public abstract class AbstractToolEntryMatcher extends BaseMatcher<ToolEntry> {

	public String description() {
		return "Matches palette tool entries";
	}

	public boolean matches(Object w) {
		if (w instanceof ToolEntry) {
			return matches((ToolEntry)w);
		}
		return false;
	}
	
	protected abstract boolean matches(ToolEntry toolEntry);
}
