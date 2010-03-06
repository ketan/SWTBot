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
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Combo.class, preferredName = "comboBox", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT })
public class SWTBotCombo extends AbstractSWTBotControl<Combo> {

	/**
	 * Constructs an instance of this with the given combo box.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 1.0
	 */
	public SWTBotCombo(Combo w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an instance of this with the given combo box.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 1.0
	 */
	public SWTBotCombo(Combo w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	/**
	 * Types the string in the combo box.
	 *
	 * @param text the text to be typed.
	 * @return the same instance.
	 */
	public SWTBotCombo typeText(final String text) {
		return typeText(text, SWTBotPreferences.TYPE_INTERVAL);
	}

	/**
	 * Types the string in the combo box.
	 *
	 * @param text the text to be typed.
	 * @param interval the interval between consecutive key strokes.
	 * @return the same instance.
	 */
	public SWTBotCombo typeText(final String text, int interval) {
		log.debug(MessageFormat.format("Inserting text:{0} into text {1}", text, this)); //$NON-NLS-1$
		setFocus();
		keyboard().typeText(text, interval);
		return this;
	}

	/**
	 * Set the selection to the specified text.
	 * 
	 * @param text the text to set into the combo.
	 */
	public void setSelection(final String text) {
		log.debug(MessageFormat.format("Setting selection on {0} to {1}", this, text)); //$NON-NLS-1$
		waitForEnabled();
		_setSelection(text);
		notify(SWT.Selection);
		log.debug(MessageFormat.format("Set selection on {0} to {1}", this, text)); //$NON-NLS-1$
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
			throw new RuntimeException("Item `" + text + "' not found in combo box."); //$NON-NLS-1$ //$NON-NLS-2$
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
		waitForEnabled();
		int itemCount = itemCount();
		if (index > itemCount)
			throw new RuntimeException("The index (" + index + ") is more than the number of items (" + itemCount + ") in the combo."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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
		log.debug(MessageFormat.format("Setting text on {0} to {1}", this, text)); //$NON-NLS-1$
		waitForEnabled();

		if (hasStyle(widget, SWT.READ_ONLY))
			throw new RuntimeException("This combo box is read-only."); //$NON-NLS-1$

		asyncExec(new VoidResult() {
			public void run() {
				widget.setText(text);
			}
		});
		notify(SWT.Modify);
		log.debug(MessageFormat.format("Set text on {0} to {1}", this, text)); //$NON-NLS-1$
	}

}
