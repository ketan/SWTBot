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
final class StopRecorderListener implements SelectionListener {

	private final SWTBotRecorderUI	recorderUI;
	private final Button			startPauseButton;
	private final Button			stopRecorderButton;

	public StopRecorderListener(SWTBotRecorderUI recorderUI, Button startPauseButton, Button stopRecorderButton) {
		this.recorderUI = recorderUI;
		this.startPauseButton = startPauseButton;
		this.stopRecorderButton = stopRecorderButton;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		startPauseButton.setImage(SWTBotRecorderUI.imageRegistry.get(SWTBotRecorderUI.START));
		startPauseButton.setSelection(false);
		recorderUI.stop();
		recorderUI.setButtonStates();
		stopRecorderButton.setSelection(false);
	}

	public void widgetSelected(SelectionEvent e) {
		widgetDefaultSelected(e);
	}
}
