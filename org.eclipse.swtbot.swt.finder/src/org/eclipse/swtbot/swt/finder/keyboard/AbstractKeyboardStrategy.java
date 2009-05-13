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

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class AbstractKeyboardStrategy implements KeyboardStrategy {

	/**
	 * Presses the specified key.
	 * 
	 * @param key the keystroke to press down
	 */
	protected abstract void pressKey(KeyStroke key);

	/**
	 * Releases the specified key.
	 * 
	 * @param key the keystroke to release.
	 */
	protected abstract void releaseKey(KeyStroke key);

	public void pressKeys(KeyStroke... keys) {
		for (KeyStroke key : keys) {
			assertKey(key);
			pressKey(key);
		}
	}

	public void releaseKeys(KeyStroke... keys) {
		for (KeyStroke key : keys) {
			assertKey(key);
			releaseKey(key);
		}
	}

	private void assertKey(KeyStroke key) {
		boolean hasNaturalKey = key.getNaturalKey() != KeyStroke.NO_KEY;
		boolean hasModifiers = key.getModifierKeys() != KeyStroke.NO_KEY;

		Assert.isTrue(hasNaturalKey ^ hasModifiers, "You just gave me a complex keystroke. Please split the keystroke into multiple keystrokes.");
	}

}
