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
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
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
 * @author Joshua Gosse &lt;jlgosse [at] ca [dot] ibm [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Tree.class, preferredName = "tree", referenceBy = { ReferenceBy.LABEL })
public class SWTBotTree extends AbstractSWTBot<Tree> {

	/**
	 * Constructs an instance of this object with the given tree.
	 *
	 * @param tree the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTree(Tree tree, SelfDescribing description) throws WidgetNotFoundException {
		super(tree, description);
	}

	/**
	 * Constructs an instance of this object with the given tree.
	 *
	 * @param tree the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTree(Tree tree) throws WidgetNotFoundException {
		this(tree, null);
	}

	/**
	 * Gets the number of rows in the tree.
	 *
	 * @return the number of rows in the tree
	 */
	public int rowCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getItems().length;
			}
		});
	}

	/**
	 * Gets the column count of this tree.
	 *
	 * @return the number of columns in the tree
	 */
	public int columnCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getColumnCount();
			}
		});
	}

	/**
	 * Gets the columns of this tree.
	 *
	 * @return the list of columns in the tree.
	 */
	public List<String> columns() {
		final int columnCount = columnCount();
		return syncExec(new ListResult<String>() {
			public List<String> run() {
				String columns[] = new String[columnCount];
				for (int i = 0; i < columnCount; i++)
					columns[i] = widget.getColumn(i).getText();
				return new ArrayList<String>(Arrays.asList(columns));
			}
		});
	}

	/**
	 * Gets the cell data for the given row/column index.
	 *
	 * @param row the row index.
	 * @param column the column index.
	 * @return the cell at the location specified by the row and column
	 */
	public String cell(final int row, final int column) {
		int rowCount = rowCount();
		int columnCount = columnCount();

		Assert.isLegal(row < rowCount, "The row number (" + row + ") is more than the number of rows(" + rowCount + ") in the tree."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Assert.isLegal(column < columnCount, "The column number (" + column + ") is more than the number of column(" + columnCount //$NON-NLS-1$ //$NON-NLS-2$
				+ ") in the tree."); //$NON-NLS-1$

		return syncExec(new StringResult() {
			public String run() {
				TreeItem item = widget.getItem(row);
				return item.getText(column);
			}
		});
	}

	/**
	 * Gets the cell data for the given row/column information.
	 *
	 * @param row the row index.
	 * @param columnName the column name.
	 * @return the cell in the tree at the specified row and column header.
	 */
	public String cell(int row, String columnName) {
		List<String> columns = columns();
		Assert.isLegal(columns.contains(columnName), "The column `" + columnName + "' is not found."); //$NON-NLS-1$ //$NON-NLS-2$
		int columnIndex = columns.indexOf(columnName);
		if (columnIndex == -1)
			return ""; //$NON-NLS-1$
		return cell(row, columnIndex);
	}

	/**
	 * Gets the current selection count.
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
	 * Gets the table collection representing the selection.
	 *
	 * @return the selection in the tree
	 */
	public TableCollection selection() {
		final int columnCount = columnCount();

		return syncExec(new Result<TableCollection>() {
			public TableCollection run() {
				final TableCollection selection = new TableCollection();
				TreeItem[] items = widget.getSelection();
				for (TreeItem item : items) {
					TableRow tableRow = new TableRow();
					if (columnCount == 0)
						tableRow.add(item.getText());
					else
						for (int j = 0; j < columnCount; j++)
							tableRow.add(item.getText(j));
					selection.add(tableRow);
				}
				return selection;
			}
		});
	}

	/**
	 * Selects the items matching the array list.
	 *
	 * @param items the items to select.
	 * @return this same instance.
	 */
	public SWTBotTree select(final String... items) {
		assertEnabled();
		setFocus();
		asyncExec(new VoidResult() {
			public void run() {
				TreeItem[] treeItems = widget.getItems();
				List<TreeItem> selection = new ArrayList<TreeItem>();
				for (TreeItem treeItem : treeItems) {
					for (String item : items) {
						if (treeItem.getText().equals(item))
							selection.add(treeItem);
					}
				}
				if (hasStyle(widget, SWT.MULTI) && items.length > 1)
					log.warn("Tree does not support SWT.MULTI, cannot make multiple selections"); //$NON-NLS-1$
				widget.setSelection(selection.toArray(new TreeItem[] {}));
			}
		});
		notifySelect();
		return this;
	}

	/**
	 * Unselects the selection in the tree.
	 *
	 * @return this same instance.
	 */
	public SWTBotTree unselect() {
		assertEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Unselecting all in {0}", widget)); //$NON-NLS-1$
				widget.deselectAll();
			}
		});
		notifySelect();
		return this;
	}

	/**
	 * Select the indexes provided.
	 *
	 * @param indices the indices to select.
	 * @return this same instance.
	 */
	public SWTBotTree select(final int... indices) {
		assertEnabled();
		setFocus();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Selecting rows [{0}] in tree{1}", StringUtils.join(indices, ", "), this)); //$NON-NLS-1$ //$NON-NLS-2$
				if (hasStyle(widget, SWT.MULTI) && indices.length > 1)
					log.warn("Tree does not support SWT.MULTI, cannot make multiple selections"); //$NON-NLS-1$
				TreeItem items[] = new TreeItem[indices.length];
				for (int i = 0; i < indices.length; i++)
					items[i] = widget.getItem(indices[i]);
				widget.setSelection(items);
			}
		});
		notifySelect();
		return this;
	}

	/**
	 * Notifies the tree widget about selection changes
	 */
	protected void notifySelect() {
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.FocusIn);
		notify(SWT.MouseDown);
		notify(SWT.Selection);
		notify(SWT.MouseUp);
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
	}

	/**
	 * Expands the node matching the node information.
	 *
	 * @param nodeText the text on the node.
	 * @return the Tree item that was expanded.
	 * @throws WidgetNotFoundException if the node is not found.
	 */
	public SWTBotTreeItem expandNode(final String nodeText) throws WidgetNotFoundException {
		assertEnabled();
		return getTreeItem(nodeText).expand();
	}

	/**
	 * Collapses the node matching the node information.
	 *
	 * @param nodeText the text on the node.
	 * @return the Tree item that was expanded.
	 * @throws WidgetNotFoundException if the node is not found.
	 */
	public SWTBotTreeItem collapseNode(final String nodeText) throws WidgetNotFoundException {
		assertEnabled();
		return getTreeItem(nodeText).collapse();
	}

	/**
	 * Gets the visible row count.
	 *
	 * @return the number of visible rows
	 */
	public int visibleRowCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				TreeItem[] items = widget.getItems();
				return getVisibleChildrenCount(items);
			}

			private int getVisibleChildrenCount(TreeItem item) {
				if (item.getExpanded())
					return getVisibleChildrenCount(item.getItems());
				return 0;
			}

			private int getVisibleChildrenCount(TreeItem[] items) {
				int count = 0;
				for (TreeItem item : items) {
					int j = getVisibleChildrenCount(item) + 1;
					count += j;
				}
				return count;
			}
		});

	}

	/**
	 * Expands the nodes as if the plus sign was clicked.
	 *
	 * @param nodeText the node to be expanded.
	 * @param recursive if the expansion should be recursive.
	 * @return the tree item that was expanded.
	 * @throws WidgetNotFoundException if the node is not found.
	 */
	public SWTBotTreeItem expandNode(final String nodeText, final boolean recursive) throws WidgetNotFoundException {
		assertEnabled();
		return syncExec(new Result<SWTBotTreeItem>() {
			public SWTBotTreeItem run() {
				SWTBotTreeItem item;
				try {
					item = getTreeItem(nodeText);
					expandNode(item);
				} catch (WidgetNotFoundException e) {
					throw new RuntimeException(e);
				}
				return item;
			}

			private void expandNode(SWTBotTreeItem item) throws WidgetNotFoundException {
				item.expand();
				if (recursive)
					expandTreeItem(item.widget);
			}

			private void expandTreeItem(TreeItem node) throws WidgetNotFoundException {
				TreeItem[] items = node.getItems();
				for (TreeItem item : items) {
					expandNode(new SWTBotTreeItem(item));
				}
			}
		});
	}


	// /**
	// * Expand the node using the keyboard
	// *
	// * @param node the node to be expanded.
	// * @param recursive if the expansion should be recursive.
	// * @return the tree item that was expanded.
	// * @throws WidgetNotFoundException if the node is not found.
	// */
	// public SWTBotTreeItem expandNode(final SWTBotTreeItem node, final boolean recursive) throws
	// WidgetNotFoundException {
	// assertEnabled();
	// return syncExec(new Result<SWTBotTreeItem>() {
	// public SWTBotTreeItem run() {
	// try {
	// expandNode(node);
	// } catch (WidgetNotFoundException e) {
	// throw new RuntimeException(e);
	// }
	// return node;
	// }
	//
	// private void expandNode(SWTBotTreeItem node) throws WidgetNotFoundException {
	// if(node.getItems().length != 0) {
	// node.expandWithKeys();
	//
	// node.keyPress(SWT.ARROW_DOWN, true);
	//
	// if (recursive) {
	// expandTreeItem(node);
	// }
	// }
	// else {
	// keyPress(SWT.ARROW_DOWN, true);
	// return;
	// }
	// }
	//
	// private void expandTreeItem(SWTBotTreeItem node) throws WidgetNotFoundException {
	// SWTBotTreeItem[] items = node.getItems();
	// for (SWTBotTreeItem item : items) {
	// expandNode(item);
	// }
	// }
	// });
	// }

	/**
	 * Gets the tree item matching the given name.
	 *
	 * @param nodeText the text on the node.
	 * @return the tree item with the specified text.
	 * @throws WidgetNotFoundException if the node was not found.
	 */
	public SWTBotTreeItem getTreeItem(final String nodeText) throws WidgetNotFoundException {
		try {
			new SWTBot().waitUntil(new DefaultCondition() {
				public String getFailureMessage() {
					return "Could not find node with text " + nodeText; //$NON-NLS-1$
				}

				public boolean test() throws Exception {
					return getItem(nodeText) != null;
				}
			});
		} catch (TimeoutException e) {
			throw new WidgetNotFoundException("Timed out waiting for tree item " + nodeText, e); //$NON-NLS-1$
		}
		return new SWTBotTreeItem(getItem(nodeText));
	}

	/**
	 * Gets the item matching the given name.
	 *
	 * @param nodeText the text on the node.
	 * @return the tree item with the specified text.
	 */
	private TreeItem getItem(final String nodeText) {
		return syncExec(new WidgetResult<TreeItem>() {
			public TreeItem run() {
				TreeItem[] items = widget.getItems();
				for (TreeItem item : items) {
					if (item.getText().equals(nodeText))
						return item;
				}
				return null;
			}
		});
	}

	/**
	 * Gets all the items in the tree.
	 *
	 * @return the list of all tree items in the tree, or an empty list if there are none.
	 * @since 1.0
	 */
	public SWTBotTreeItem[] getAllItems() {
		return syncExec(new ArrayResult<SWTBotTreeItem>() {
			public SWTBotTreeItem[] run() {
				TreeItem[] items = widget.getItems();
				SWTBotTreeItem[] result = new SWTBotTreeItem[items.length];

				for (int i = 0; i < items.length; i++)
					try {
						result[i] = new SWTBotTreeItem(items[i]);
					} catch (WidgetNotFoundException e) {
						return new SWTBotTreeItem[0];
					}
				return result;
			}
		});
	}

	/**
	 * Gets if this tree has items within it.
	 *
	 * @return <code>true</code> if the tree has any items, <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean hasItems() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getItemCount() > 0;
			}
		});
	}

	// /**
	// * Expands all of the nodes in the tree
	// */
	// public void expandNodes() {
	// SWTBotTreeItem[] children = getAllItems();
	// for (SWTBotTreeItem node : children) {
	// expandNode(node, true);
	// }
	// }
	//
	// /**
	// * Collapses all of the nodes in the tree
	// */
	// public void collapseNodes() {
	// SWTBotTreeItem[] children = getAllItems();
	// for (SWTBotTreeItem node : children) {
	// System.out.println("Pressing left key");
	// node.keyPress(SWT.ARROW_LEFT, true);
	// try {
	// Thread.sleep(500);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// node.keyPress(SWT.ARROW_DOWN, true);
	// }
	// }
}
