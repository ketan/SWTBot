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
package org.eclipse.swtbot.eclipse.spy;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotExecutionListener.java 1193 2008-12-02 07:22:51Z kpadegaonkar $
 */
public class SWTBotExecutionListener implements Listener {
	public void handleEvent(final Event e) {
		if (e.stateMask == SWT.CTRL && e.keyCode == SWT.SHIFT) {
			Thread thread = new PlayBackThread(e);
			thread.start();
		}
	}
}
