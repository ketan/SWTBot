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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

class SWTKeyboardStrategy extends AbstractKeyboardStrategy {

	private final Display	display;

	SWTKeyboardStrategy() {
		this.display = SWTUtils.display();
	}

	public void pressKey(KeyStroke key) {
		Assert.isTrue(display.post(keyEvent(key, SWT.KeyDown)), "Could not post keyevent.");
		display.wake();
	}

	public void releaseKey(KeyStroke key) {
		Assert.isTrue(display.post(keyEvent(key, SWT.KeyUp)), "Could not post keyevent.");
		display.wake();
	}

	private Event keyEvent(KeyStroke modifier, int type) {
		Event e = new Event();
		e.type = type;
		e.keyCode = modifier.getModifierKeys();
		e.character = (char) modifier.getNaturalKey();
		return e;
	}

}