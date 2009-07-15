/*******************************************************************************
 * Copyright (c) 2008-2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.waits;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

/**
 * Waits for objects to appear until the matcher evaluates to true.
 *
 * @see Conditions
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class WaitForObjectCondition<T> extends DefaultCondition {

	/** The matcher that is used to match widgets. */
	protected final Matcher<T>	matcher;
	private final List<T>		matches;

	/**
	 * Waits until the matcher is true.
	 *
	 * @param matcher the matcher.
	 */
	public WaitForObjectCondition(Matcher<T> matcher) {
		this.matcher = matcher;
		matches = new ArrayList<T>();
	}

	public boolean test() throws Exception {
		matches.clear();
		matches.addAll(findMatches());
		return !matches.isEmpty();
	}

	/**
	 * @return the matches that subclasses that matched.
	 */
	protected abstract List<T> findMatches();

	/**
	 * @return all objects that matched the matcher.
	 */
	public List<T> getAllMatches() {
		return this.matches;
	}

	/**
	 * @param index the index of the object.
	 * @return the element at the specified index in the list of matched objects.
	 */
	public T get(int index) {
		return this.matches.get(index);
	}
}
