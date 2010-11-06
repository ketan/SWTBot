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
package org.eclipse.swtbot.swt.finder.keyboard;

import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTKeyboardTest extends AbstractKeyboardTest {

	@BeforeClass
	public static void beforeClass() {
		SWTBotPreferences.KEYBOARD_STRATEGY = "org.eclipse.swtbot.swt.finder.keyboard.SWTKeyboardStrategy";
	}

	@AfterClass
	public static void afterClass() {
		SWTBotPreferences.KEYBOARD_STRATEGY = "org.eclipse.swtbot.swt.finder.keyboard.AWTKeyboardStrategy";
	}
}
