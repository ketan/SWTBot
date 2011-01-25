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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 *
 */
public class AllOfTest {
	@Test
	public void testShouldAnswerTrueIfAllMatcherMatches() throws Exception {
		MyMatcher matcher1 = new MyMatcher(true);
		MyMatcher matcher2 = new MyMatcher(true);
		assertTrue(allOf(matcher1, matcher2).matches(null));
		assertTrue(matcher1.matched);
		assertTrue(matcher2.matched);
	}

	@Test
	public void testShouldAnswerFalseIfAnyMatchersDoesNotMatch() throws Exception {
		MyMatcher matcher1 = new MyMatcher(true);
		MyMatcher matcher2 = new MyMatcher(false);
		assertFalse(allOf(matcher1, matcher2).matches(null));
		assertTrue(matcher1.matched);
		assertTrue(matcher2.matched);
	}
}
