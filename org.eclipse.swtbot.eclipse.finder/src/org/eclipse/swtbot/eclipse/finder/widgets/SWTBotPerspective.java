/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Patel - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259837
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
 * @author Ralf Ebert
 */
public class SWTBotPerspective {

	private SWTWorkbenchBot bot;
	private IPerspectiveDescriptor perspectiveDescriptor;

	/**
	 * Constructs an instance of the given object.
	 *
	 * @param perspectiveDescriptor the perspective descriptor.
	 * @param bot the instance of {@link SWTWorkbenchBot} which will be used to drive operations on behalf of this object.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotPerspective(IPerspectiveDescriptor perspectiveDescriptor, SWTWorkbenchBot bot) throws WidgetNotFoundException {
		this.bot = bot;
		Assert.isNotNull(perspectiveDescriptor, "The perspective descriptor cannot be null"); //$NON-NLS-1$
		this.perspectiveDescriptor = perspectiveDescriptor;
	}

	/**
	 * @return the perspective descriptor for this perspective.
	 */
	public IPerspectiveDescriptor getPerspectiveDescriptor() {
		return perspectiveDescriptor;
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
		return bot.activePerspective().getPerspectiveDescriptor().getId().equals(this.perspectiveDescriptor.getId());
	}
	
	@Override
	public String toString() {
		return "SWTBotEclipsePerspective[id=\""+perspectiveDescriptor.getLabel()+"\", label=\""+perspectiveDescriptor.getLabel()+"\"]";
	}

}
