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

import static org.hamcrest.Matchers.allOf;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTabItem;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TabSelectionListener extends AbstractTextBasedRecorderListener {

	public TabSelectionListener(ActionList eventList, SWTBot bot) {
		super(SWTBotTabItem.class, eventList, bot);
	}

	protected SWTBotEvent createEvent(Event event) {
		return new SWTBotEvent("activate");
	}

	protected boolean doCanHandleEvent(Event event) {
		return true;
	}

	protected Widget getWidget(Event event) {
		return event.item;
	}

	protected Shell getShell(Widget widget) {
		if (widget instanceof TabItem) {
			return ((TabItem) widget).getParent().getShell();
		}
		return super.getShell(widget);
	}

	protected boolean matchesWidgetStyle(Widget widget) {
		return true;
	}

	protected Matcher<Widget> createMatcher(String text) {
		return allOf(typeMatcher(), mnemonicTextMatcher(text));
	}
}
