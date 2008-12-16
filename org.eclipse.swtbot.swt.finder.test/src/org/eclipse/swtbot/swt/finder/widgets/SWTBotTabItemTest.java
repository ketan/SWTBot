/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;


import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotTabItemTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class SWTBotTabItemTest extends AbstractSWTTestCase {
	final SWTBot	bot	= new  SWTBot();

	public void testFindsTabs() throws Exception {
		bot.shell("SWT Controls").activate();
		final AbstractSWTBot tabItem = bot.tabItem("Sash");
		assertEquals("Sash", tabItem.getText());
		bot.tabItem("Button").activate();
	}

	public void testActivatesTabItem() throws Exception {
		bot.shell("SWT Controls").activate();
		SWTBotTabItem tabItem = bot.tabItem("Sash");
		List findControls = new ControlFinder().findControls(allOf(instanceOf(TabItem.class), withText("Sash")));
		assertSameWidget((Widget) findControls.get(0), tabItem.widget);
	}
}
