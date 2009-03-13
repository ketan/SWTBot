/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Patel - initial API and implementation (Bug 259860)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Item;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author Ketan Patel (Bug 259860)
 * @version $Id$
 */
public class WithItem<T extends Item> extends AbstractMatcher<T> {
	private final Matcher<?>	itemMatcher;
	private final ArrayList<T>	matches;

	/**
	 * Matches widgets which contains <code>item(s)</code>, as returned by <code>getItems</code> method, that match
	 * given matcher...i.e CTabFolder with Item with text "xyz"
	 * 
	 * @param itemMatcher the item matcher
	 */
	WithItem(Matcher<?> itemMatcher) {
		this.itemMatcher = itemMatcher;
		matches = new ArrayList<T>();
	}

	public void describeTo(Description description) {
		description.appendText("with item");
		this.itemMatcher.describeTo(description);
	}

	protected boolean doMatch(Object obj) {
		boolean result = false;
		try {
			for (T item : getItems(obj)) {
				if (this.itemMatcher.matches(item)) {
					this.matches.add(item);
					result = true;
				}
			}
		} catch (Exception e) {
			// do nothing
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private T[] getItems(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return (T[]) SWTUtils.invokeMethod(obj, "getItems");
	}

	public ArrayList<T> getAllMatches() {
		return this.matches;
	}

	public Object get(int index) {
		return this.matches.get(index);
	}

	/**
	 * Returns a matcher that matches objects containing an item that matches the matcher.
	 * <p>
	 * <strong>Note:</strong> This invokes getItems method on the object and expects to see an array as a return value.
	 * </p>
	 * 
	 * @param matcher the matcher.
	 * @return a matcher.
	 */
	@Factory
	public static <T extends Item> WithItem<T> withItem(Matcher<?> matcher) {
		return new WithItem<T>(matcher);
	}
}
