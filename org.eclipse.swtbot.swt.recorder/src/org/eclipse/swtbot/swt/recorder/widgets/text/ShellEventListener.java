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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ShellEventListener extends AbstractTextBasedRecorderListener {

	public ShellEventListener(ActionList eventList, SWTBot bot) {
		super(SWTBotShell.class, eventList, bot);
	}

	protected SWTBotEvent createEvent(Event event) {
		return new SWTBotEvent("activate"); //$NON-NLS-1$
	}

	protected boolean doCanHandleEvent(Event event) {
		return event.type == SWT.Activate;
	}
	
	protected boolean matchesWidgetStyle(Widget widget) {
		return true;
	}
	
	protected List<? extends Widget> similarWidgets(Matcher<?> matcher, Widget widget) {
		return bot.shells(SWTUtils.getText(widget));
	}

}
