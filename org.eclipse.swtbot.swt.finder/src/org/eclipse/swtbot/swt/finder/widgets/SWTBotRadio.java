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
import org.eclipse.swt.widgets.Widget;
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
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Button.class, style = @Style(name = "SWT.RADIO", value = SWT.RADIO), preferredName = "radio", referenceBy = {	ReferenceBy.LABEL, ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })  //$NON-NLS-1$
public class SWTBotRadio extends AbstractSWTBot<Button> {

	/**
	 * Constructs an instance of this with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotRadio(Button w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an instance of this with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotRadio(Button w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
		Assert.isTrue(SWTUtils.hasStyle(w, SWT.RADIO), "Expecting a radio."); //$NON-NLS-1$
	}

	/**
	 * Selects the radio button.
	 */
	public void click() {
		if (isSelected()) {
			log.debug(MessageFormat.format("Widget {0} is already selected, not clicking again.", this)); //$NON-NLS-1$
			return;
		}
		assertEnabled();
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		asyncExec(new VoidResult() {
			public void run() {
				deselectOtherRadioButtons();
				log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
				widget.setSelection(true);
			}

			/**
			 * @see "http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet224.java?view=co"
			 */
			private void deselectOtherRadioButtons() {
				if (hasStyle(widget.getParent(), SWT.NO_RADIO_GROUP))
					return;
				Widget[] siblings = SWTUtils.siblings(widget);
				for (Widget widget : siblings) {
					if ((widget instanceof Button) && hasStyle(widget, SWT.RADIO))
						((Button) widget).setSelection(false);
				}
			}
		});
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
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
	}

	/**
	 * Checks if the item is selected.
	 * 
	 * @return <code>true</code> if the radio button is selected. Otherwise <code>false</code>.
	 */
	public boolean isSelected() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getSelection();
			}
		});
	}
}
