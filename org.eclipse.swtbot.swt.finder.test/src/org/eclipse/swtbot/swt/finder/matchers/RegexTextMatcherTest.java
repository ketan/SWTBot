/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
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
import junit.framework.TestCase;

import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class RegexTextMatcherTest extends TestCase {

	public void testCannotNotMatchText() throws Exception {
		Matcher matcher = withRegex("Some Text");
		assertFalse(matcher.matches(new Object()));
	}

	public void testDoesNotMatchNullText() throws Exception {
		Matcher matcher = withRegex("Some Text");
		assertFalse(matcher.matches(new ObjectWithGetText(null)));
	}

	public void testDoesNotMatchRegex() throws Exception {
		Matcher matcher = withRegex("Some (.*) Text");
		assertFalse(matcher.matches(new ObjectWithGetText("Some text that should not match")));
	}

	public void testMatchesRegex() throws Exception {
		Matcher matcher = withRegex("Some (.*) Text");
		assertTrue(matcher.matches(new ObjectWithGetText("Some long string Text")));
	}

	public void testToString() throws Exception {
		Matcher matcher = withRegex("Some Text");
		assertEquals("with regex 'Some Text'", matcher.toString());
	}

}
