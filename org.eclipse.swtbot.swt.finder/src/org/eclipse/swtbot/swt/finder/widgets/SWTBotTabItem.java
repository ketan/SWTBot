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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Joshua Gosse &lt;jlgosse [at] ca [dot] ibm [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = TabItem.class, preferredName = "tabItem", referenceBy = { ReferenceBy.MNEMONIC })
public class SWTBotTabItem extends AbstractSWTBot<TabItem> {

	private TabFolder	parent;

	/**
	 * Constructs a new instance of this object.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTabItem(TabItem w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs a new instance of this object.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTabItem(TabItem w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
		this.parent = syncExec(new WidgetResult<TabFolder>() {
			public TabFolder run() {
				return widget.getParent();
			}
		});
	}

	/**
	 * Activates the tabItem.
	 * 
	 * @return itself.
	 * @throws TimeoutException if the tab does not activate
	 */
	public SWTBotTabItem activate() throws TimeoutException {
		log.trace(MessageFormat.format("Activating {0}", this)); //$NON-NLS-1$
		assertEnabled();
		// this runs in sync because tabFolder.setSelection() does not send a notification, and so should not block.
		asyncExec(new VoidResult() {
			public void run() {
				widget.getParent().setSelection(widget);
				log.debug(MessageFormat.format("Activated {0}", this)); //$NON-NLS-1$
			}
		});

		notify(SWT.Selection, createEvent(), parent);

		new SWTBot().waitUntil(new DefaultCondition() {
			public boolean test() throws Exception {
				return isActive();
			}

			public String getFailureMessage() {
				return "Timed out waiting for " + SWTUtils.toString(widget) + " to activate"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		});
		return this;
	}

	protected Event createEvent() {
		Event event = super.createEvent();
		event.widget = parent;
		event.item = widget;
		return event;
	}

	public boolean isActive() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return parent.getSelection()[0] == widget;
			}
		});
	}

	public boolean isEnabled() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getParent().isEnabled();
			}
		});
	}

}
