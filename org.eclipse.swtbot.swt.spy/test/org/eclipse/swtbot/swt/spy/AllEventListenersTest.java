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

import junit.framework.TestCase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AllEventListenersTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class AllEventListenersTest extends TestCase {

	private Display			display;

	private Shell			shell1;

	private ControlExample	controlExample1;

	private Shell			shell2;

	private ControlExample	controlExample2;

	protected void setUp() throws Exception {
		display = new Display();
		createShell1();
		createShell2();
		new ControlFinder();
	}

	private void createShell1() {
		shell1 = new Shell(display, SWT.SHELL_TRIM);
		shell1.setLayout(new FillLayout());
		controlExample1 = new ControlExample(shell1);
		shell1.setText(ControlExample.getResourceString("window.title"));
		ControlExample.setShellSize(controlExample1, shell1);
		shell1.open();
	}

	private void createShell2() {
		shell2 = new Shell(display, SWT.SHELL_TRIM);
		shell2.setLayout(new FillLayout());
		controlExample2 = new ControlExample(shell2);
		shell2.setText(ControlExample.getResourceString("window.title"));
		ControlExample.setShellSize(controlExample2, shell2);
		shell2.open();
	}

	protected void tearDown() throws Exception {
		while (!(shell1.isDisposed() || shell2.isDisposed()))
			if (!display.readAndDispatch())
				display.sleep();
		controlExample1.dispose();
		controlExample2.dispose();
	}

	public void testListensToAllEvents() throws Exception {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				System.out.println(event);
			}
		};
		display.addFilter(SWT.MouseMove, listener);
	}
}
