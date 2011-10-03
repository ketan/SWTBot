/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jesper S Møller - initial API and implementation 
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.waits;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.test.AbstractSWTShellTest;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;

/**
 * @author Jesper Steen Moeller &lt;jesper [at] selskabet [dot] org&gt;
 */
public class TreeHasRowsTest extends AbstractSWTShellTest {

	private static final String	TEXT	= "this should close in a while - " + TreeHasRowsTest.class.getSimpleName();
	private Tree				tree;

	@Test
	public void waitsForTreeToContainRows() throws Exception {
		keepAddingRowsInTree(tree, 49);
		assertFalse(Conditions.treeHasRows(new SWTBotTree(tree), 50).test());
		keepAddingRowsInTree(tree, 1);
		assertTrue(Conditions.treeHasRows(new SWTBotTree(tree), 50).test());
	}

	private void keepAddingRowsInTree(final Tree Tree, final int rows) {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				for (int i = 0; i < rows; i++) {
					new TreeItem(Tree, SWT.NONE).setText("item " + i);
				}
			}
		});
	}

	@Override
	protected void createUI(Composite parent) {
		tree = createTree(createShell(TEXT));
	}

	protected static boolean isMac() {
		return (isCarbon() || isCocoa());
	}

	private static boolean isCarbon() {
		return "carbon".equals(SWT.getPlatform());
	}

	protected static boolean isCocoa() {
		return SWT.getPlatform().equals("cocoa");
	}

	private Shell createShell(final String text) {
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

	private Tree createTree(final Shell shell) {
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
