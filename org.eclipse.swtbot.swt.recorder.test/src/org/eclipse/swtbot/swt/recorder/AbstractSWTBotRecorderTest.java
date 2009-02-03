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
package org.eclipse.swtbot.swt.recorder;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAction;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class AbstractSWTBotRecorderTest extends AbstractSWTTestCase {
	protected SWTBotRecorder	recorder;

	/**
	 * 
	 */
	public AbstractSWTBotRecorderTest() {
		super();
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		if (bot.activeShell().widget == controlShell)
			bot.tabItem(tabItem()).activate();
		recorder = new SWTBotRecorder(display, bot);
		recorder.start();
	}

	protected String tabItem() {
		return "Button";
	}

	public void tearDown() throws Exception {
		recorder.stop();
		super.tearDown();
	}

	protected void assertEvent(final String string) throws Exception {
		bot.waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return "Could not get event: " + string + ". Got " + recorder.getEvents() + " instead.";
			}

			public boolean test() throws Exception {
				final ActionList events = recorder.getEvents();
				List actions = events.getActions();
				for (Iterator iterator = actions.iterator(); iterator.hasNext();) {
					SWTBotAction action = (SWTBotAction) iterator.next();
					if (action.toJava().equals(string))
						return true;
				}
				return false;
			}
		});
	}
}
