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
package org.eclipse.swtbot.swt.spy;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IParentResolver;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTSpy {

	private Shell	spyShell;

	Action			actionMonitor;

	long			lastWidget;

	StyledText		output;

	Runnable		trackWidgets;

	/**
	 * @param display
	 * @param childrenResolver
	 * @param parentResolver
	 */
	public SWTSpy(Display display, IChildrenResolver childrenResolver, IParentResolver parentResolver) {
		initialize(display);
		trackWidgets = new WidgetTracker(this, childrenResolver, parentResolver);
	}

	private void createActionMonitor() {
		actionMonitor = new Action("Monitor", IAction.AS_CHECK_BOX) {
			public void run() {
				if (actionMonitor.isChecked()) {
					Display display = output.getDisplay();
					display.timerExec(100, trackWidgets);
				}
			}
		};

		actionMonitor.setToolTipText("Enable/Disable monitoring of widgets");
		actionMonitor.setChecked(false);
	}

	private void createOutputText() {
		output = new StyledText(spyShell, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		output.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, true));
		output.setFont(new Font(Display.getCurrent(), "Courier New", 10, SWT.NONE));
	}

	private void createShell(Display display) {
		spyShell = new Shell(display, SWT.SHELL_TRIM);
		spyShell.setText("SWT Spy");
		spyShell.setSize(400, 300);
		spyShell.setLayout(new FillLayout());
		spyShell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				e.doit = false;
			}
		});
		spyShell.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, true));
	}

	private void hookAccelerator() {
		spyShell.getDisplay().addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == SWT.SHIFT) {
					if (actionMonitor.isChecked())
						actionMonitor.setChecked(false);
					else {
						actionMonitor.setChecked(true);
						actionMonitor.run();
					}
					e.doit = false;
				}
				;
			}
		});
	}

	private void initialize(Display display) {
		createShell(display);
		createOutputText();
		createActionMonitor();
		hookAccelerator();
		spyShell.open();
	}
}
