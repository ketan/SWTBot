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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Keys {

	public static List<Character> getInputs() {
		List<Character> chars = new ArrayList<Character>();
		alphabets(chars);
		numbers(chars);
		specialChars(chars);
		return chars;
	}

	/**
	 * These are specific to the US keyboard. These are characters that are available on the keyboard, without pressing
	 * any of the modifier keys -- SHIFT, ALT, CTRL, COMMAND. For e.g. On a US keyboard, this should contain '/' but not
	 * '?' since '?' is a combination of SHIFT+/.
	 */
	private static void specialChars(List<Character> chars) {
		Character[] others = new Character[] { '`', '-', '=', '[', ']', '\\', ';', '\'', ',', '.', '/' };
		chars.addAll(Arrays.asList(others));
	}

	private static void numbers(List<Character> chars) {
		for (char i = '0'; i <= '9'; i++) {
			chars.add(i);
		}
	}

	private static void alphabets(List<Character> chars) {
		for (char i = 'a'; i <= 'z'; i++) {
			chars.add(i);
		}
	}

}
