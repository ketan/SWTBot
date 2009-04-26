/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Keystrokes {

	public static final KeyStroke SHIFT = KeyStroke.getInstance(SWT.SHIFT, 0);
	public static final KeyStroke CTRL = KeyStroke.getInstance(SWT.CTRL, 0);
	public static final KeyStroke ALT = KeyStroke.getInstance(SWT.ALT, 0);
	public static final KeyStroke COMMAND = KeyStroke.getInstance(SWT.COMMAND, 0);

	public static KeyStroke[] create(String characters) {
		List<KeyStroke> keys = new ArrayList<KeyStroke>();

		for (int i = 0; i < characters.length(); i++) {
			keys.addAll(Arrays.asList(create(characters.charAt(i))));
		}

		return keys.toArray(new KeyStroke[0]);
	}

	public static KeyStroke[] create(char ch) {
		KeyStroke keyStroke = KeyboardLayout.getDefaultKeyboardLayout().keyStrokeFor(ch);
		if (keyStroke.getModifierKeys() == KeyStroke.NO_KEY) {
			return new KeyStroke[] { keyStroke };
		} else {
			KeyStroke modifier = KeyStroke.getInstance(keyStroke.getModifierKeys(), 0);
			KeyStroke key = KeyStroke.getInstance(keyStroke.getNaturalKey());
			return new KeyStroke[] { modifier, key };
		}
	}

}
