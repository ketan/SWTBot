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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.test.AbstractSWTShellTest;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TableHasRowsTest extends AbstractSWTShellTest {

	private Table				table;

	@Test
	public void waitsForTableToContainRows() throws Exception {
		keepAddingRowsInTable(table, 49);
		assertFalse(Conditions.tableHasRows(new SWTBotTable(table), 50).test());
		keepAddingRowsInTable(table, 1);
		assertTrue(Conditions.tableHasRows(new SWTBotTable(table), 50).test());
	}

	@Override
	protected void createUI(Composite parent) {
		shell.setLayout(new GridLayout());
		
		table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).pack();
		}
		
		shell.layout(true);
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
