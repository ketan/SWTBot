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
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class MockKeyboardStrategy implements KeyboardStrategy {

	private final Widget	widget;

	public MockKeyboardStrategy(Widget widget) {
		this.widget = widget;
	}

	public void pressKey(KeyStroke key) {
		assertDisposed();
		
	}

	public void releaseKey(KeyStroke key) {
		assertDisposed();
	}

	private void assertDisposed() {
		Assert.isTrue(!widget.isDisposed(), "The widget has been disposed.");
	}

}
