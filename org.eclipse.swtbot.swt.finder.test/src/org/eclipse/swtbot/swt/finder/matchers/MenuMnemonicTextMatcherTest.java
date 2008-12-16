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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import junit.framework.TestCase;

import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: MenuMnemonicTextMatcherTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class MenuMnemonicTextMatcherTest extends TestCase {

	public void testMatchesMenuWithMnemonic() throws Exception {
		Matcher matcher = withMnemonic("New Mnemonic");
		assertTrue(matcher.matches(new ObjectWithGetText("New &Mnemonic\tCTRL_M")));
	}

	public void testMatchesMenuWithoutMnemonic() throws Exception {
		Matcher matcher = withMnemonic("New Mnemonic");
		assertTrue(matcher.matches(new ObjectWithGetText("New Mnemonic\tCTRL_M")));
	}

	public void testMatchesMenuWithoutAccesor() throws Exception {
		Matcher matcher = withMnemonic("New Mnemonic");
		assertTrue(matcher.matches(new ObjectWithGetText("New Mnemonic")));
	}

	public void testMatchesMnemonic() throws Exception {
		Object object = new ObjectWithGetText("&New Mnemonic");
		assertTrue(withMnemonic("New Mnemonic").matches(object));
	}

	public void testMatchesWithoutMnemonic() throws Exception {
		Object object = new ObjectWithGetText("New Mnemonic");
		assertTrue(withMnemonic("New Mnemonic").matches(object));
	}

}
