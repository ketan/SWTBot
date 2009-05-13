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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class MockKeyboardStrategy extends AbstractKeyboardStrategy {

	private MyWidget	widget;

	public MockKeyboardStrategy(Widget widget, SelfDescribing description) {
		this.widget = new MyWidget(widget, description);
	}

	public void pressKeys(KeyStroke... keys) {
		assertNotDisposed();
		widget.notify(SWT.KeyDown, event(keys));
	}

	public void releaseKeys(KeyStroke... keys) {
		assertNotDisposed();
		widget.notify(SWT.KeyUp, event(keys));
	}

	private Event event(KeyStroke... keys) {
		int modifiers = SWT.NONE;
		int ch = 0;

		Event e = new Event();
		e.character = Keystrokes.toCharacter(keys);
		e.widget = widget.widget;
		e.keyCode = ch;
		e.stateMask = modifiers;
		return e;
	}

	private void assertNotDisposed() {
		Assert.isTrue(!widget.widget.isDisposed(), "The widget has been disposed.");
	}

	public void pressKey(KeyStroke key) {
		throw new UnsupportedOperationException("This operation is not supported");
	}

	public void releaseKey(KeyStroke key) {
		throw new UnsupportedOperationException("This operation is not supported");
	}

	private static class MyWidget extends AbstractSWTBot<Widget> {

		public MyWidget(Widget w, SelfDescribing description) throws WidgetNotFoundException {
			super(w, description);
		}

		public void notify(int eventType, Event createEvent) {
			super.notify(eventType, createEvent);
		}
	}

}
