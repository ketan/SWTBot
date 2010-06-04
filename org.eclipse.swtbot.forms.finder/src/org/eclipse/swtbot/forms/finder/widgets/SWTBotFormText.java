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
import org.eclipse.swtbot.swt.finder.results.StringResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBotControl;
import org.eclipse.ui.forms.widgets.FormText;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link FormText} widget.
 * 
 * @author Chris Aniszczyk &lt;caniszczyk [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = FormText.class, preferredName = "formText", referenceBy = { ReferenceBy.TEXT })
public class SWTBotFormText extends AbstractSWTBotControl<FormText> {

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotFormText(FormText w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotFormText(FormText w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}
	
	public String selectionText() {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getSelectionText();
			}
		});
	}
	
	public String selectedLinkText() {
		return syncExec(new StringResult() {
			public String run() {
				return widget.getSelectedLinkText();
			}
		});
	}
	
	public void setText(final String text, final boolean parseTags, final boolean expandURLs) {
		syncExec(new VoidResult() {
			public void run() {
				widget.setText(text, parseTags, expandURLs);
			}
		});
	}

}
