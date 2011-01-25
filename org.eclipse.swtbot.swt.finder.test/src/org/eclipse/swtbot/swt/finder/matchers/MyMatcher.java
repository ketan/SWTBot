/*******************************************************************************
 * Copyright (c) 2011 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import org.hamcrest.Description;

final class MyMatcher<T> extends AbstractMatcher<T> {

	private final boolean	toAnswer;
	boolean					matched;

	public MyMatcher(boolean toAnswer) {
		this.toAnswer = toAnswer;
	}

	public void describeTo(Description description) {

	}

	@Override
	protected boolean doMatch(Object item) {
		this.matched = true;
		return toAnswer;
	}

}
