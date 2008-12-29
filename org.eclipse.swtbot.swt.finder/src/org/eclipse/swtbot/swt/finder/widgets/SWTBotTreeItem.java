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
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTreeItem extends AbstractSWTBot<TreeItem> {

	private Tree	tree;

	/**
	 * @param treeItem the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTreeItem(final TreeItem treeItem) throws WidgetNotFoundException {
		super(treeItem);
		this.tree = syncExec(new WidgetResult<Tree>() {
			public Tree run() {
				return treeItem.getParent();
			}
		});
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
	 * Send the {@link SWT#Expand} event to build the child items of the tree item that we are expanding.
	 *
	 * @since 1.2
	 */
	private void preExpandNotify() {
		notifyTree(SWT.Expand, selectionEvent());
	}

	/**
	 * Notifies the item of expansion.
	 *
	 * @since 1.2
	 */
	private void postExpandNotify() {
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
				ArrayList<String> result = new ArrayList<String>(items.length);
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
	 * Gets the node matching the given node text.
	 *
	 * @param nodeText the text on the node.
	 * @return the node with the specified text <code>null</code> if not match exists.
	 * @since 1.2
	 */
	public SWTBotTreeItem getNode(final String nodeText) {
		SWTBotTreeItem item = syncExec(new Result<SWTBotTreeItem>() {
			public SWTBotTreeItem run() {
				TreeItem[] items = widget.getItems();
				for (TreeItem treeItem : items) {
					if (treeItem.getText().equals(nodeText))
						return new SWTBotTreeItem(treeItem);
				}
				return null;
			}
		});
		if (item == null)
			throw new WidgetNotFoundException("Could not find node with text: " + nodeText);
		return item;
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
		log.debug(MessageFormat.format("Clicking on {0}", this));
		notifyTree(SWT.MouseEnter);
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.Activate);
		notifyTree(SWT.FocusIn);
		notifyTree(SWT.MouseDown, createMouseEvent(x, y, 1, SWT.BUTTON1, 1));
		notifyTree(SWT.MouseUp);
		notifyTree(SWT.Selection, selectionEvent());
		notifyTree(SWT.MouseHover);
		notifyTree(SWT.MouseMove);
		notifyTree(SWT.MouseExit);
		notifyTree(SWT.Deactivate);
		notifyTree(SWT.FocusOut);
		log.debug(MessageFormat.format("Clicked on {0}", this));
	}

	/**
	 * Clicks on this node.
	 *
	 * @return the current node.
	 * @since 1.2
	 */
	public SWTBotTreeItem click() {
		assertEnabled();
		Rectangle cellBounds = (Rectangle) syncExec(new Result() {
			public Object run() {
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
		click();
		notifyTree(SWT.MouseDown);
		notifyTree(SWT.MouseDoubleClick);
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
				log.debug(MessageFormat.format("Setting state to {0} on: {1}", (checked ? "checked" : "unchecked"), widget.getText()));
				widget.setChecked(checked);
			}
		});
		notifyCheck();
	}

	private void assertIsCheck() {
		Assert.isLegal(hasStyle(tree, SWT.CHECK), "The tree does not have the style SWT.CHECK");
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

	/**
	 * Gets the selection event.
	 *
	 * @return The event.
	 */
	private Event selectionEvent() {
		Event event = createEvent();
		event.item = widget;
		event.widget = tree;
		return event;
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
}
