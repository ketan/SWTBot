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
package org.eclipse.swtbot.eclipse.finder;

import org.eclipse.swtbot.swt.finder.SWTBotTestCase;

/**
 * This is a wrapper test case to the SWTBotTestCase that adds an eclipse bot
 * instead of the standard bot.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotEclipseTestCase.java 1190 2008-12-02 07:06:28Z kpadegaonkar $
 * @since 1.2
 */
public abstract class SWTBotEclipseTestCase extends SWTBotTestCase {
	/**
	 * An instance of SWTEclipseBot.
	 *
	 * @since 1.1
	 */
	protected SWTEclipseBot	bot	= new SWTEclipseBot();
}
