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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;

/**
 * Sends keyboard notifications using AWT {@link Robot}.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class AWTKeyboardStrategy extends AbstractKeyboardStrategy {

	private final Robot	robot;

	AWTKeyboardStrategy() {
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	public void pressKey(KeyStroke key) {
		robot.keyPress(key(key));
	}

	public void releaseKey(KeyStroke key) {
		robot.keyRelease(key(key));

	}

	private int key(KeyStroke key) {
		if (key.getNaturalKey() != 0)
			return key.getNaturalKey();
		switch (key.getModifierKeys()) {
		case SWT.CTRL:
			return KeyEvent.VK_CONTROL;
		case SWT.SHIFT:
			return KeyEvent.VK_SHIFT;
		case SWT.ALT:
			return KeyEvent.VK_ALT;
		case SWT.COMMAND:
			return KeyEvent.VK_WINDOWS;
		case SWT.ARROW_RIGHT:
			return KeyEvent.VK_RIGHT;
		case SWT.ARROW_DOWN:
			return KeyEvent.VK_DOWN;
		case SWT.ARROW_LEFT:
			return KeyEvent.VK_LEFT;
		case SWT.ARROW_UP:
			return KeyEvent.VK_UP;
		case SWT.ESC:
			return KeyEvent.VK_ESCAPE;
		case SWT.BS:
			return KeyEvent.VK_BACK_SPACE;

		default:
			throw new IllegalArgumentException("Could not understand keystroke " + key);
		}
	}

}
