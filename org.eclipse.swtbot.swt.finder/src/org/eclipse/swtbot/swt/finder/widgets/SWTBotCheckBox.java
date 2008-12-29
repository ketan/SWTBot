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

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Button.class, style = @Style(name = "SWT.CHECK", value = SWT.CHECK), preferredName = "checkBox", referenceBy = { ReferenceBy.LABEL,
		ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })
public class SWTBotCheckBox extends AbstractSWTBot<Button> {

	/**
	 * Constructs an instance of this object with the given button (Checkbox)
	 *
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 1.0
	 */
	public SWTBotCheckBox(Button w) throws WidgetNotFoundException {
		super(w);
		Assert.isTrue(SWTUtils.hasStyle(w, SWT.CHECK), "Expecting a checkbox.");
	}

	/**
	 * Click on the checkbox, toggle it.
	 */
	public void click() {
		log.debug(MessageFormat.format("Clicking on {0}", this));
		toggle();
		log.debug(MessageFormat.format("Clicked on {0}", this));
	}

	/**
	 * Deselect the checkbox.
	 */
	public void deselect() {
		log.debug(MessageFormat.format("Deselecting {0}", this));
		assertEnabled();
		if (!isChecked()) {
			log.debug(MessageFormat.format("Widget {0} already deselected, not deselecting again.", this));
			return;
		}
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Deselecting {0}", this));
				widget.setSelection(false);
			}
		});
		notifyListeners();
	}

	/**
	 * Select the checkbox.
	 */
	public void select() {
		log.debug(MessageFormat.format("Selecting {0}", this));
		assertEnabled();
		if (isChecked()) {
			log.debug(MessageFormat.format("Widget {0} already selected, not selecting again.", this));
			return;
		}
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Selecting {0}", this));
				widget.setSelection(true);
			}
		});
		notifyListeners();
	}

	/**
	 * Toggle the checkbox.
	 */
	protected void toggle() {
		assertEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				log.debug(MessageFormat.format("Toggling state on {0}. Setting state to {1}", widget, (!widget.getSelection() ? "selected"
						: "unselected")));
				widget.setSelection(!widget.getSelection());
			}
		});
		notifyListeners();
	}

	/**
	 * notify listeners about checkbox state change.
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
	 * Gets if the checkbox button is checked.
	 *
	 * @return <code>true</code> if the checkbox is checked. Otherwise <code>false</code>.
	 */
	public boolean isChecked() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getSelection();
			}
		});
	}

}
