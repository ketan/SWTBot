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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class Keyboard {

	private final Display display;

	public Keyboard(Display display) {
		this.display = display;
	}

	public void pressShortcut(KeyStroke... keys) {
		pressShortcut(new ArrayList<KeyStroke>(Arrays.asList(keys)));
	}

	public void pressShortcut(KeyStroke one, KeyStroke[] others) {
		List<KeyStroke> keys = new ArrayList<KeyStroke>();
		keys.add(one);
		keys.addAll(Arrays.asList(others));
		pressShortcut(keys);
	}

	public void pressShortcut(KeyStroke one, KeyStroke two, KeyStroke[] others) {
		List<KeyStroke> keys = new ArrayList<KeyStroke>();
		keys.add(one);
		keys.add(two);
		keys.addAll(Arrays.asList(others));
		pressShortcut(keys);
	}

	public void pressShortcut(KeyStroke one, KeyStroke two, KeyStroke three, KeyStroke[] others) {
		List<KeyStroke> keys = new ArrayList<KeyStroke>();
		keys.add(one);
		keys.add(two);
		keys.add(three);
		keys.addAll(Arrays.asList(others));
		pressShortcut(keys);
	}

	public void pressShortcut(KeyStroke one, KeyStroke two, KeyStroke three, KeyStroke four, KeyStroke[] others) {
		List<KeyStroke> keys = new ArrayList<KeyStroke>();
		keys.add(one);
		keys.add(two);
		keys.add(three);
		keys.add(four);
		keys.addAll(Arrays.asList(others));
		pressShortcut(keys);
	}

	private void pressShortcut(List<KeyStroke> strokes) {
		pressKeys(strokes, SWT.KeyDown);
		Collections.reverse(strokes);
		pressKeys(strokes, SWT.KeyUp);
	}

	private void pressKeys(List<KeyStroke> keys, int type) {
		for (KeyStroke key : keys) {
			pressKey(key, type);
		}
	}

	private void pressKey(KeyStroke key, int type) {
		Event e = keyEvent(key);
		e.type = type;
		display.post(e);
		SWTUtils.sleep(50);
		display.wake();
	}

	private Event keyEvent(KeyStroke modifier) {
		Event e = new Event();
		e.keyCode = modifier.getModifierKeys();
		e.character = (char) modifier.getNaturalKey();
		return e;
	}

	public void pressKeys(String text) {
		for (int i = 0; i < text.length(); i++) {
			KeyStroke[] keys = Keystrokes.create(text.charAt(i));
			pressShortcut(keys);
		}
	}
}
