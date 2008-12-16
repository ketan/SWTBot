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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultParentResolver;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SwtSpyTest.java 1193 2008-12-02 07:22:51Z kpadegaonkar $
 */
public class SwtSpyTest extends TestCase {

	private Display			display;

	private Shell			controlExampleShell;

	public void testSomething() throws Exception {

	}

	private void createShellToSpy() {
		controlExampleShell = new Shell(display, SWT.SHELL_TRIM);
		controlExampleShell.setSize(800, 600);
		controlExampleShell.setLayout(new FillLayout());
		createPartControl(controlExampleShell);
		controlExampleShell.setText(ControlExample.getResourceString("window.title"));
		controlExampleShell.open();
	}

	protected void createPartControl(Shell parent) {
		new ControlExample(controlExampleShell);
		controlExampleShell.open();
	}

	private void createSwtSpy() {
		new SWTSpy(display, new DefaultChildrenResolver(), new DefaultParentResolver());
	}

	protected void setUp() throws Exception {
		display = new Display();
		createShellToSpy();
		createSwtSpy();
	}

	protected void tearDown() throws Exception {
		while (!controlExampleShell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
