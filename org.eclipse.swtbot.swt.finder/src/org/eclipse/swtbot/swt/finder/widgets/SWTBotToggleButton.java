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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.Style;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * Represents a toggle button {@link Button} of type {@link SWT#TOGGLE}.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @see SWTBotButton
 * @see SWTBotRadio
 * @see SWTBotCheckBox
 */
@SWTBotWidget(clasz = Button.class, style = @Style(name = "SWT.TOGGLE", value = SWT.TOGGLE), preferredName = "toggleButton", referenceBy = { ReferenceBy.LABEL, ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })//$NON-NLS-1$
public class SWTBotToggleButton extends AbstractSWTBot<Button> {

	/**
	 * Constructs an instance of this object with the given button (Toggle)
	 *
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 1.0
	 */
	public SWTBotToggleButton(Button w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an instance of this object with the given button (Toggle)
	 *
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 1.0
	 */
	public SWTBotToggleButton(Button w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
		Assert.isTrue(SWTUtils.hasStyle(w, SWT.TOGGLE), "Expecting a toggle button."); //$NON-NLS-1$
	}

	/**
	 * Click on the toggle button, toggle it.
	 */
	@Override
	public SWTBotToggleButton click() {
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		toggle();
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
		return this;
	}

	/**
	 * Un-press the toggle button.
	 */
	public void unpress() {
		log.debug(MessageFormat.format("Deselecting {0}", this)); //$NON-NLS-1$
		waitForEnabled();
		if (!isPressed()) {
			log.debug(MessageFormat.format("Widget {0} already deselected, not deselecting again.", this)); //$NON-NLS-1$
			return;
		}
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Deselecting {0}", this)); //$NON-NLS-1$
				widget.setSelection(false);
			}
		});
		notifyListeners();
	}

	/**
	 * Select the toggle button.
	 */
	public void press() {
		log.debug(MessageFormat.format("Selecting {0}", this)); //$NON-NLS-1$
		waitForEnabled();
		if (isPressed()) {
			log.debug(MessageFormat.format("Widget {0} already selected, not selecting again.", this)); //$NON-NLS-1$
			return;
		}
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Selecting {0}", this)); //$NON-NLS-1$
				widget.setSelection(true);
			}
		});
		notifyListeners();
	}

	/**
	 * Toggle the toggle button.
	 */
	protected void toggle() {
		waitForEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Toggling state on {0}. Setting state to {1}", widget, (!widget.getSelection() ? "selected" //$NON-NLS-1$ //$NON-NLS-2$
						: "unselected"))); //$NON-NLS-1$
				widget.setSelection(!widget.getSelection());
			}
		});
		notifyListeners();
	}

	/**
	 * Notify listeners about toggle button state change.
	 */
	protected void notifyListeners() {
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.FocusIn);
		notify(SWT.MouseDown);
		notify(SWT.MouseUp);
		notify(SWT.Selection);
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
	}

	/**
	 * Gets if the toggle button is pressed.
	 *
	 * @return <code>true</code> if the toggle button is pressed. Otherwise <code>false</code>.
	 */
	public boolean isPressed() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getSelection();
			}
		});
	}

}
