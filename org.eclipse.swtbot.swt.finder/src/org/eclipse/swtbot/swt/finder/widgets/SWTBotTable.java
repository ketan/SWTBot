/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=16
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=100
 *     http://www.inria.fr/ - http://swtbot.org/bugzilla/show_bug.cgi?id=114
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=122
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.utils.TableRow;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Table.class, preferredName = "table", referenceBy = { ReferenceBy.LABEL })
public class SWTBotTable extends AbstractSWTBot<Table> {

	/** The last selected item */
	private TableItem	lastSelectionItem;

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param table the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTable(Table table) throws WidgetNotFoundException {
		this(table, null);
	}

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param table the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTable(Table table, SelfDescribing description) throws WidgetNotFoundException {
		super(table, description);
	}

	/**
	 * Gets the row count.
	 *
	 * @return the number of rows in the table
	 */
	public int rowCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getItemCount();
			}
		});
	}

	/**
	 * Gets the column count.
	 *
	 * @return the number of columns in the table
	 */
	public int columnCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getColumnCount();
			}
		});
	}

	/**
	 * Gets the columns in this table.
	 *
	 * @return the list of columns in the table.
	 */
	public List<String> columns() {
		return syncExec(new ListResult<String>() {
			public List<String> run() {
				ArrayList<String> result = new ArrayList<String>();

				TableColumn[] columns = widget.getColumns();

				for (int i : widget.getColumnOrder()) {
					result.add(columns[i].getText());
				}

				return result;
			}
		});
	}

	/**
	 * @param column the text on the column.
	 * @return the index of the specified column.
	 * @since 1.3
	 */
	public int indexOfColumn(String column) {
		return columns().indexOf(column);
	}

	/**
	 * Gets the column matching the given label.
	 *
	 * @param label the header text.
	 * @return the header of the table.
	 * @throws WidgetNotFoundException if the header is not found.
	 */
	public SWTBotTableColumn header(final String label) throws WidgetNotFoundException {
		TableColumn column = syncExec(new Result<TableColumn>() {
			public TableColumn run() {
				TableColumn[] columns = widget.getColumns();
				for (TableColumn column : columns) {
					if (column.getText().equals(label))
						return column;
				}
				return null;
			}
		});
		return new SWTBotTableColumn(column, widget);
	}

	/**
	 * Gets the cell data for the given row/column index.
	 *
	 * @param row the row in the table.
	 * @param column the column in the table.
	 * @return the cell at the location specified by the row and column
	 */
	public String cell(final int row, final int column) {
		assertIsLegalCell(row, column);

		return syncExec(new StringResult() {
			public String run() {
				TableItem item = widget.getItem(row);
				return item.getText(column);
			}
		});
	}

	/**
	 * Gets the cell data for the given row and column label.
	 *
	 * @param row the row in the table
	 * @param columnName the column title.
	 * @return the cell in the table at the specified row and columnheader
	 */
	public String cell(int row, String columnName) {
		Assert.isLegal(columns().contains(columnName), "The column `" + columnName + "' is not found."); //$NON-NLS-1$ //$NON-NLS-2$
		List<String> columns = columns();
		int columnIndex = columns.indexOf(columnName);
		if (columnIndex == -1)
			return ""; //$NON-NLS-1$
		return cell(row, columnIndex);
	}

	/**
	 * Gets the selected item count.
	 *
	 * @return the number of selected items.
	 */
	public int selectionCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getSelectionCount();
			}
		});
	}

	/**
	 * Gets the selected items.
	 *
	 * @return the selection in the table
	 */
	public TableCollection selection() {
		final int columnCount = columnCount();
		return syncExec(new Result<TableCollection>() {
			public TableCollection run() {
				final TableCollection selection = new TableCollection();
				TableItem[] items = widget.getSelection();
				for (TableItem item : items) {
					TableRow tableRow = new TableRow();
					for (int j = 0; j < columnCount; j++)
						tableRow.add(item.getText(j));
					selection.add(tableRow);
				}
				return selection;
			}
		});
	}

	private void assertIsLegalRowIndex(final int rowIndex) {
		Assert.isLegal(rowIndex < rowCount(), "The row number: " + rowIndex + " does not exist in the table"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Sets the selection to the given items.
	 *
	 * @param items the items to select in the table.
	 * @since 1.0
	 */
	public void select(final String... items) {
		assertEnabled();
		setFocus();
		int[] itemIndicies = new int[items.length];
		for(int i = 0; i < items.length; i++) {
			itemIndicies[i] = indexOf(items[i]);
			Assert.isLegal(itemIndicies[i] >= 0, "Could not find item:" + items[i] + " in table"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		select(itemIndicies);
		notifySelect();
	}

	/**
	 * Gets the index of the item matching the given item.
	 * 
	 * @param item the item in the table.
	 * @return the index of the specified item in the table, or -1 if the item does not exist in the table.
	 * @since 1.0
	 */
	public int indexOf(final String item) {
		return syncExec(new IntResult() {
			public Integer run() {
				TableItem[] items = widget.getItems();
				for (int i = 0; i < items.length; i++) {
					TableItem tableItem = items[i];
					if (tableItem.getText().equals(item))
						return i;
				}
				return -1;
			}
		});
	}

	/**
	 * @param item the item in the table.
	 * @return <code>true</code> if the table contains the specified item, <code>false</code> otherwise.
	 */
	public boolean containsItem(final String item) {
		return indexOf(item) != -1;
	}

	/**
	 * Gets the index of the item matching the given item and the given column.
	 * 
	 * @param item the index of the item in the table, or -1 if the item does not exist in the table.
	 * @param column the column for which to get the index of.
	 * @return the index of the specified item and of the specified column in the table.
	 * @since 1.3
	 */
	public int indexOf(final String item, final int column) {
		return syncExec(new IntResult() {
			public Integer run() {
				TableItem[] items = widget.getItems();
				for (int i = 0; i < items.length; i++) {
					TableItem tableItem = items[i];
					if (tableItem.getText(column).equals(item))
						return i;
				}
				return -1;
			}
		});
	}

	/**
	 * Gets the index of the item matching the given item and the given column.
	 *
	 * @param item the index of the item in the table, or -1 if the item does not exist in the table.
	 * @param column the column for which to get the index of.
	 * @return the index of the specified item and of the specified column in the table.
	 * @since 1.3
	 */
	public int indexOf(final String item, final String column) {
		return indexOf(item, indexOfColumn(column));
	}

	/**
	 * Unselect all selections.
	 */
	public void unselect() {
		assertEnabled();
		setFocus();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Unselecting all in {0}", widget)); //$NON-NLS-1$
				widget.deselectAll();
			}
		});
	}

	/**
	 * Selects the given index items.
	 *
	 * @param indices the row indices to select in the table.
	 */
	public void select(final int... indices) {
		assertEnabled();
		if (indices.length > 1)
			assertMultiSelect();
		setFocus();
		log.debug(MessageFormat.format("Selecting rows {0} in table {1}", StringUtils.join(indices, ", "), this)); //$NON-NLS-1$ //$NON-NLS-2$
		unselect();
		for (int i = 0; i < indices.length; i++)
			additionalSelectAndNotify(indices[i]);
	}

	/**
	 * Does not clear previous selections.
	 */
	private void additionalSelectAndNotify(final int j) {
		assertIsLegalRowIndex(j);
		asyncExec(new VoidResult() {
			public void run() {
				lastSelectionItem = widget.getItem(j);
				widget.select(j);
			}
		});
		notifySelect();
	}

	private void assertMultiSelect() {
		Assert.isLegal(hasStyle(widget, SWT.MULTI), "Table does not support multi selection."); //$NON-NLS-1$
	}

	/**
	 * Notifies the selection.
	 */
	protected void notifySelect() {
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.FocusIn);
		notify(SWT.MouseDown);
		notify(SWT.Selection, selectionEvent());
		notify(SWT.MouseUp);
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
	}

	private Event selectionEvent() {
		Event createEvent = createEvent();
		createEvent.item = lastSelectionItem;
		return createEvent;
	}

	/**
	 * Click on the table on given cell. This can be used to activate a cellEditor on a cell.
	 *
	 * @param row the row in the table.
	 * @param column the column in the table.
	 * @since 1.2
	 */
	public void click(final int row, final int column) {
		assertIsLegalCell(row, column);
		// for some reason, it does not work without setting selection first
		setFocus();
		select(row);
		asyncExec(new VoidResult() {
			public void run() {
				TableItem item = widget.getItem(row);
				Rectangle cellBounds = item.getBounds(column);
				clickXY(cellBounds.x + (cellBounds.width / 2), cellBounds.y + (cellBounds.height / 2));
			}
		});
	}

	/**
	 * Click on the table on given cell. This can be used to activate a cellEditor on a cell.
	 *
	 * @param row the row in the table.
	 * @param column the column in the table.
	 * @since 1.2
	 */
	public void doubleClick(final int row, final int column) {
		assertIsLegalCell(row, column);
		setFocus();
		asyncExec(new VoidResult() {
			public void run() {
				TableItem item = widget.getItem(row);
				Rectangle cellBounds = item.getBounds(column);
				// for some reason, it does not work without setting selection first
				widget.setSelection(row);
				doubleClickXY(cellBounds.x + (cellBounds.width / 2), cellBounds.y + (cellBounds.height / 2));
			}
		});
	}

	/**
	 * Asserts that the row and column are legal for this instance of the table.
	 *
	 * @param row the row number
	 * @param column the column number
	 * @since 1.2
	 */
	protected void assertIsLegalCell(final int row, final int column) {
		int rowCount = rowCount();
		int columnCount = columnCount(); // 0 if no TableColumn has been created by user

		Assert.isLegal(row < rowCount, "The row number (" + row + ") is more than the number of rows(" + rowCount + ") in the table."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Assert.isLegal((column < columnCount) || ((columnCount == 0) && (column == 0)), "The column number (" + column //$NON-NLS-1$
				+ ") is more than the number of column(" + columnCount + ") in the table."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the table item matching the given name.
	 *
	 * @param itemText the text on the node.
	 * @return the table item with the specified text.
	 * @throws WidgetNotFoundException if the node was not found.
	 * @since 1.3
	 */
	public SWTBotTableItem getTableItem(final String itemText) throws WidgetNotFoundException {
		try {
			new SWTBot().waitUntil(new DefaultCondition() {
				public String getFailureMessage() {
					return "Could not find node with text " + itemText; //$NON-NLS-1$
				}

				public boolean test() throws Exception {
					return getItem(itemText) != null;
				}
			});
		} catch (TimeoutException e) {
			throw new WidgetNotFoundException("Timed out waiting for table item " + itemText, e); //$NON-NLS-1$
		}
		return new SWTBotTableItem(getItem(itemText));
	}

	/**
	 * Gets the item matching the given name.
	 *
	 * @param itemText the text on the node.
	 * @return the table item with the specified text.
	 */
	private TableItem getItem(final String itemText) {
		return syncExec(new WidgetResult<TableItem>() {
			public TableItem run() {
				TableItem[] items = widget.getItems();
				for (int i = 0; i < items.length; i++) {
					TableItem item = items[i];
					if (item.getText().equals(itemText))
						return item;
				}
				return null;
			}
		});
	}

	/**
	 * Gets the table item matching the given row number.
	 *
	 * @param row the row number.
	 * @return the table item with the specified row.
	 * @throws WidgetNotFoundException if the node was not found.
	 * @since 2.0
	 */
	public SWTBotTableItem getTableItem(final int row) throws WidgetNotFoundException {
		try {
			new SWTBot().waitUntil(new DefaultCondition() {
				public String getFailureMessage() {
					return "Could not find table item for row " + row; //$NON-NLS-1$
				}

				public boolean test() throws Exception {
					return getItem(row) != null;
				}
			});
		} catch (TimeoutException e) {
			throw new WidgetNotFoundException("Timed out waiting for table item in row " + row, e); //$NON-NLS-1$
		}
		return new SWTBotTableItem(getItem(row));
	}

	/**
	 * Gets the item matching the given row number.
	 *
	 * @param row the row number.
	 * @return the table item with the specified row.
	 */
	private TableItem getItem(final int row) {
		return syncExec(new WidgetResult<TableItem>() {
			public TableItem run() {
				return widget.getItem(row);
			}

		});
	}

}
