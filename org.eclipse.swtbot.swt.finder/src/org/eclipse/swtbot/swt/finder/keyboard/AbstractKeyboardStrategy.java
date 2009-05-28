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

import org.apache.log4j.Logger;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * Implementors must have a default no-args constructor.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class AbstractKeyboardStrategy implements KeyboardStrategy {

	protected final Logger	log;

	protected AbstractKeyboardStrategy() {
		this.log = Logger.getLogger(getClass());
	}

	public void init(Widget widget, SelfDescribing description) {
		// do nothing
	}

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
		assertKeys(keys);
		for (KeyStroke key : keys) {
			log.trace(MessageFormat.format("Pressing down key {0}", key));
			pressKey(key);
		}
	}

	public void releaseKeys(KeyStroke... keys) {
		assertKeys(keys);
		for (KeyStroke key : keys) {
			log.trace(MessageFormat.format("Releasing key {0}", key));
			releaseKey(key);
		}
	}

	private void assertKeys(KeyStroke... keys) {
		for (KeyStroke key : keys) {
			assertKey(key);
		}
	}

	private void assertKey(KeyStroke key) {
		boolean hasNaturalKey = key.getNaturalKey() != KeyStroke.NO_KEY;
		boolean hasModifiers = key.getModifierKeys() != KeyStroke.NO_KEY;

		Assert.isTrue(hasNaturalKey ^ hasModifiers, "You just gave me a complex keystroke. Please split the keystroke into multiple keystrokes.");
	}

}
