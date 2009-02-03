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

import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultParentResolver;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SwtSpyTest {

	private Display			display;

	private Shell			controlExampleShell;

	@Test public void Something() throws Exception {

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

	public void setUp() throws Exception {
		display = new Display();
		createShellToSpy();
		createSwtSpy();
	}

	public void tearDown() throws Exception {
		while (!controlExampleShell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
