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
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MenuMnemonicTextMatcherTest {

	@Test
	public void matchesMenuWithMnemonic() throws Exception {
		Matcher matcher = withMnemonic("New Mnemonic");
		assertTrue(matcher.matches(new ObjectWithGetText("New &Mnemonic\tCTRL_M")));
	}

	@Test
	public void matchesMenuWithoutMnemonic() throws Exception {
		Matcher matcher = withMnemonic("New Mnemonic");
		assertTrue(matcher.matches(new ObjectWithGetText("New Mnemonic\tCTRL_M")));
	}

	@Test
	public void matchesMenuWithoutAccesor() throws Exception {
		Matcher matcher = withMnemonic("New Mnemonic");
		assertTrue(matcher.matches(new ObjectWithGetText("New Mnemonic")));
	}

	@Test
	public void matchesMnemonic() throws Exception {
		Object object = new ObjectWithGetText("&New Mnemonic");
		assertTrue(withMnemonic("New Mnemonic").matches(object));
	}

	@Test
	public void matchesWithoutMnemonic() throws Exception {
		Object object = new ObjectWithGetText("New Mnemonic");
		assertTrue(withMnemonic("New Mnemonic").matches(object));
	}

}
