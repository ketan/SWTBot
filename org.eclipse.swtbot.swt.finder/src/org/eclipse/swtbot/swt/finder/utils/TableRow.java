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
import java.util.List;

/**
 * Represents a table row.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TableRow {

	private final List<String>	tableColumns;

	/**
	 * Constructs an empty table row.
	 */
	public TableRow() {
		this(32);
	}

	/**
	 * Constructs a row with the specified number of columns.
	 *
	 * @param columns the number of columns in the row.
	 */
	public TableRow(int columns) {
		tableColumns = new ArrayList<String>(columns);
	}

	/**
	 * Constructs a table row with the specified columns.
	 *
	 * @param strings the items in the row.
	 */
	public TableRow(String[] strings) {
		this(strings.length);
		for (String string : strings)
			tableColumns.add(string);
	}

	/**
	 * Adds a column text label to the list.
	 *
	 * @param text the item to be added at the end of the row.
	 */
	public void add(String text) {
		tableColumns.add(text);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableColumns == null) ? 0 : tableColumns.hashCode());
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
		final TableRow other = (TableRow) obj;
		if (tableColumns.equals(other.tableColumns))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return tableColumns.toString();
	}

	/**
	 * Gets the column at the given index.
	 *
	 * @param index the index of the column in the row.
	 * @return the text at the specified column in the row.
	 */
	public String get(int index) {
		return tableColumns.get(index);
	}

	/**
	 * Gets the number of columns.
	 *
	 * @return the number of columns
	 */
	public int columnCount() {
		return tableColumns.size();
	}

}
