/*******************************************************************************
 * Copyright (c) 2010 Chris Aniszczyk and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Chris Aniszczyk <caniszczyk@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.forms.finder.widgets;

import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.ui.forms.widgets.TreeNode;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link TreeNode} widget.
 * 
 * @author Chris Aniszczyk &lt;caniszczyk [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = TreeNode.class, preferredName = "treeNode", referenceBy = { ReferenceBy.TEXT })
public class SWTBotTreeNode extends SWTBotToggleHyperlink {

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTreeNode(TreeNode w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotTreeNode(TreeNode w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	@Override
	public SWTBotToggleHyperlink click() {
		log.debug(MessageFormat.format("Clicking on {0}", SWTUtils.getText(widget))); //$NON-NLS-1$
		internalToggle();
		sendNotifications();
		log.debug(MessageFormat.format("Clicked on {0}", SWTUtils.getText(widget))); //$NON-NLS-1$
		return this;
	}
	
	private void internalToggle() {
		syncExec(new VoidResult() {
			public void run() {
				widget.setExpanded(!widget.isExpanded());
			}
		});
	}

}
