/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=17
 *     Stefan Seelmann - http://swtbot.org/bugzilla/show_bug.cgi?id=26
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.Arrays;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotCombo.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
@SWTBotWidget(clasz = Combo.class, preferredName = "comboBox", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT })
public class SWTBotCombo extends AbstractSWTBot<Combo> {

	/**
	 * Constructs an instance of this with the given combo box.
	 *
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 1.0
	 */
	public SWTBotCombo(Combo w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Set the selection to the specified text.
	 *
	 * @param text the text to set into the combo.
	 */
	public void setSelection(final String text) {
		log.debug(MessageFormat.format("Setting selection on {0} to {1}", this, text));
		assertEnabled();
		_setSelection(text);
		notify(SWT.Selection);
		log.debug(MessageFormat.format("Set selection on {0} to {1}", this, text));
	}

	/**
	 * Sets the selection to the given text.
	 *
	 * @param text The text to select.
	 */
	private void _setSelection(final String text) {
		final int indexOf = syncExec(new IntResult() {
			public Integer run() {
				String[] items = widget.getItems();
				return Arrays.asList(items).indexOf(text);
			}
		});
		if (indexOf == -1)
			throw new RuntimeException("Item `" + text + "' not found in combo box.");
		asyncExec(new VoidResult() {
			public void run() {
				widget.select(indexOf);
			}
		});
	}

	/**
	 * Attempts to select the current item.
	 *
	 * @return the current selection in the combo box.
	 */
	public String selection() {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getItem(widget.getSelectionIndex());
			}
		});
	}

	/**
	 * Sets the selection to the given index.
	 *
	 * @return the zero based index of the current selection.
	 */
	public int selectionIndex() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getSelectionIndex();
			}
		});
	}

	/**
	 * Sets the selection to the specified index.
	 *
	 * @param index the zero based index.
	 */
	public void setSelection(final int index) {
		assertEnabled();
		int itemCount = itemCount();
		if (index > itemCount)
			throw new RuntimeException("The index (" + index + ") is more than the number of items (" + itemCount + ") in the combo.");

		asyncExec(new VoidResult() {
			public void run() {
				widget.select(index);
			}
		});
		notify(SWT.Selection);
	}

	/**
	 * Gets the item count in the combo box.
	 *
	 * @return the number of items in the combo box.
	 */
	public int itemCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getItemCount();
			}
		});
	}

	/**
	 * Returns an array of <code>String</code>s which are the items in the receiver's list.
	 *
	 * @return the items in the receiver's list
	 * @since 1.0
	 */
	public String[] items() {
		return syncExec(new ArrayResult<String>() {
			public String[] run() {
				return widget.getItems();
			}
		});
	}

	/**
	 * Sets the text of the combo box.
	 *
	 * @param text the text to set.
	 * @since 1.0
	 */
	public void setText(final String text) {
		log.debug(MessageFormat.format("Setting text on {0} to {1}", this, text));
		assertEnabled();

		if (hasStyle(widget, SWT.READ_ONLY))
			throw new RuntimeException("This combo box is read-only.");

		asyncExec(new VoidResult() {
			public void run() {
				widget.setText(text);
			}
		});
		notify(SWT.Modify);
		log.debug(MessageFormat.format("Set text on {0} to {1}", this, text));
	}

}
