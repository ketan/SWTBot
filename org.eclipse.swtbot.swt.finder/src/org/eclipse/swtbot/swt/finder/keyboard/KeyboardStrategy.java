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
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.SelfDescribing;

/**
 * A strategy that can type keys on the keyboard. Implementors are adviced to use {@link AbstractKeyboardStrategy} and
 * must have a default no-args constructor.
 * 
 * @see AbstractKeyboardStrategy
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public interface KeyboardStrategy {

	/**
	 * @param widget the widget on which this keyboard may type. Note that it is not necessary that the strategy
	 *            actually types on this widget.
	 * @param description the description of the widget.
	 */
	void init(Widget widget, SelfDescribing description);

	/**
	 * Presses the specified keys.
	 * 
	 * @param keys the keystrokes to press down
	 */
	void pressKeys(KeyStroke... keys);

	/**
	 * Presses the specified keys.
	 * 
	 * @param keys the keystrokes to press down
	 */
	void releaseKeys(KeyStroke... keys);
}
