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
package org.eclipse.swtbot.swt.finder.waits;


import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * A condition that waits until the specified shell is disposed/visible.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
class ShellCloses extends DefaultCondition {

	private final SWTBotShell	shell;

	/**
	 * Creates a condition that evaluates to false until the shell is disposed or visible.
	 * 
	 * @param shell the shell to be monitored.
	 */
	ShellCloses(SWTBotShell shell) {
		Assert.isNotNull(shell, "The shell was null");
		this.shell = shell;
	}

	public String getFailureMessage() {
		return "The shell " + shell + " did not close.";
	}

	public boolean test() throws Exception {
		return UIThreadRunnable.syncExec(new BoolResult() {
			public Boolean run() {
				return shell.widget.isDisposed() || !shell.widget.isVisible();
			}
		});
	}

}
