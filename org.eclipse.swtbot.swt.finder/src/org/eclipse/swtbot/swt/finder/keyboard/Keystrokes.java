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

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
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
	public static final KeyStroke	SHIFT					= KeyStroke.getInstance(SWT.SHIFT, 0);
	/** The CTRL keystroke. */
	public static final KeyStroke	CTRL					= KeyStroke.getInstance(SWT.CTRL, 0);
	/** The ALT keystroke. */
	public static final KeyStroke	ALT						= KeyStroke.getInstance(SWT.ALT, 0);
	/** The COMMAND keystroke. */
	public static final KeyStroke	COMMAND					= KeyStroke.getInstance(SWT.COMMAND, 0);
	/** The ESC keystroke. */
	public static final KeyStroke	ESC						= KeyStroke.getInstance(SWT.ESC, 0);
	/** The backspace keystroke. */
	public static final KeyStroke	BS						= KeyStroke.getInstance(SWT.BS, 0);

	/** The keyboard layout to use for mapping characters. */
	private static KeyboardLayout	defaultKeyboardLayout	= KeyboardLayout.getDefaultKeyboardLayout();

	/**
	 * @param ch the character to convert to {@link KeyStroke}s.
	 * @return the {@link KeyStroke}s corresponding to the character.
	 */
	static KeyStroke[] create(char ch) {
		KeyStroke keyStroke = defaultKeyboardLayout.keyStrokeFor(ch);
		if (keyStroke.getModifierKeys() == KeyStroke.NO_KEY)
			return new KeyStroke[] { keyStroke };
		KeyStroke modifier = KeyStroke.getInstance(keyStroke.getModifierKeys(), 0);
		KeyStroke key = KeyStroke.getInstance(0, keyStroke.getNaturalKey());
		return new KeyStroke[] { modifier, key };
	}

	static char toCharacter(KeyStroke... keys) {
		int modifierKeys = 0;
		int ch = 0;
		for (KeyStroke keyStroke : keys) {
			modifierKeys |= keyStroke.getModifierKeys();
			ch |= keyStroke.getNaturalKey();
		}

		return defaultKeyboardLayout.toCharacter(KeyStroke.getInstance(modifierKeys, ch));
	}

	/**
	 * @param modificationKeys a combination of.
	 * @param c the character to type.
	 * @return the keystrokes corresponding to the modification keys and character.
	 */
	static KeyStroke[] toKeys(int modificationKeys, char c) {
		LinkedHashSet<KeyStroke> keys = new LinkedHashSet<KeyStroke>();
		if (modificationKeys != 0) {
			int[] sortModifierKeys = sortModifierKeys(modificationKeys);
			for (int modifierKey : sortModifierKeys) {
				if (modifierKey != KeyStroke.NO_KEY)
					keys.add(KeyStroke.getInstance(modifierKey, 0));
			}
		}
		if (c != 0)
			keys.addAll(Arrays.asList(Keystrokes.create(c)));
		return keys.toArray(new KeyStroke[] {});
	}

	// FIXME: performance improvement: put in a hash of input and output values.
	private static int[] sortModifierKeys(final int modifierKeys) {
		final IKeyLookup lookup = KeyLookupFactory.getDefault();
		final int[] sortedKeys = new int[4];
		int index = 0;

		if ((modifierKeys & lookup.getAlt()) != 0) {
			sortedKeys[index++] = lookup.getAlt();
		}
		if ((modifierKeys & lookup.getCommand()) != 0) {
			sortedKeys[index++] = lookup.getCommand();
		}
		if ((modifierKeys & lookup.getCtrl()) != 0) {
			sortedKeys[index++] = lookup.getCtrl();
		}
		if ((modifierKeys & lookup.getShift()) != 0) {
			sortedKeys[index++] = lookup.getShift();
		}

		return sortedKeys;
	}

}
