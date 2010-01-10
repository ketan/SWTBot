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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Joshua Gosse &lt;jlgosse [at] ca [dot] ibm [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = List.class, preferredName = "list", referenceBy = { ReferenceBy.LABEL })
public class SWTBotList extends AbstractSWTBotControl<List> {

	/**
	 * Constructs an isntance of this with the given list widget.
	 * 
	 * @param list the list.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotList(List list) throws WidgetNotFoundException {
		this(list, null);
	}

	/**
	 * Constructs an isntance of this with the given list widget.
	 * 
	 * @param list the list.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotList(List list, SelfDescribing description) throws WidgetNotFoundException {
		super(list, description);
	}

	/**
	 * Selects the item matching the given text.
	 * 
	 * @param item the item to select in the list.
	 */
	public void select(final String item) {
		log.debug(MessageFormat.format("Set selection {0} to text {1}", this, item)); //$NON-NLS-1$
		waitForEnabled();
		final int indexOf = indexOf(item);
		Assert.isTrue(indexOf != -1, "Item `" + item + "' not found in list."); //$NON-NLS-1$ //$NON-NLS-2$
		asyncExec(new VoidResult() {
			public void run() {
				widget.setSelection(indexOf);
			}
		});
		notifySelect();
	}

	/**
	 * Selects the given index.
	 * 
	 * @param index the selection index.
	 */
	public void select(final int index) {
		log.debug(MessageFormat.format("Set selection {0} to index {1}", this, index)); //$NON-NLS-1$
		waitForEnabled();
		int itemCount = itemCount();
		Assert.isTrue(index <= itemCount, java.text.MessageFormat.format(
				"The index ({0}) is more than the number of items ({1}) in the list.", index, itemCount)); //$NON-NLS-1$
		asyncExec(new VoidResult() {
			public void run() {
				widget.setSelection(index);
			}
		});
		notifySelect();
	}

	/**
	 * Gets the item count in the list
	 * 
	 * @return the number of items in the list.
	 */
	public int itemCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getItemCount();
			}
		});
	}

	/**
	 * Gets the selection count.
	 * 
	 * @return the number of selected items in the list.
	 */
	public int selectionCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getSelectionCount();
			}
		});
	}

	/**
	 * Gets the arrray of selected items.
	 * 
	 * @return the selected items in the list.
	 */
	public String[] selection() {
		return syncExec(new ArrayResult<String>() {
			public String[] run() {
				return widget.getSelection();
			}
		});
	}

	/**
	 * Selects the indexes provided.
	 * 
	 * @param indices the indices to select in the list.
	 */
	public void select(final int[] indices) {
		log.debug(MessageFormat.format("Set selection {0} to indices {1}]", this, StringUtils.join(indices, ", "))); //$NON-NLS-1$ //$NON-NLS-2$
		waitForEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setSelection(indices);
			}

		});
		notifySelect();
	}

	/**
	 * Sets the selection to the given list of items.
	 * 
	 * @param items the items to select in the list.
	 */
	public void select(final String[] items) {
		log.debug(MessageFormat.format("Set selection {0} to items [{1}]", this, StringUtils.join(items, ", "))); //$NON-NLS-1$ //$NON-NLS-2$
		waitForEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				widget.deselectAll();
				for (String item : items) {
					int index = widget.indexOf(item);
					if (index != -1)
						widget.select(index);
				}
			}
		});
		notifySelect();
	}

	/**
	 * Notifies of a selection.
	 */
	protected void notifySelect() {
		notify(SWT.MouseDown);
		notify(SWT.Selection);
		notify(SWT.MouseUp);
	}

	/**
	 * Unselects everything.
	 */
	public void unselect() {
		asyncExec(new VoidResult() {
			public void run() {
				widget.deselectAll();
			}
		});
		notifySelect();
	}

	/**
	 * Gets the index of the given item.
	 * 
	 * @param item the search item.
	 * @return the index of the item, or -1 if the item does not exist.
	 */
	public int indexOf(final String item) {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.indexOf(item);
			}
		});
	}

	/**
	 * Gets the item at the given index.
	 * 
	 * @param index the zero based index.
	 * @return the item at the specified index.
	 */
	public String itemAt(final int index) {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getItem(index);
			}
		});
	}

	/**
	 * Gets the array of Strings from the List
	 * 
	 * @return an array of Strings
	 */
	public String[] getItems() {
		return syncExec(new ArrayResult<String>() {
			public String[] run() {
				return widget.getItems();
			}
		});
	}
}
