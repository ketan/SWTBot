/*******************************************************************************
 * Copyright (c) 2009 - 2010 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder;

import java.util.List;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * A default implementation of the workbench. May be subclassed to provide alternate implementations for different
 * versions of Eclipse.
 * <p>
 * Do not access directly. Instead use <code>Eclipse.workbench()</code>
 * </p>
 * 
 * @author David Green
 * @see Eclipse
 */
class DefaultWorkbench {

	/** The bot that may be used to drive the workbench. */
	private final SWTWorkbenchBot	bot;

	/**
	 * Creates an instance of the default workbench.
	 * 
	 * @param bot the bot that can drive the workbench.
	 */
	DefaultWorkbench(SWTWorkbenchBot bot) {
		this.bot = bot;
	}

	DefaultWorkbench resetActivePerspective() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				activePage().resetPerspective();
			}
		});
		return this;
	}

	DefaultWorkbench resetWorkbench() {
		return closeAllShells().saveAllEditors().closeAllEditors();
	}

	DefaultWorkbench closeAllShells() {
		SWTBotShell[] shells = bot.shells();
		for (SWTBotShell shell : shells) {
			if (!isEclipseShell(shell)) {
				shell.close();
			}
		}
		return this;
	}

	DefaultWorkbench saveAllEditors() {
		List<? extends SWTBotEditor> editors = bot.editors();
		for (SWTBotEditor editor : editors) {
			editor.save();
		}
		return this;
	}

	DefaultWorkbench closeAllEditors() {
		List<? extends SWTBotEditor> editors = bot.editors();
		for (SWTBotEditor editor : editors) {
			editor.close();
		}
		return this;
	}

	private boolean isEclipseShell(final SWTBotShell shell) {
		return getActiveWorkbenchWindowShell() == shell.widget;
	}

	private IWorkbenchWindow getActiveWorkbenchWindow() {
		return UIThreadRunnable.syncExec(bot.getDisplay(), new Result<IWorkbenchWindow>() {
			public IWorkbenchWindow run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			}
		});
	}
	
	private Widget getActiveWorkbenchWindowShell() {
		return getActiveWorkbenchWindow().getShell();
	}

	private IWorkbenchPage activePage() {
		return getActiveWorkbenchWindow().getActivePage();
	}

	private IPerspectiveDescriptor[] perspectives() {
		return PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
	}

}
