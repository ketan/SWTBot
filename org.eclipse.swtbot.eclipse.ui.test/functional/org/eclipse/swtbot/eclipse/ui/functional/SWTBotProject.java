/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.ui.functional;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
public class SWTBotProject {

	private final SWTWorkbenchBot	bot	= new SWTWorkbenchBot();

	public void create(String projectId) {
		bot.menu("File").menu("New").menu("Project...").click();
		SWTBotShell shell = bot.shell("New Project");
		shell.activate();

		bot.tree().expandNode("Other", "New SWTBot Test Plug-in").select();
		bot.button("Next >").click();

		bot.textWithLabel("Plug-in Name:").setText(projectId);
		bot.textWithLabel("Plug-in id:").setText(projectId);
		bot.textWithLabel("Provider:").setText("ACME Corp.");
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
	}

}
