/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.jface.snippets.viewers.Snippet052DoubleClickCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.junit.Test;

/**
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 * @since 1.2
 */
public class SWTBotTableDoubleClickTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotTable	table;
	private Shell		snippetCellEditorShell;

	@Test
	public void doubleClickOnCell() throws Exception {
		table.doubleClick(0, 0);
		bot.text("Column 1 => Item 0", 0);
	}

	protected Shell getFocusShell() {
		return snippetCellEditorShell;
	}

	public void setUp() throws Exception {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				snippetCellEditorShell = new Shell(display, SWT.SHELL_TRIM);
				snippetCellEditorShell.setLayout(new FillLayout());
				snippetCellEditorShell.setText("Snippet cell editor");
				new Snippet052DoubleClickCellEditor(snippetCellEditorShell);
				snippetCellEditorShell.open();
			}
		});
		super.setUp();
		bot = new SWTBot();
		table = bot.table();
	}

	public void tearDown() throws Exception {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				snippetCellEditorShell.close();
			}
		});
		super.tearDown();
	}

}
