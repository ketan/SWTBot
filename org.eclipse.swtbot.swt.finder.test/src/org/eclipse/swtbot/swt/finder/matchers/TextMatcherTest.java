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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withTextIgnoringCase;
import junit.framework.TestCase;

import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: TextMatcherTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class TextMatcherTest extends TestCase {


	public void testDoesNotMatchObjectsWithNoGetTextMethod() throws Exception {
		Matcher matcher = withText("Some Text");
		assertFalse(matcher.matches(new Object()));
	}


	public void testDoesNotMatchObjectsWithNullGetText() throws Exception {
		Matcher matcher = withText("Some Text");
		assertFalse(matcher.matches(new ObjectWithGetText(null)));
	}


	public void testDoesNotMatchText() throws Exception {
		Matcher matcher = withText("Some Text");
		assertFalse(matcher.matches(new Object()));
	}


	public void testMatchText() throws Exception {
		Matcher matcher = withText("Some Text");
		assertTrue(matcher.matches(new ObjectWithGetText("Some Text")));
	}


	public void testMatchTextIgnoreCase() throws Exception {
		Matcher matcher = withTextIgnoringCase("Some Text");
		assertTrue(matcher.matches(new ObjectWithGetText("some text")));
	}


	public void testToString() throws Exception {
		Matcher matcher = withText("Some Text");
		assertEquals("with text 'Some Text'", matcher.toString());
	}

}
