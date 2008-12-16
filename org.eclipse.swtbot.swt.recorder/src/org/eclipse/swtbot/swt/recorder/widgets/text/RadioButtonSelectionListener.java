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
package org.eclipse.swtbot.swt.recorder.widgets.text;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class RadioButtonSelectionListener extends AbstractTextBasedRecorderListener {

	public RadioButtonSelectionListener(ActionList eventList, SWTBot bot) {
		super(SWTBotRadio.class, eventList, bot);
	}

	protected SWTBotEvent createEvent(Event event) {
		return new SWTBotEvent("click");
	}

	protected boolean doCanHandleEvent(Event event) {
		return event.type == SWT.Selection || event.type == SWT.DefaultSelection;
	}
}
