/*******************************************************************************
 * Copyright (c) 2010 Chris Aniszczyk and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Chris Aniszczyk <caniszczyk@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.forms.finder.test;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.forms.finder.finders.SWTFormsBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;


@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractSWTBotFormsTestCase {

	public static Display display;
	public static SWTFormsBot bot;
	public static FormView view;
	public static Thread thread;
	
	protected Shell createShell(final String text) {
		return UIThreadRunnable.syncExec(new WidgetResult<Shell>() {
			public Shell run() {
				Shell shell = new Shell(display);
				shell.setText(text);
				shell.setLayout(new GridLayout());
				return shell;
			}
		});
	}

	@Before
	public void setUp() {
		display = new Display();
		bot = new SWTFormsBot();
		thread = new Thread("UI Thread") {
			public void run() {
				while ((display != null) && !display.isDisposed())
					if (!display.readAndDispatch())
						display.sleep();
			}
		};
		thread.start();
		Shell shell = createShell("Forms Test");
		view = new FormView(shell);
		shell.open();
	}

	@After
	public void tearDown() {
		// TODO
	}


}
