/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class RegexTextMatcherTest {

	@Test
	public void cannotNotMatchText() throws Exception {
		Matcher matcher = withRegex("Some Text");
		assertFalse(matcher.matches(new Object()));
	}

	@Test
	public void doesNotMatchNullText() throws Exception {
		Matcher matcher = withRegex("Some Text");
		assertFalse(matcher.matches(new ObjectWithGetText(null)));
	}

	@Test
	public void doesNotMatchRegex() throws Exception {
		Matcher matcher = withRegex("Some (.*) Text");
		assertFalse(matcher.matches(new ObjectWithGetText("Some text that should not match")));
	}

	@Test
	public void matchesRegex() throws Exception {
		Matcher matcher = withRegex("Some (.*) Text");
		assertTrue(matcher.matches(new ObjectWithGetText("Some long string Text")));
	}

	@Test
	public void getsToString() throws Exception {
		Matcher matcher = withRegex("Some Text");
		assertEquals("with regex 'Some Text'", matcher.toString());
	}

}
