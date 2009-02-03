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
package org.eclipse.swtbot.eclipse.finder.widgets.helpers;

import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * Screen object to create a new java class.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class NewJavaClass {

	private SWTEclipseBot	bot	= new SWTEclipseBot();

	public void createClass(String packageName, String className) throws Exception {
		bot.menu("File").menu("New").menu("Class").click();
		SWTBotShell shell = bot.shell("New Java Class");
		shell.activate();
		bot.textWithLabel("Package:").setText(packageName);
		bot.textWithLabel("Name:").setText(className);
		bot.button("Finish").click();

		bot.waitUntil(Conditions.shellCloses(shell));
	}
}
