/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.examples.helloworld;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/*
 * This example builds on HelloWorld2 and demonstrates how to resize the Label when the Shell resizes using a Layout.
 */
public class HelloWorld4 {
	private static ResourceBundle	resHello	= ResourceBundle.getBundle("examples_helloworld");

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new HelloWorld4().open(display);
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	public Shell open(Display display) {
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Label label = new Label(shell, SWT.CENTER);
		label.setText(HelloWorld4.resHello.getString("Hello_world"));
		shell.pack();
		shell.open();
		return shell;
	}
}
