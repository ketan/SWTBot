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
package org.eclipse.swtbot.swt.finder.keyboard;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences.KeyboardLayoutDetector;
import org.junit.After;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeyboardLayoutTest {

	@Test
	public void shouldBeAbleToParseUserProvidedKeyboardLayout() throws Exception {
		KeyboardLayout layout = KeyboardLayout.getKeyboardLayout("com.foo.bar.BAZ");
		assertEquals(keys(SWT.SHIFT, 't'), layout.keyStrokeFor('#'));
	}

	@Test
	public void shouldBeAbleToParseUserProvidedKeyboardLayoutViaPreference() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = "com.foo.bar.MAC_FOOBAR";
		KeyboardLayout layout = KeyboardLayout.getDefaultKeyboardLayout();
		assertEquals(keys(SWT.SHIFT, 'Y'), layout.keyStrokeFor('*'));
	}

	@After
	public void tearDown() {
		SWTBotPreferences.KEYBOARD_LAYOUT = KeyboardLayoutDetector.detectKeyboard();
	}

	private KeyStroke keys(int modifierKeys, char c) {
		return KeyStroke.getInstance(modifierKeys, c);
	}

}
