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
package org.eclipse.swtbot.swt.finder.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a table.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: TableCollection.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class TableCollection {

	private final List<TableRow>	rows;

	/**
	 * @param tableRow the rows in the table.
	 */
	public TableCollection(TableRow tableRow) {
		this(new TableRow[] { tableRow });
	}

	/**
	 * Cosntructs the table collection with the given table rows as an array.
	 *
	 * @param tableRows the rows in the table.
	 */
	public TableCollection(TableRow[] tableRows) {
		this(tableRows.length);
		for (TableRow tableRow : tableRows)
			add(tableRow);
	}

	/**
	 * Creates an empty table.
	 */
	public TableCollection() {
		this(32);
	}

	/**
	 * Creates a table with the specified number of rows.
	 *
	 * @param rowCount the number of rows.
	 */
	public TableCollection(int rowCount) {
		rows = new ArrayList<TableRow>(rowCount);
	}

	/**
	 * Adds a new row to the table collection.
	 *
	 * @param tableRow the row to be added at the end of the table.
	 * @return a reference to this object.
	 */
	public TableCollection add(TableRow tableRow) {
		rows.add(tableRow);
		return this;
	}

	/**
	 * Gets the row count.
	 *
	 * @return the number of rows in the selection.
	 */
	public int rowCount() {
		return rows.size();
	}

	/**
	 * Gets the string data for the given row/column index.
	 *
	 * @param row the index of the row.
	 * @param column the column in the row.
	 * @return the string at the specified cell in the collection.
	 */
	public String get(int row, int column) {
		return get(row).get(column);
	}

	/**
	 * Gets the row at the given index.
	 *
	 * @param row the row index.
	 * @return the row at the index.
	 */
	public TableRow get(int row) {
		return rows.get(row);
	}

	/**
	 * Gets the column count.
	 *
	 * @return the number of columns
	 */
	public int columnCount() {
		if (rowCount() > 0)
			return rows.get(0).columnCount();
		return 0;
	}

	@Override
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		for (final Iterator<TableRow> iterator = rows.iterator(); iterator.hasNext();) {
			final TableRow row = iterator.next();
			buf.append(row);
			buf.append("\n");
		}
		return buf.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TableCollection other = (TableCollection) obj;
		if (rows.equals(other.rows))
			return true;
		return false;
	}
}
