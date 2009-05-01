/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
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

/**
 * A strategy that can type keys on the keyboard.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public interface KeyboardStrategy {
	/**
	 * Presses the specified key.
	 * 
	 * @param key the keystroke to press down
	 */
	void pressKey(KeyStroke key);

	/**
	 * Releases the specified key.
	 * 
	 * @param key the keystroke to release.
	 */
	void releaseKey(KeyStroke key);
}
