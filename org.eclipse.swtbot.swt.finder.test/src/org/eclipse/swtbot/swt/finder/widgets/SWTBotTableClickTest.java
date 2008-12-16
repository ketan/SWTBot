/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
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


import org.eclipse.jface.snippets.viewers.Snippet009CellEditors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;

/**
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id: SWTBotTableClickTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 * @since 1.2
 */
public class SWTBotTableClickTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotTable	table;
	private Shell		snippetCellEditorShell;

	public void testClickOnCell() throws Exception {
		table.click(0, 0);
		bot.sleep(1000);
		bot.text("0", 0).setText("101");
		bot.sleep(1000);
		table.click(1, 0);
		bot.sleep(1000);
		assertEquals("Item 101", table.cell(0, 0));
	}

	protected Shell getFocusShell() {
		return snippetCellEditorShell;
	}

	protected void setUp() throws Exception {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				snippetCellEditorShell = new Shell(display, SWT.SHELL_TRIM);
				snippetCellEditorShell.setLayout(new FillLayout());
				snippetCellEditorShell.setText("Snippet cell editor");
				new Snippet009CellEditors(snippetCellEditorShell);
				snippetCellEditorShell.open();
			}
		});
		super.setUp();
		bot = new  SWTBot();
		table = bot.table();
	}

	protected void tearDown() throws Exception {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				snippetCellEditorShell.close();
			}
		});
		super.tearDown();
	}

}