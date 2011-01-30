/*******************************************************************************
 * Copyright (c) 2011 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Toby Weston - initial API and implementation (Bug 259799)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.hamcrest.SelfDescribing;

/**
 * Represents an {@link ExpandItem}.
 * 
 * @author Toby Weston (Bug 259799)
 * @version $Id$
 */
public class SWTBotExpandItem extends AbstractSWTBot<ExpandItem> {
	private ExpandBar	expandBar;

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotExpandItem(ExpandItem w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotExpandItem(final ExpandItem w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
		this.expandBar = syncExec(new WidgetResult<ExpandBar>() {
			public ExpandBar run() {
				return w.getParent();
			}
		});
	}

	/**
	 * Expand this item and return itself.
	 * 
	 * @return itself.
	 */
	public SWTBotExpandItem expand() {
		asyncExec(new VoidResult() {
			public void run() {
				if (isExpanded())
					return;
				widget.setExpanded(true);
				expandNotify();
			}
		});
		return this;
	}

	/**
	 * Collapse this item and return itself.
	 * 
	 * @return itself.
	 */
	public SWTBotExpandItem collapse() {
		asyncExec(new VoidResult() {
			public void run() {
				if (isCollapsed())
					return;
				widget.setExpanded(false);
				collapseNotify();
			}
		});
		return this;
	}

	/**
	 * @return <code>true</code> if the item is expanded, <code>false</code> otherwise.
	 * @see #isCollapsed()
	 */
	public boolean isExpanded() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getExpanded();
			}
		});
	}

	/**
	 * @return <code>true</code> if the item is collapsed, <code>false</code> otherwise.
	 * @see SWTBotExpandItem#isExpanded()
	 */
	public boolean isCollapsed() {
		return !isExpanded();
	}

	private void expandNotify() {
		notifyExpandBar(SWT.MouseMove);
		notifyExpandBar(SWT.Activate);
		notifyExpandBar(SWT.FocusIn);
		notifyExpandBar(SWT.MouseDown);
		notifyExpandBar(SWT.Expand);
		notifyExpandBar(SWT.MeasureItem);
		notifyExpandBar(SWT.Deactivate);
		notifyExpandBar(SWT.FocusOut);
	}

	private void collapseNotify() {
		notifyExpandBar(SWT.MouseMove);
		notifyExpandBar(SWT.Activate);
		notifyExpandBar(SWT.FocusIn);
		notifyExpandBar(SWT.MouseDown);
		notifyExpandBar(SWT.Collapse);
		notifyExpandBar(SWT.MeasureItem);
		notifyExpandBar(SWT.Deactivate);
		notifyExpandBar(SWT.FocusOut);
	}

	private void notifyExpandBar(int eventType) {
		notify(eventType, createEvent(), expandBar);
	}

	protected Event createEvent() {
		Event e = super.createEvent();
		e.widget = expandBar;
		e.item = widget;
		return e;
	}

}
