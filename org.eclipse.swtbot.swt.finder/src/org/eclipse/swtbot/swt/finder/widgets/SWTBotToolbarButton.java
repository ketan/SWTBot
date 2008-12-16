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
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = ToolItem.class, preferredName = "toolbarButton", referenceBy = { ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })
public class SWTBotToolbarButton extends AbstractSWTBot<ToolItem> {

	/**
	 * Construcst an new instance of this item.
	 *
	 * @param w the tool item.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotToolbarButton(ToolItem w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Click on the tool item.
	 *
	 * @since 1.0
	 */
	public void click() {
		log.debug(MessageFormat.format("Clicking on {0}", this));
		assertEnabled();
		notify(SWT.MouseEnter);
		notify(SWT.MouseMove);
		notify(SWT.Activate);
		notify(SWT.MouseDown);
		notify(SWT.MouseUp);
		notify(SWT.Selection);
		notify(SWT.MouseHover);
		notify(SWT.MouseMove);
		notify(SWT.MouseExit);
		notify(SWT.Deactivate);
		notify(SWT.FocusOut);
		log.debug(MessageFormat.format("Clicked on {0}", this));
	}

	@Override
	public boolean isEnabled() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.isEnabled();
			}
		});
	}
}
