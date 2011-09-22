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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
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

}
