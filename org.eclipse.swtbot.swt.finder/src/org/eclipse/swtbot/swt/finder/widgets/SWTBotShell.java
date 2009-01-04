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
package org.eclipse.swtbot.swt.finder.widgets;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Shell.class, preferredName = "shell")
public class SWTBotShell extends AbstractSWTBot<Shell> {

	/**
	 * Constructs an instance of this with the given shell.
	 * 
	 * @param shell the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotShell(Shell shell) throws WidgetNotFoundException {
		super(shell);
	}

	// @Override
	// protected Widget findWidget(int index) throws WidgetNotFoundException {
	// // could have used a matcher, but that would just slow down things
	// Shell[] shells = finder.getShells();
	// for (int i = 0; i < shells.length; i++) {
	// Shell shell = shells[i];
	// if (new SWTBotShell(shell).getText().equals(text))
	// return shell;
	// }
	// throw new WidgetNotFoundException("Cound not find shell matching text:" + text);
	// }

	/**
	 * Activates the shell.
	 * 
	 * @throws TimeoutException if the shell could not be activated
	 */
	public void activate() throws TimeoutException {
		new SWTBot().waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return "Timed out waiting for " + SWTUtils.toString(widget) + " to get activated";
			}

			public boolean test() throws Exception {
				syncExec(new VoidResult() {
					public void run() {
						widget.forceActive();
						widget.forceFocus();
					}
				});
				return isActive();
			}
		});
		notify(SWT.Activate);
	}

	/**
	 * Closes the shell
	 * 
	 * @throws TimeoutException if the shell does not close.
	 */
	public void close() throws TimeoutException {
		notify(SWT.Close);
		asyncExec(new VoidResult() {
			public void run() {
				// TODO investigate bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=259895
				if (!widget.isDisposed())
					widget.close();
			}
		});
		new SWTBot().waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return !isOpen();
			}

			public String getFailureMessage() {
				return "Timed out waiting for " + SWTUtils.toString(widget) + " to close.";
			}
		});
	}

	/**
	 * Checks if the shell is open.
	 * 
	 * @return <code>true</code> if the shell is visible, <code>false</code> otherwise.
	 */
	public boolean isOpen() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return !widget.isDisposed() && widget.isVisible();
			}
		});
	}

	/**
	 * Checks if the shell is active.
	 * 
	 * @return <code>true</code> if the shell is active, <code>false</code> otherwise.
	 */
	public boolean isActive() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return display.getActiveShell() == widget;
			}
		});
	}

}
