/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.test;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.RunUIThreadRule;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractSWTTest {

	static {
		java.lang.System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");
	}

	protected final Logger	log	= Logger.getLogger(getClass());
	protected SWTBot		bot;
	protected ControlFinder	controlFinder;
	protected Finder		finder;
	protected MenuFinder	menuFinder;

	@Rule
	public MethodRule runner = new MethodRule(){
		public Statement apply(Statement base, FrameworkMethod method, Object target) {
			return new RunUIThreadRule(target).apply(base, method, target);
		}
	};
	
	@Before
	public final void setupSWTBot() {
		bot = new SWTBot();
		controlFinder = new ControlFinder();
		menuFinder = new MenuFinder();
		finder = new Finder(controlFinder, menuFinder);
	}

	protected static boolean isMac() {
		String swtPlatform = SWT.getPlatform();
		return ("carbon".equals(swtPlatform) || "cocoa".equals(swtPlatform));
	}
	
	protected Shell createShell(final String text) {
		return UIThreadRunnable.syncExec(new WidgetResult<Shell>() {
			public Shell run() {
				Shell shell = new Shell(Display.getCurrent());
				shell.setText(text);
				shell.setLayout(new GridLayout(1, false));
				shell.open();
				return shell;
			}
		});
	}
	protected Tree createTree(final Shell shell) {
		return UIThreadRunnable.syncExec(new WidgetResult<Tree>() {
			public Tree run() {
				Tree tree = new Tree(shell, SWT.SINGLE);
				tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				tree.setLinesVisible(true);
				tree.setHeaderVisible(true);

				shell.layout(true);
				return tree;
			}
		});
	}
}
