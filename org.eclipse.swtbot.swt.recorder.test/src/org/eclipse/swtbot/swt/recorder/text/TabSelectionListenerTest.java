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
package org.eclipse.swtbot.swt.recorder.text;

import org.eclipse.swtbot.swt.recorder.AbstractSWTBotRecorderTest;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TabSelectionListenerTest extends AbstractSWTBotRecorderTest {

	@Test
	public void tabItemClickIsRecorded() throws Exception {
		bot.tabItem("CoolBar").activate();
		assertEvent("bot.tabItem(\"CoolBar\").activate();");
	}

	@Test
	public void tabItemClickIsRecorded2() throws Exception {
		bot.tabItem("Button").activate();
		assertEvent("bot.tabItem(\"Button\").activate();");
	}
}
