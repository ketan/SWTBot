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
 * @version $Id: SaveButtonSelectionListener.java 1192 2008-12-02 07:15:31Z kpadegaonkar $
 */
public class SaveButtonSelectionListener implements SelectionListener {

	private final Button			saveButton;
	private final SWTBotRecorderUI	recorderUI;

	/**
	 * @param saveButton the button to listen
	 * @param recorderUI the ui to be invoked when the button is clicked.
	 * @see SWTBotRecorderUI#save()
	 */
	public SaveButtonSelectionListener(SWTBotRecorderUI recorderUI, Button saveButton) {
		this.recorderUI = recorderUI;
		this.saveButton = saveButton;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		saveButton.setSelection(false);
		recorderUI.save();
	}

	public void widgetSelected(SelectionEvent e) {
		widgetDefaultSelected(e);
	}

}
