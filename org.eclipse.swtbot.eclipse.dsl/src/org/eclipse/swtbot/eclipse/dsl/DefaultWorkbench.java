/*******************************************************************************
 * Copyright (c) 2009 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.dsl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.StringConverter;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
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
public class DefaultWorkbench implements Workbench {

	protected SWTEclipseBot	bot;

	/**
	 * Creates an instance of the default workbench.
	 * 
	 * @param bot the bot that can drive the workbench.
	 */
	public DefaultWorkbench(SWTEclipseBot bot) {
		this.bot = bot;
	}

	/**
	 * @return the active workbench window's shell.
	 */
	protected Widget getActiveWorkbenchWindowShell() {
		return getActiveWorkbenchWindow().getShell();
	}

	/**
	 * @return the active workbench window.
	 */
	protected IWorkbenchWindow getActiveWorkbenchWindow() {
		return UIThreadRunnable.syncExec(bot.getDisplay(), new Result<IWorkbenchWindow>() {
			public IWorkbenchWindow run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			}
		});
	}

	public Workbench switchPerspectives(String perspectiveName) throws WidgetNotFoundException, TimeoutException {
		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
		for (IPerspectiveDescriptor perspective : perspectives) {
			if (perspectiveNameMatches(perspective, perspectiveName)) {
				IWorkbenchPage activePage = getActiveWorkbenchWindow().getActivePage();
				activePage.setPerspective(perspective);
				return this;
			}
		}

		String availablePerspectives = StringUtils.join(perspectives, ", ", new StringConverter() {
			public String toString(Object object) {
				return ((IPerspectiveDescriptor) object).getLabel();
			}
		});

		throw new IllegalArgumentException(MessageFormat.format("Cannot select perspective{0}. The valid perspective names are: {1}",//$NON-NLS-1$
				perspectiveName, availablePerspectives));
	}

	private boolean perspectiveNameMatches(final IPerspectiveDescriptor perspective, String perspectiveName) {
		String perspectiveLabel = perspective.getLabel();
		return perspectiveLabel.equals(perspectiveName) || perspective.getLabel().equals(perspectiveName + " (default)");
	}

	public Workbench resetPerspective() throws WidgetNotFoundException, TimeoutException {
		bot.menu("Window").menu("Reset Perspective...").click();
		bot.shell("Reset Perspective").activate();
		bot.button("OK").click();
		return this;
	}

	public void reset() throws WidgetNotFoundException, TimeoutException {
		final Widget viewShells = getActiveWorkbenchWindowShell();
		final List<Shell> closedShells = new ArrayList<Shell>();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					if (shell == viewShells) {
						continue;
					}
					closedShells.add(shell);
					shell.close();
				}
			}
		});
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				// wait for event pump
			}
		});
		if (!closedShells.isEmpty()) {
			new SWTBot().waitUntil(new DefaultCondition() {
				public boolean test() throws Exception {
					return !isOneOpen(closedShells);
				}

				private boolean isOneOpen(final List<Shell> closedShells) {
					return UIThreadRunnable.syncExec(new BoolResult() {
						public Boolean run() {
							for (Shell shell : closedShells) {
								if (!shell.isDisposed() && shell.isVisible()) {
									return true;
								}
							}
							return false;
						}
					});
				}

				public String getFailureMessage() {
					return "Timed out waiting for " + closedShells.size() + " shells to close.";
				}
			});
		}

		try {
			SWTBotView view = bot.view("Welcome");
			view.close();
		} catch (WidgetNotFoundException e) {
			// ignore, expected
		}
	}
}
