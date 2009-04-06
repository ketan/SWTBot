/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *     Cédric Chabanois - http://swtbot.org/bugzilla/show_bug.cgi?id=17
 *     Stefan Seelmann - http://swtbot.org/bugzilla/show_bug.cgi?id=26
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link CCombo} widget.
 * 
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 * @since 1.0
 */
@SWTBotWidget(clasz = CCombo.class, preferredName = "ccomboBox", referenceBy = { ReferenceBy.TEXT, ReferenceBy.LABEL })
public class SWTBotCCombo extends AbstractSWTBotControl<CCombo> {

	/**
	 * Constructs an instance of this with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotCCombo(CCombo w) throws WidgetNotFoundException {
		this(w, null);
	}
	
	/**
	 * Constructs an instance of this with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotCCombo(CCombo w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	/**
	 * Sets the text in the cCombo box.
	 * 
	 * @param text the text to set.
	 */
	public void setText(final String text) {
		log.debug(MessageFormat.format("Setting text on widget {0} to {1}", this, text)); //$NON-NLS-1$
		assertEnabled();
		if (hasStyle(widget, SWT.READ_ONLY))
			throw new RuntimeException("This combo box is read-only."); //$NON-NLS-1$
		asyncExec(new VoidResult() {
			public void run() {
				widget.setText(text);
			}
		});
		notify(SWT.Modify);
	}

	/**
	 * Returns the maximum number of characters that the receiver's text field is capable of holding. If this has not
	 * been changed by <code>setTextLimit()</code>, it will be the constant <code>Combo.LIMIT</code>.
	 * 
	 * @return the text limit
	 */
	public int textLimit() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getTextLimit();
			}
		});
	}

	/**
	 * Set the selection to the specified text.
	 * 
	 * @param text the text to set into the combo.
	 */
	public void setSelection(final String text) {
		log.debug(MessageFormat.format("Setting selection on {0} to {1}", widget, text)); //$NON-NLS-1$
		_setSelection(text);
		notify(SWT.Selection);
		log.debug(MessageFormat.format("Set selection on {0} to {1}", widget, text)); //$NON-NLS-1$
	}

	/**
	 * Sets the selection to the given text.
	 * 
	 * @param text The text to use.
	 */
	private void _setSelection(final String text) {
		assertEnabled();
		final int indexOf = syncExec(new IntResult() {
			public Integer run() {
				String[] items = widget.getItems();
				return Arrays.asList(items).indexOf(text);
			}
		});
		if (indexOf == -1)
			throw new RuntimeException("Item `" + text + "' not found in combo box."); //$NON-NLS-1$ //$NON-NLS-2$

		select(indexOf);
	}

	private void select(final int indexOf) {
		asyncExec(new VoidResult() {
			public void run() {
				widget.select(indexOf);
			}
		});
		notify(SWT.Selection);
	}

	/**
	 * Gets the current selection in the combo.
	 * 
	 * @return the current selection in the combo box or null if no item is selected.
	 */
	public String selection() {
		return syncExec(new StringResult() {
			public String run() {
				int selectionIndex = widget.getSelectionIndex();
				if (selectionIndex == -1)
					return null;
				return widget.getItem(selectionIndex);
			}
		});
	}

	/**
	 * Gets the current selection index.
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
			throw new RuntimeException("The index (" + index + ") is more than the number of items (" + itemCount + ") in the combo."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		select(index);
	}

	/**
	 * Gets the number of items in the combo box.
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
	 */
	public String[] items() {
		return syncExec(new ArrayResult<String>() {
			public String[] run() {
				return widget.getItems();
			}
		});
	}

}
