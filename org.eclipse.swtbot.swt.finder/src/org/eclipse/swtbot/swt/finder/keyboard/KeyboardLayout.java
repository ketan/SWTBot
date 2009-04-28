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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.utils.FileUtils;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;

/**
 * Allows mapping of characters to {@link KeyStroke}s based on keyboard layouts.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class KeyboardLayout {
	private final Map<Character, KeyStroke>	keyStrokes	= new TreeMap<Character, KeyStroke>();
	private final String					layoutName;

	private KeyboardLayout(String name, URL resource) throws IOException {
		this.layoutName = name;
		initialiseDefaults();
		parseKeyStrokes(resource);
	}

	public String toString() {
		return layoutName + " keyboard layout";
	}

	/**
	 * Returns the keystroke for typing the specified character.
	 * <p>
	 * E.g. 'T' will correspond to SHIFT+T. 't' will correspond to 'T'. '!' will correspond to SHIFT+1 on the US
	 * keyboard.
	 * </p>
	 * 
	 * @param ch a character.
	 * @return the keystroke applicable corresponding to the character.
	 */
	public KeyStroke keyStrokeFor(char ch) {
		KeyStroke keyStroke = keyStrokes.get(ch);
		if (keyStroke != null) {
			return keyStroke;
		}
		throw new IllegalArgumentException("no stroke available for character '" + ch + "'");
	}

	/**
	 * @return the default keyboard layout.
	 * @see SWTBotPreferences#getKeyboardLayout()
	 */
	public static KeyboardLayout getDefaultKeyboardLayout() {
		return getKeyboardLayout(SWTBotPreferences.getKeyboardLayout());
	}

	/**
	 * @param layoutName the layout of the keyboard.
	 * @return the keyboard layout corresponding to the specified layout.
	 */
	public static KeyboardLayout getKeyboardLayout(String layoutName) {
		URL configURL = KeyboardLayout.class.getResource(layoutName + ".keyboard");

		if (configURL == null) {
			throw new IllegalArgumentException("keyboard layout " + layoutName + " not available.");
		}

		try {
			return new KeyboardLayout(layoutName, configURL);
		} catch (IOException e) {
			throw new IllegalStateException("could not parse " + layoutName + " keyboard layout properties");
		}
	}

	private void initialiseDefaults() {
		for (char ch = '0'; ch <= '9'; ch++) {
			keyStrokes.put(ch, KeyStroke.getInstance(0, ch));
		}
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			keyStrokes.put(Character.toLowerCase(ch), KeyStroke.getInstance(0, ch));
			keyStrokes.put(ch, KeyStroke.getInstance(SWT.SHIFT, ch));
		}
		keyStrokes.put('\n', KeyStroke.getInstance(0, '\n'));
		keyStrokes.put('\t', KeyStroke.getInstance(0, '\t'));
		keyStrokes.put('\b', KeyStroke.getInstance(0, '\b'));
		keyStrokes.put(' ', KeyStroke.getInstance(0, ' '));
	}

	private void parseKeyStrokes(URL resource) throws IOException {
		String contents = FileUtils.read(resource);
		BufferedReader in = new BufferedReader(new StringReader(contents));
		parseKeyStrokes(in);
	}

	private void parseKeyStrokes(BufferedReader in) throws IOException {
		String line;
		while ((line = in.readLine()) != null) {
			char ch = line.charAt(0);
			String keyStrokeSpec = line.substring(2).replaceAll(" \\+ ", "+");
			try {
				keyStrokes.put(ch, KeyStroke.getInstance(keyStrokeSpec));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
