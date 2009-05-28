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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;

/**
 * Sends keyboard notifications using AWT {@link Robot}.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class AWTKeyboardStrategy extends AbstractKeyboardStrategy {

	private final Robot							robot;

	private static final Map<Integer, Integer>	modifierKeyMapping		= new HashMap<Integer, Integer>();
	private static final Map<Integer, Integer>	naturalKeyKeyMapping	= new HashMap<Integer, Integer>();

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
		if (key.getModifierKeys() != KeyStroke.NO_KEY)
			return sendModifierKeys(key);
		if (key.getNaturalKey() != KeyStroke.NO_KEY)
			return sendNaturalKey(key);
		throw new IllegalArgumentException("Could not understand keystroke " + key);
	}

	private int sendNaturalKey(KeyStroke key) {
		Integer awtKey = naturalKeyKeyMapping.get(key.getNaturalKey());
		return awtKey != null ? awtKey : key.getNaturalKey();
	}

	private int sendModifierKeys(KeyStroke key) {
		Integer awtKey = modifierKeyMapping.get(key.getModifierKeys());
		if (awtKey != null)
			return awtKey;
		throw new IllegalArgumentException("Could not understand keystroke " + key);
	}

	static {
		/* the modifier keys */
		addModifierKeyMapping(SWT.CTRL, KeyEvent.VK_CONTROL);
		addModifierKeyMapping(SWT.SHIFT, KeyEvent.VK_SHIFT);
		addModifierKeyMapping(SWT.ALT, KeyEvent.VK_ALT);
		addModifierKeyMapping(SWT.COMMAND, KeyEvent.VK_WINDOWS);

		/* the natural keys */
		addNaturalKeyMapping(SWT.ESC, KeyEvent.VK_ESCAPE);
		/* function keys */
		addNaturalKeyMapping(SWT.F1, KeyEvent.VK_F1);
		addNaturalKeyMapping(SWT.F2, KeyEvent.VK_F2);
		addNaturalKeyMapping(SWT.F3, KeyEvent.VK_F3);
		addNaturalKeyMapping(SWT.F4, KeyEvent.VK_F4);
		addNaturalKeyMapping(SWT.F5, KeyEvent.VK_F5);
		addNaturalKeyMapping(SWT.F6, KeyEvent.VK_F6);
		addNaturalKeyMapping(SWT.F7, KeyEvent.VK_F7);
		addNaturalKeyMapping(SWT.F8, KeyEvent.VK_F8);
		addNaturalKeyMapping(SWT.F9, KeyEvent.VK_F9);
		addNaturalKeyMapping(SWT.F10, KeyEvent.VK_F10);
		addNaturalKeyMapping(SWT.F11, KeyEvent.VK_F11);
		addNaturalKeyMapping(SWT.F12, KeyEvent.VK_F12);

		addNaturalKeyMapping(SWT.BS, KeyEvent.VK_BACK_SPACE);
		addNaturalKeyMapping(SWT.DEL, KeyEvent.VK_DELETE);

		/* direction and page navigation keys */
		addNaturalKeyMapping(SWT.HOME, KeyEvent.VK_HOME);
		addNaturalKeyMapping(SWT.END, KeyEvent.VK_END);
		addNaturalKeyMapping(SWT.PAGE_UP, KeyEvent.VK_PAGE_UP);
		addNaturalKeyMapping(SWT.PAGE_DOWN, KeyEvent.VK_PAGE_DOWN);
		addNaturalKeyMapping(SWT.ARROW_RIGHT, KeyEvent.VK_RIGHT);
		addNaturalKeyMapping(SWT.ARROW_DOWN, KeyEvent.VK_DOWN);
		addNaturalKeyMapping(SWT.ARROW_LEFT, KeyEvent.VK_LEFT);
		addNaturalKeyMapping(SWT.ARROW_UP, KeyEvent.VK_UP);

		/* special characters that don't map to the ascii codes. */
		addNaturalKeyMapping('`', KeyEvent.VK_BACK_QUOTE);
	}

	private static void addModifierKeyMapping(int swtKey, int awtKey) {
		modifierKeyMapping.put(swtKey, awtKey);
	}

	private static void addNaturalKeyMapping(int swtKey, int awtKey) {
		naturalKeyKeyMapping.put(swtKey, awtKey);
	}

}
