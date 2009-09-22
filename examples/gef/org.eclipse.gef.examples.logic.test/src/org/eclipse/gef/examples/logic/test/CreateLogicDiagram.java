/*******************************************************************************
 * Copyright (c) 2009 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *     Steve Monnier <steve.monnier@obeo.fr> - fix and evolution
 *******************************************************************************/

package org.eclipse.gef.examples.logic.test;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

public class CreateLogicDiagram {

	private SWTWorkbenchBot	bot	= new SWTWorkbenchBot();
	
	public void createFile(final String projectName, final String fileName) throws Exception {
		bot.menu("File").menu("New").menu("Other...").click();
	    bot.waitUntil(Conditions.shellIsActive("New"));
		SWTBotShell shell = bot.shell("New");
		shell.activate();
		
		
	    SWTBotTree wizardTree = bot.tree();
	    wizardTree.expandNode("Examples").expandNode("GEF (Graphical Editing Framework)").select("Logic Diagram");
	    bot.button("Next >").click();
		
	    SWTBotTree projectSelectionTree = bot.tree();
	    projectSelectionTree.select(projectName); 
	    
	    bot.textWithLabel("File name:").setText(fileName);
	    bot.button("Finish").click();
		bot.waitUntil(Conditions.shellCloses(shell));
	}
	
}
