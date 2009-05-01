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

class AWTKeyboardStrategy implements KeyboardStrategy {

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
		else if (key.getModifierKeys() == SWT.CTRL)
			return KeyEvent.VK_CONTROL;
		else if (key.getModifierKeys() == SWT.SHIFT)
			return KeyEvent.VK_SHIFT;
		else if (key.getModifierKeys() == SWT.ALT)
			return KeyEvent.VK_ALT;
		else if (key.getModifierKeys() == SWT.COMMAND)
			return KeyEvent.VK_WINDOWS;
		throw new IllegalArgumentException("Could not understand keystroke " + key);
	}

}