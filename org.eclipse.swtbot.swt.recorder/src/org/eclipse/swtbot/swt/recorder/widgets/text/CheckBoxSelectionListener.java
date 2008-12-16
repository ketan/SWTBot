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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class CheckBoxSelectionListener extends AbstractTextBasedRecorderListener {

	public CheckBoxSelectionListener(ActionList eventList, SWTBot bot) {
		super(SWTBotCheckBox.class, eventList, bot);
	}

	protected SWTBotEvent createEvent(Event event) {
		Button checkBox = (Button) event.widget;
		return new SWTBotEvent(checkBox.getSelection() ? "select" : "deselect");
	}

	protected boolean doCanHandleEvent(Event event) {
		return event.type == SWT.Selection || event.type == SWT.DefaultSelection;
	}

}
