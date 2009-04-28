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

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;

/**
 * A factory to convert 'complex' keystrokes to a chain of simple keystrokes.
 * <p>
 * For e.g. on a US keyboard converts a character '$' to two keystrokes SHIFT and 4, and a 'T' to a SHIFT and 'T'.
 * </p>
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Keystrokes {

	/** The SHIFT keystroke. */
	public static final KeyStroke	SHIFT	= KeyStroke.getInstance(SWT.SHIFT, 0);
	/** The CTRL keystroke. */
	public static final KeyStroke	CTRL	= KeyStroke.getInstance(SWT.CTRL, 0);
	/** The ALT keystroke. */
	public static final KeyStroke	ALT		= KeyStroke.getInstance(SWT.ALT, 0);
	/** The COMMAND keystroke. */
	public static final KeyStroke	COMMAND	= KeyStroke.getInstance(SWT.COMMAND, 0);

	/**
	 * @param ch the character to convert to {@link KeyStroke}s.
	 * @return the {@link KeyStroke}s corresponding to the character.
	 */
	public static KeyStroke[] create(char ch) {
		KeyStroke keyStroke = KeyboardLayout.getDefaultKeyboardLayout().keyStrokeFor(ch);
		if (keyStroke.getModifierKeys() == KeyStroke.NO_KEY)
			return new KeyStroke[] { keyStroke };
		KeyStroke modifier = KeyStroke.getInstance(keyStroke.getModifierKeys(), 0);
		KeyStroke key = KeyStroke.getInstance(0, keyStroke.getNaturalKey());
		return new KeyStroke[] { modifier, key };
	}

}
