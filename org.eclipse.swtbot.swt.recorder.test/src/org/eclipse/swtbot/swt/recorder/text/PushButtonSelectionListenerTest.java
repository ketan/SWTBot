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
public class PushButtonSelectionListenerTest extends AbstractSWTBotRecorderTest {

	@Test
	public void buttonClickIsRecorded() throws Exception {
		bot.button("One").click();
		assertEvent("bot.button(\"One\").click();");
	}

	@Test
	public void buttonClickIsRecorded2() throws Exception {
		bot.button("Two", 1).click();
		assertEvent("bot.button(\"Two\", 1).click();");
	}

}
