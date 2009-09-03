/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=10
 *     Ketan Patel - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259720
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.TableRow;
import org.eclipse.swtbot.swt.finder.utils.TextDescription;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ketan Patel
 * @author Joshua Gosse &lt;jlgosse [at] ca [dot] ibm [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTreeItem extends AbstractSWTBot<TreeItem> {
//	private static final int	expandKey	= SWT.getPlatform().equals("gtk") ? SWT.KEYPAD_ADD : SWT.ARROW_RIGHT;
//	private static final int	collapseKey	= SWT.getPlatform().equals("gtk") ? SWT.KEYPAD_SUBTRACT : SWT.ARROW_LEFT;
	private Tree				tree;

	/**
	 * @param treeItem the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTreeItem(final TreeItem treeItem) throws WidgetNotFoundException {
		this(treeItem, null);
	}

	/**
	 * @param treeItem the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTreeItem(final TreeItem treeItem, SelfDescribing description) throws WidgetNotFoundException {
		super(treeItem, description);
		this.tree = syncExec(new WidgetResult<Tree>() {
			public Tree run() {
				return treeItem.getParent();
			}
		});
	}

	/**
	 * Returns the text stored at the given column index in the receiver, or empty string if the text has not been set.
	 * Throws an exception if the column is greater than the number of columns in the tree.
	 * 
	 * @param column the column index.
	 * @return the cell at the location specified by the column
	 */
	public String cell(final int column) {
		if (column == 0) {
			return getText();
		}
		int columnCount = new SWTBotTree(tree).columnCount();
		Assert.isLegal(column < columnCount, java.text.MessageFormat.format("The column index ({0}) is more than the number of column({1}) in the tree.", column, columnCount)); //$NON-NLS-1$
		return syncExec(new StringResult() {
			public String run() {
				return widget.getText(column);
			}
		});
	}

	/**
	 * Returns the table row representation of cell values
	 * 
	 * @return the cell values for this item
	 */
	public TableRow row() {
		return syncExec(new Result<TableRow>() {
			public TableRow run() {
				int columnCount = tree.getColumnCount();
				TableRow tableRow = new TableRow();
				if (columnCount == 0)
					tableRow.add(widget.getText());
				else
					for (int j = 0; j < columnCount; j++)
						tableRow.add(widget.getText(j));
				return tableRow;
			}
		});
	}

	/**
	 * Returns the number of items contained in the receiver that are direct item children of the receiver.
	 * 
	 * @return the number of items
	 */
	public int rowCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getItemCount();
			}
		});
	}

	/**
	 * Gets the nodes at the given, zero-relative index in the receiver. Throws an exception if the index is out of
	 * range.
	 * 
	 * @param row the index of the item to return
	 * @return the item at the given index
	 */
	public SWTBotTreeItem getNode(final int row) {
		int rowCount = rowCount();
		Assert.isLegal(row < rowCount, java.text.MessageFormat.format("The row number ({0}) is more than the number of rows({1}) in the tree.", row, rowCount)); //$NON-NLS-1$
		return syncExec(new Result<SWTBotTreeItem>() {
			public SWTBotTreeItem run() {
				return new SWTBotTreeItem(widget.getItem(row));
			}
		});
	}

	/**
	 * Gets the cell data for the given row/column index.
	 * 
	 * @param row the row index.
	 * @param column the column index.
	 * @return the cell at the location specified by the row and column
	 * @see #getNode(int)
	 * @see #cell(int)
	 */
	public String cell(final int row, final int column) {
		return getNode(row).cell(column);
	}

	/**
	 * Expands the tree item to simulate click the plus sign.
	 * 
	 * @return the tree item, after expanding it.
	 */
	public SWTBotTreeItem expand() {
		assertEnabled();
		preExpandNotify();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setExpanded(true);
			}
		});
		postExpandNotify();
		return this;
	}

	/**
	 * Collapses the tree item to simulate click the plus sign.
	 *
	 * @return the tree item, after collapsing it.
	 */
	public SWTBotTreeItem collapse() {
		assertEnabled();
		preCollapseNotify();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setExpanded(false);
			}
		});
		postCollapseNotify();
		return this;
	}

	private void preExpandNotify() {
		notifyTree(SWT.Expand, createEvent());
	}

	private void postExpandNotify() {
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.Activate);
		notifyTree(SWT.FocusIn);
		notifyTree(SWT.MouseDown);
		notifyTree(SWT.MeasureItem);
		notifyTree(SWT.Deactivate);
		notifyTree(SWT.FocusOut);
	}

	private void preCollapseNotify() {
		notifyTree(SWT.Collapse, createEvent());
	}

	private void postCollapseNotify() {
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.Activate);
		notifyTree(SWT.FocusIn);
		notifyTree(SWT.MouseDown);
		notifyTree(SWT.MeasureItem);
		notifyTree(SWT.Deactivate);
		notifyTree(SWT.FocusOut);
	}

	private void notifyTree(int eventType) {
		notify(eventType, createEvent(), tree);
	}

	private void notifyTree(int eventType, Event event) {
		notify(eventType, event, tree);
	}

	protected Event createEvent() {
		Event event = super.createEvent();
		event.widget = tree;
		event.item = widget;
		return event;
	}

	/**
	 * Gets the nodes of the tree item.
	 * 
	 * @return the list of nodes in the treeitem.
	 */
	public List<String> getNodes() {
		return syncExec(new ListResult<String>() {
			public List<String> run() {
				TreeItem[] items = widget.getItems();
				List<String> result = new ArrayList<String>(items.length);
				for (TreeItem item : items)
					result.add(item.getText());
				return result;
			}
		});

	}

	/**
	 * Expands the node matching the given node text.
	 * 
	 * @param nodeText the text on the node.
	 * @return the node that was expanded or <code>null</code> if not match exists.
	 */
	public SWTBotTreeItem expandNode(final String nodeText) {
		assertEnabled();
		return getNode(nodeText).expand();
	}

	/**
	 * Collapses the node matching the given node text.
	 *
	 * @param nodeText the text on the node.
	 * @return the node that was collapsed or <code>null</code> if not match exists.
	 */
	public SWTBotTreeItem collapseNode(final String nodeText) {
		assertEnabled();
		return getNode(nodeText).collapse();
	}

	// /**
	// * Expand the tree item using the right arrow key.
	// *
	// * @return the tree item, after expanding it.
	// */
	// SWTBotTreeItem expandWithKeys() {
	// SWTBotTree parent = getParent();
	// parent.keyPress(expandKey, true);
	// return this;
	// }

	// /**
	// * Collapse the tree item using the left arrow key.
	// *
	// * @return the tree item, after collapsing it.
	// */
	// SWTBotTreeItem collapseWithKeys() {
	// SWTBotTree parent = getParent();
	// parent.keyPress(collapseKey, true);
	// return this;
	// }

	/**
	 * Gets the node matching the given node text and index.
	 * 
	 * @param nodeText the text on the node.
	 * @param index the n'th node with the nodeText.
	 * @return the node with the specified text or <code>WidgetNotFoundException</code> if not match exists.
	 * @since 2.0
	 */
	public SWTBotTreeItem getNode(final String nodeText, final int index) {
		List<SWTBotTreeItem> nodes = getNodes(nodeText);
		Assert.isTrue(index < nodes.size(), MessageFormat.format("The index ({0}) was more than the number of nodes ({1}) in the tree.", index, nodes.size()));
		return nodes.get(index);
	}

	/**
	 * Gets all nodes matching the given node text.
	 * 
	 * @param nodeText the text on the node.
	 * @return the nodes with the specified text or <code>WidgetNotFoundException</code> if not match exists.
	 * @since 2.0
	 */
	public List<SWTBotTreeItem> getNodes(final String nodeText) {
		List<SWTBotTreeItem> foundItems = syncExec(new ListResult<SWTBotTreeItem>() {
			public List<SWTBotTreeItem> run() {
				TreeItem[] items = widget.getItems();
				List<SWTBotTreeItem> results = new ArrayList<SWTBotTreeItem>();
				for (TreeItem treeItem : items) {
					if (treeItem.getText().equals(nodeText))
						results.add(new SWTBotTreeItem(treeItem, new TextDescription("Tree node with text: " + nodeText)));
				}
				return results;
			}
		});
		if (foundItems.isEmpty())
			throw new WidgetNotFoundException("Could not find node with text: " + nodeText); //$NON-NLS-1$
		return foundItems;
	}

	/**
	 * Gets the first node found matching the given node text.
	 * 
	 * @param nodeText the text on the node.
	 * @return the first node with the specified text or <code>WidgetNotFoundException</code> if not match exists.
	 * @since 1.2
	 */
	public SWTBotTreeItem getNode(final String nodeText) {
		return getNode(nodeText, 0);
	}

	/**
	 * Selects the current tree item.
	 * 
	 * @return the current node.
	 * @since 1.0
	 */
	public SWTBotTreeItem select() {
		assertEnabled();
		syncExec(new VoidResult() {
			public void run() {
				tree.setFocus();
				tree.setSelection(widget);
			}
		});
		notifySelect();
		return this;
	}

	/**
	 * Click on the table at given coordinates
	 * 
	 * @param x the x co-ordinate of the click
	 * @param y the y co-ordinate of the click
	 * @since 1.2
	 */
	@Override
	protected void clickXY(int x, int y) {
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		notifyTree(SWT.MouseEnter);
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.Activate);
		notifyTree(SWT.FocusIn);
		notifyTree(SWT.MouseDown, createMouseEvent(x, y, 1, SWT.BUTTON1, 1));
		notifyTree(SWT.MouseUp);
		notifyTree(SWT.Selection, createEvent());
		notifyTree(SWT.MouseHover);
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.MouseExit);
		notifyTree(SWT.Deactivate);
		notifyTree(SWT.FocusOut);
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
	}

	/**
	 * Clicks on this node.
	 * 
	 * @return the current node.
	 * @since 1.2
	 */
	public SWTBotTreeItem click() {
		assertEnabled();
		Rectangle cellBounds = syncExec(new Result<Rectangle>() {
			public Rectangle run() {
				return widget.getBounds();
			}
		});
		clickXY(cellBounds.x + (cellBounds.width / 2), cellBounds.y + (cellBounds.height / 2));
		return this;
	}

	/**
	 * Double clicks on this node.
	 * 
	 * @return the current node.
	 * @since 1.2
	 */
	public SWTBotTreeItem doubleClick() {
		assertEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				tree.setSelection(widget);
			}
		});
		notifyTree(SWT.Selection);
		notifyTree(SWT.MouseDown);
		notifyTree(SWT.MouseUp);
		notifyTree(SWT.MouseDown);
		notifyTree(SWT.MouseDoubleClick);
		notifyTree(SWT.DefaultSelection);
		notifyTree(SWT.MouseUp);
		return this;
	}

	/**
	 * Selects the items matching the array provided.
	 * 
	 * @param items the items to select.
	 * @return the current node.
	 * @since 1.0
	 */
	public SWTBotTreeItem select(final String... items) {
		assertEnabled();
		final List<String> nodes = Arrays.asList(items);
		Assert.isTrue(getNodes().containsAll(nodes));

		syncExec(new VoidResult() {
			public void run() {
				TreeItem[] treeItems = widget.getItems();
				ArrayList<TreeItem> selection = new ArrayList<TreeItem>();

				for (TreeItem treeItem : treeItems) {
					if (nodes.contains(treeItem.getText()))
						selection.add(treeItem);
				}
				tree.setFocus();
				tree.setSelection(selection.toArray(new TreeItem[] {}));
			}
		});

		notifySelect();
		return this;
	}

	/**
	 * Selects the item matching the given name.
	 * 
	 * @param item the items to select.
	 * @return the current node.
	 * @since 1.0
	 */
	public SWTBotTreeItem select(final String item) {
		return select(new String[] { item });
	}

	/**
	 * notifies the tree widget about selection changes.
	 * 
	 * @since 1.0
	 */
	private void notifySelect() {
		notifyTree(SWT.MouseEnter);
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.Activate);
		notifyTree(SWT.FocusIn);
		notifyTree(SWT.MouseDown);
		notifyTree(SWT.Selection);
		notifyTree(SWT.MouseUp);
		notifyTree(SWT.MouseHover);
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.MouseExit);
		notifyTree(SWT.Deactivate);
		notifyTree(SWT.FocusOut);
	}

	@Override
	public String getText() {
		return SWTUtils.getText(widget);
	}

	@Override
	public SWTBotMenu contextMenu(String text) {
		new SWTBotTree(tree).assertEnabled();
		select();
		notifyTree(SWT.MenuDetect);
		return super.contextMenu(tree, text);
	}

	/**
	 * Toggle the tree item.
	 * 
	 * @since 1.3
	 */
	public void toggleCheck() {
		setChecked(!isChecked());
	}

	/**
	 * Check the tree item.
	 * 
	 * @since 1.3
	 */
	public void check() {
		setChecked(true);
	}

	/**
	 * Uncheck the tree item.
	 * 
	 * @since 1.3
	 */
	public void uncheck() {
		setChecked(false);
	}

	/**
	 * Gets if the checkbox button is checked.
	 * 
	 * @return <code>true</code> if the checkbox is checked. Otherwise <code>false</code>.
	 * @since 1.3
	 */
	public boolean isChecked() {
		assertIsCheck();
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getChecked();
			}
		});
	}

	/**
	 * Creates an event for CheckboxTreeItem case.
	 * 
	 * @return an event that encapsulates {@link #widget} and {@link #display}.
	 */
	private Event createCheckEvent() {
		Event event = createEvent();
		event.time = (int) System.currentTimeMillis();
		event.item = widget;
		event.widget = tree;
		event.detail = SWT.CHECK;
		return event;
	}

	private void setChecked(final boolean checked) {
		assertEnabled();
		assertIsCheck();
		syncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Setting state to {0} on: {1}", (checked ? "checked" : "unchecked"), widget.getText())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				widget.setChecked(checked);
			}
		});
		notifyCheck();
	}

	private void assertIsCheck() {
		Assert.isLegal(hasStyle(tree, SWT.CHECK), "The tree does not have the style SWT.CHECK"); //$NON-NLS-1$
	}

	/**
	 * notify listeners about checkbox state change.
	 * 
	 * @since 1.3
	 */
	private void notifyCheck() {
		syncExec(new VoidResult() {
			public void run() {
				tree.notifyListeners(SWT.Selection, createCheckEvent());
			}
		});
	}

	protected void assertEnabled() {
		new SWTBotTree(tree).assertEnabled();
	}

	/**
	 * @return <code>true</code> if the item is selected, false otherwise.
	 * @since 2.0
	 */
	public boolean isSelected() {
		return UIThreadRunnable.syncExec(new BoolResult() {
			public Boolean run() {
				return Arrays.asList(tree.getSelection()).contains(widget);
			}
		});
	}

	// protected Rectangle absoluteLocation() {
	// return syncExec(new Result<Rectangle>() {
	// public Rectangle run() {
	// return display.map(widget.getParent(), null, getBounds());
	// }
	// });
	// }
	//
	// /**
	// * Click on the center of the widget.
	// *
	// * @param post Whether or not {@link Display#post} should be used
	// */
	// private SWTBotTreeItem click(final boolean post) {
	// if (post) {
	// Rectangle location = absoluteLocation();
	// click(location.x, location.y, true);
	// } else
	// click();
	// return this;
	// }
	//
	// /**
	// * Right click on the center of the widget.
	// *
	// * @param post Whether or not {@link Display#post} should be used
	// */
	// private SWTBotTreeItem rightClick(final boolean post) {
	// if (post) {
	// Rectangle location = absoluteLocation();
	// rightClick(location.x, location.y, true);
	// } else
	// rightClick();
	// return this;
	// }
	//
	// /**
	// * Moves the cursor to the center of the widget
	// */
	// private void moveMouseToWidget() {
	// syncExec(new VoidResult() {
	// public void run() {
	// Rectangle bounds = getBounds();
	// Point point = new Point(bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2));
	// Point pt = display.map(widget.getParent(), null, point.x, point.y);
	// moveMouse(pt.x, pt.y);
	// }
	// });
	// }

	/**
	 * Gets all the items in this tree node.
	 * 
	 * @return all the items in this tree node.
	 */
	public SWTBotTreeItem[] getItems() {
		return syncExec(new ArrayResult<SWTBotTreeItem>() {
			public SWTBotTreeItem[] run() {
				TreeItem[] items = widget.getItems();
				SWTBotTreeItem[] children = new SWTBotTreeItem[items.length];
				for (int i = 0; i < items.length; i++) {
					children[i] = new SWTBotTreeItem(items[i]);
				}
				return children;
			}
		});
	}
}
