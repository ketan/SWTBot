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
package org.eclipse.swtbot.swt.recorder.ui;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ClearButtonSelectionListener implements SelectionListener {

	private final SWTBotRecorderWindow	recorderUI;
	private final Button			clearButton;

	/**
	 * @param clearButton the clear button
	 * @param recorderUI the ui to be invoked when the button is clicked.
	 */
	public ClearButtonSelectionListener(SWTBotRecorderWindow recorderUI, Button clearButton) {
		this.recorderUI = recorderUI;
		this.clearButton = clearButton;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		recorderUI.clear();
		clearButton.setSelection(false);
	}

	public void widgetSelected(SelectionEvent e) {
		widgetDefaultSelected(e);
	}

}
