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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;

/**
 * @author Jesper Steen Moeller &lt;jesper [at] selskabet [dot] org&gt;
 */
public class TreeHasRowsTest extends AbstractSWTTestCase {

	private static final String	TEXT	= "this should close in a while - " + TreeHasRowsTest.class.getSimpleName();
	private Tree				Tree;

	@Test
	public void waitsForTreeToContainRows() throws Exception {
		keepAddingRowsInTree(Tree, 49);
		assertFalse(Conditions.treeHasRows(new SWTBotTree(Tree), 50).test());
		keepAddingRowsInTree(Tree, 1);
		assertTrue(Conditions.treeHasRows(new SWTBotTree(Tree), 50).test());
	}

	public void setUp() throws Exception {
		super.setUp();
		Tree = createTree(createShell(TEXT));
	}

	public void tearDown() throws Exception {
		super.tearDown();
		new SWTBot().shell(TEXT).close();
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

}
