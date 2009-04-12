/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.widgets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This represents an eclipse workbench perspective.
 * 
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 */
public class SWTBotPerspective {

	private final SWTWorkbenchBot			bot;
	private final IPerspectiveDescriptor	perspectiveDescriptor;

	/**
	 * Constructs an instance of the given object.
	 * 
	 * @param perspectiveDescriptor the perspective descriptor.
	 * @param bot the instance of {@link SWTWorkbenchBot} which will be used to drive operations on behalf of this
	 *            object.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotPerspective(IPerspectiveDescriptor perspectiveDescriptor, SWTWorkbenchBot bot) throws WidgetNotFoundException {
		this.bot = bot;
		Assert.isNotNull(perspectiveDescriptor, "The perspective descriptor cannot be null"); //$NON-NLS-1$
		this.perspectiveDescriptor = perspectiveDescriptor;
	}

	/**
	 * Switches the active workbench page to this perspective.
	 */
	public void activate() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				try {
					IWorkbench workbench = PlatformUI.getWorkbench();
					workbench.showPerspective(perspectiveDescriptor.getId(), workbench.getActiveWorkbenchWindow());
				} catch (Exception e) {
					// TODO: what's the correct exception for such an error? Own exception class?
					throw new WidgetNotFoundException(e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * @return true if this perspective is active in the active workbench page
	 */
	public boolean isActive() {
		return bot.activePerspective().perspectiveDescriptor.getId().equals(this.perspectiveDescriptor.getId());
	}

	public String toString() {
		return "SWTBotEclipsePerspective[id=\"" + perspectiveDescriptor.getLabel() + "\", label=\"" + perspectiveDescriptor.getLabel()
				+ "\"]";
	}

}
