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
package org.eclipse.swtbot.swt.finder.waits;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: TableHasRowsTest.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public class TableHasRowsTest extends AbstractSWTTestCase {

	private static final String	TEXT	= "this should close in a while - " + TableHasRowsTest.class;
	private Table	table;

	public void testWaitsForTableToContainRows() throws Exception {
		keepAddingRowsInTable(table, 49);
		assertFalse(Conditions.tableHasRows(new SWTBotTable(table), 50).test());
		keepAddingRowsInTable(table, 1);
		assertTrue(Conditions.tableHasRows(new SWTBotTable(table), 50).test());
	}

	protected void setUp() throws Exception {
		super.setUp();
		table = createTable(createShell(TEXT));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		new SWTBot().shell(TEXT).close();
	}

	private void keepAddingRowsInTable(final Table table, final int rows) {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				for (int i = 0; i < rows; i++) {
					new TableItem(table, SWT.NONE).setText("item " + i);
				}
			}
		});
	}

}
