/*******************************************************************************
 *  Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/

package org.eclipse.swtbot.swt.finder.alltests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.addressbook.AddressBook;
import org.eclipse.swt.examples.clipboard.ClipboardExample;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.examples.controlexample.CustomControlExample;
import org.eclipse.swt.examples.dnd.DNDExample;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: Setup.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class Setup {

	private Controls	controls;

	protected void setUp() throws Exception {
		final boolean hasSetup[] = new boolean[] { false };

		if (shouldRunInSeparateThread())
			createUIThread(hasSetup);
		else
			_setup(hasSetup);
		while (!hasSetup[0])
			Thread.sleep(100);
	}

	/**
	 * @param hasSetup
	 */
	private void createUIThread(final boolean[] hasSetup) {
		if ((controls.UIThread == null) || !controls.UIThread.isAlive()) {
			controls.UIThread = new Thread("UI Thread") {
				public void run() {
					_setup(hasSetup);
					while ((controls.display != null) && !controls.display.isDisposed())
						if (!controls.display.readAndDispatch())
							controls.display.sleep();
				}
			};
			controls.UIThread.start();
		} else
			hasSetup[0] = true;
	}

	/**
	 * @param hasSetup
	 */
	private void _setup(final boolean[] hasSetup) {
		createDisplay();
		createShell();
		createExample();
		openShells();
		hasSetup[0] = true;
	}

	protected void openShells() {
		controls.controlShell.open();
		controls.customControlShell.open();
		controls.menuShell.open();
		controls.dndShell.open();
	}

	protected boolean shouldRunInSeparateThread() {
		return true;
	}

	/**
	 * Subclasses may override to create a shell.
	 */
	protected void createShell() {
		if ((controls.controlShell == null) || controls.controlShell.isDisposed()) {
			controls.controlShell = new Shell(controls.display, SWT.SHELL_TRIM);
			controls.controlShell.setLayout(new FillLayout());
			controls.controlShell.setText(ControlExample.getResourceString("window.title"));
		}

		if ((controls.menuShell == null) || controls.menuShell.isDisposed()) {
			controls.menuShell = new Shell(controls.display, SWT.SHELL_TRIM);
			controls.menuShell.setLayout(new FillLayout());
			controls.menuShell.setText("Address Book - Untitled");
		}

		if ((controls.customControlShell == null) || controls.customControlShell.isDisposed()) {
			controls.customControlShell = new Shell(controls.display, SWT.SHELL_TRIM);
			controls.customControlShell.setLayout(new FillLayout());
			controls.customControlShell.setText(ControlExample.getResourceString("custom.window.title"));
		}

		if ((controls.clipboardExampleShell == null) || controls.clipboardExampleShell.isDisposed()) {
			controls.clipboardExampleShell = new Shell(controls.display, SWT.SHELL_TRIM);
			controls.clipboardExampleShell.setLayout(new FillLayout());
			controls.clipboardExampleShell.setText(ControlExample.getResourceString("custom.window.title"));
		}

		if ((controls.dndShell == null) || controls.dndShell.isDisposed()) {
			controls.dndShell = new Shell(controls.display, SWT.SHELL_TRIM);
			controls.dndShell.setText("DND shell");
			controls.dndShell.setLayout(new FillLayout());
		}

	}

	/**
	 * Subclasses may override to create a display.
	 */
	protected void createDisplay() {
		if ((controls.display == null) || controls.display.isDisposed()) {
			controls.display = Display.getDefault();
			controls.controlShell = null;
			controls.controlExample = null;
		}
	}

	/**
	 * Creates the standard {@link ControlExample} shell, subclasses may override.
	 */
	protected void createExample() {
		if ((controls.controlExample == null) || controls.controlExample.getTabFolder().isDisposed()) {
			controls.controlExample = new ControlExample(controls.controlShell);
			ControlExample.setShellSize(controls.controlExample, controls.controlShell);
		}

		if ((controls.customControlExample == null) || controls.customControlExample.getTabFolder().isDisposed()) {
			controls.customControlExample = new CustomControlExample(controls.customControlShell);
			ControlExample.setShellSize(controls.customControlExample, controls.customControlShell);
		}

		if ((controls.menuExample == null) || controls.menuExample.table.isDisposed()) {
			controls.menuExample = new AddressBook();
			controls.menuExample.open(controls.menuShell);
		}

		if ((controls.clipboardExample == null) || controls.clipboardExample.copyRtfText.isDisposed()) {
			controls.clipboardExample = new ClipboardExample();
			controls.clipboardExample.open(controls.clipboardExampleShell);
		}

		if ((controls.dndExample == null) || controls.dndExample.defaultParent.isDisposed()) {
			controls.dndExample = new DNDExample();
			controls.dndExample.open(controls.dndShell);
		}

	}

	/**
	 * @param controls
	 * @throws Exception
	 */
	public void initialize(Controls controls) throws Exception {
		this.controls = controls;
		setUp();
	}

}
