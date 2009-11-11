/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.pass;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotCTabItemTest extends AbstractSWTTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("CTabFolder").activate();
		bot.checkBox("SWT.CLOSE").deselect();
		bot.checkBox("SWT.CLOSE").select();
		bot.checkBox("Listen").select();
		bot.button("Clear").click();
	}

	@Test
	public void canCloseCTabItems() throws Exception {
		bot.cTabItem("CTabItem 0").close();

		SWTBotText listeners = bot.textInGroup("Listeners");
		assertEventMatches(listeners, "CTabFolderEvent: CTabFolderEvent{CTabFolder {} time=185015066 data=null item=CTabItem {CTabItem 0} doit=true x=0 y=0 width=0 height=0}");
		assertEventMatches(listeners, "Selection [13]: SelectionEvent{CTabFolder {} time=185324514 data=null item=CTabItem {CTabItem 1} detail=0 x=0 y=0 width=0 height=0 stateMask=0 text=null doit=true}");
		assertEventMatches(listeners, "Dispose [12]: DisposeEvent{CTabItem {CTabItem 0} time=185739697 data=null}");

		try{
			bot.cTabItem("CTabItem 0");
			fail("Did not expect to find the ctab item that was just closed");
		}catch (Exception e) {
			pass();
		}
	}

	protected Shell getFocusShell() {
		return customControlShell;
	}

}
