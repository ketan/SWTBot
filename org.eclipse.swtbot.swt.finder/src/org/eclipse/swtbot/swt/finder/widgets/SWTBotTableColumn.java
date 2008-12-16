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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTableColumn extends AbstractSWTBot<TableColumn> {

	private final Table	parent;

	/**
	 * Constructs a new instance of this object.
	 *
	 * @param w the widget.
	 * @param parent the parent table.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotTableColumn(TableColumn w, Table parent) throws WidgetNotFoundException {
		super(w);
		this.parent = parent;
	}

	/**
	 * Clicks the item.
	 */
	public void click() {
		assertEnabled();
		notify(SWT.Selection);
		notify(SWT.MouseUp, createMouseEvent(0, 0, 1, SWT.BUTTON1, 1), parent);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
