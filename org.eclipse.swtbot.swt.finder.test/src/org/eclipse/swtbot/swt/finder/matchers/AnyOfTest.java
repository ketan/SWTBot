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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.anyOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AnyOfTest {

	@Test
	public void testShouldAnswerTrueIfAnyMatcherMatches() throws Exception {
		MyMatcher matcher1 = new MyMatcher(true);
		MyMatcher matcher2 = new MyMatcher(false);
		assertTrue(anyOf(matcher1, matcher2).matches(null));
		assertTrue(matcher1.matched);
		assertFalse(matcher2.matched);
	}

	@Test
	public void testShouldAnswerFalseIfNoMatchersMatche() throws Exception {
		MyMatcher matcher1 = new MyMatcher(false);
		MyMatcher matcher2 = new MyMatcher(false);
		assertFalse(anyOf(matcher1, matcher2).matches(null));
		assertTrue(matcher1.matched);
		assertTrue(matcher2.matched);
	}
}
