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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

/**
 * Screen object that represents the operations that can be performed on the package explorer view.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PackageExplorerView {

	private SWTEclipseBot	bot	= new SWTEclipseBot();

	public void deleteProject(String projectName) throws Exception {
		SWTBotTree tree = tree();
		tree.setFocus();
		tree.select(projectName);
		bot.menu("Edit").menu("Delete").click();
		String version = (String) Platform.getBundle("org.eclipse.core.runtime").getHeaders().get("Bundle-Version");
		if (version.startsWith("3.3")) {
			SWTBotShell shell = bot.shell("Confirm Project Delete");
			shell.activate();
			Button button = (Button) bot.widget(widgetOfType(Button.class), shell.widget);
			new SWTBotRadio(button).click();
			bot.button("Yes").click();
			bot.waitUntil(Conditions.shellCloses(shell));
		}
		if (version.startsWith("3.4")) {
			SWTBotShell shell = bot.shell("Delete Resources");
			shell.activate();
			Button button = (Button) bot.widget(widgetOfType(Button.class), shell.widget);
			new SWTBotCheckBox(button).select();
			bot.button("OK").click();
			bot.waitUntil(Conditions.shellCloses(shell));
		}
	}

	/**
	 * @return
	 * @throws WidgetNotFoundException
	 */
	private SWTBotTree tree() throws WidgetNotFoundException {
		SWTBotView view = view();
		SWTBotTree tree = new SWTBotTree((Tree) bot.widget(widgetOfType(Tree.class), view.getWidget()));
		return tree;
	}

	/**
	 * @return
	 * @throws WidgetNotFoundException
	 */
	private SWTBotView view() throws WidgetNotFoundException {
		return bot.view("Package Explorer");
	}

}
