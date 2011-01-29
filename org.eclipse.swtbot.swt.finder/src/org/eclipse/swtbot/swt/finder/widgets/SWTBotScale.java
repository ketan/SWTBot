/*******************************************************************************
 * Copyright (c) 2011 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     John Cortell - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.hamcrest.SelfDescribing;

/**
 * Represents a scale
 * 
 * @author John Cortell
 */
@SWTBotWidget(clasz = Scale.class, preferredName = "scale", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT, ReferenceBy.TOOLTIP })
public class SWTBotScale extends AbstractSWTBot<Scale> {

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param widget the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotScale(Scale widget) throws WidgetNotFoundException {
		super(widget);
	}

	/**
	 * Constructs an instance with the given widget
	 * 
	 * @param widget the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotScale(Scale widget, SelfDescribing description) throws WidgetNotFoundException {
		super(widget, description);
	}

	/**
	 * Return the current value of the scale.
	 * 
	 * @return the current selection in the scale.
	 */
	public int getValue() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getSelection();
			}
		});
	}

	/**
	 * Set the selection to the specified value.
	 * 
	 * @param value the value to set into the scale.
	 */
	public void setValue(final int value) {
		log.debug(MessageFormat.format("Setting selection on {0} to {1}", this, value)); //$NON-NLS-1$
		waitForEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setSelection(value);
			}
		});
		notify(SWT.Selection);
		log.debug(MessageFormat.format("Set selection on {0} to {1}", this, value)); //$NON-NLS-1$
	}

	/**
	 * Return the maximum value the scale will accept.
	 * 
	 * @return the maximum of the scale.
	 */
	public int getMaximum() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getMaximum();
			}
		});
	}

	/**
	 * Return the minimum value the scale will accept.
	 * 
	 * @return the minimum of the scale.
	 */
	public int getMinimum() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getMinimum();
			}
		});
	}

	/**
	 * Return the increment of the scale.
	 * 
	 * @return the increment of the scale.
	 */
	public int getIncrement() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getIncrement();
			}
		});
	}

	/**
	 * Return the page increment of the scale.
	 * 
	 * @return the increment of the scale.
	 */
	public int getPageIncrement() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getPageIncrement();
			}
		});
	}
}
