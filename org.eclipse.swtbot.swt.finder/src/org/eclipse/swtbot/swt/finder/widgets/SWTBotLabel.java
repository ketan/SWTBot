/*******************************************************************************
 * Copyright (c) 2008 Stephen Paulin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephen Paulin - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;


import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.Result;

/**
 * This represents a {@link Label} widget.
 *
 * @author Stephen Paulin &lt;paulin [at] developerskingdom [dot] com&gt;
 * @version $Id$
 * @since 1.2
 */
@SWTBotWidget(clasz = Label.class, preferredName = "label", referenceBy = { ReferenceBy.MNEMONIC })
public class SWTBotLabel extends AbstractSWTBot<Label> {

	/**
	 * Constructs an instance of this using the given finder and text to search for.
	 *
	 * @param widget the widget
	 * @throws WidgetNotFoundException if the widget is null or disposed.
	 */
	public SWTBotLabel(Label widget) throws WidgetNotFoundException {
		super(widget);
	}

	/**
	 * Return the Label's image or <code>null</code>.
	 *
	 * @return the image of the label or <code>null</code>.
	 */
	public Image image() {
		return syncExec(new Result<Image>() {
			public Image run() {
				return widget.getImage();
			}
		});
	}

	/**
	 * Returns the alignment. The alignment style (LEFT, CENTER or RIGHT) is returned.
	 *
	 * @return SWT.LEFT, SWT.RIGHT or SWT.CENTER
	 */
	public int alignment() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getAlignment();
			}
		});
	}

}
