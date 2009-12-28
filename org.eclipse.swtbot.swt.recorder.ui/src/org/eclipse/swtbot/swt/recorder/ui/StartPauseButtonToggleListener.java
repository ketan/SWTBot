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

public class StartPauseButtonToggleListener implements SelectionListener {

	private final Button			recordButton;
	private final SWTBotRecorderWindow	recorderUI;

	public StartPauseButtonToggleListener(SWTBotRecorderWindow recorderUI, Button recordButton) {
		this.recorderUI = recorderUI;
		this.recordButton = recordButton;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		if (recorderUI.isRunning()) {
			recordButton.setImage(SWTBotRecorderWindow.imageRegistry.get(SWTBotRecorderWindow.START));
			recorderUI.stop();
		} else {
			recordButton.setImage(SWTBotRecorderWindow.imageRegistry.get(SWTBotRecorderWindow.PAUSE));
			recorderUI.unPause();
		}
		recorderUI.setButtonStates();
	}

	public void widgetSelected(SelectionEvent e) {
		widgetDefaultSelected(e);
	}

}
