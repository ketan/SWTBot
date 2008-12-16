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


import org.eclipse.core.runtime.Assert;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;

/**
 * A condition that returns <code>false</code> until the table has the specified number of rows.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class TableHasRows extends DefaultCondition {

	/**
	 * The row count.
	 */
	private final int			rowCount;
	/**
	 * The table (SWTBotTable) instance to check.
	 */
	private final SWTBotTable	table;

	/**
	 * Constructs an instance of the condition for the given table. The row count is used to know how many rows it needs
	 * to satisfy the condition.
	 *
	 * @param table the table
	 * @param rowCount the number of rows needed.
	 * @throws NullPointerException Thrown if the table is <code>null</code>.
	 * @throws IllegalArgumentException Thrown if the row count is less then 1.
	 */
	TableHasRows(SWTBotTable table, int rowCount) {
		Assert.isNotNull(table, "The table can not be null");
		Assert.isLegal(rowCount >= 0, "The row count must be greater then zero (0)");
		this.table = table;
		this.rowCount = rowCount;
	}

	/**
	 * Performs the check to see if the condition is satisfied.
	 *
	 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
	 * @return <code>true</code> if the condition row count equals the number of rows in the table. Otherwise
	 *         <code>false</code> is returned.
	 */
	public boolean test() {
		return table.rowCount() == rowCount;
	}

	/**
	 * Gets the failure message if the test is not satisfied.
	 *
	 * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
	 * @return The failure message.
	 */
	public String getFailureMessage() {
		return "Timed out waiting for " + table + " to contain " + rowCount + " rows.";
	}
}
