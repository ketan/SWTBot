/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link Link} widget.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Link.class, preferredName = "link", referenceBy = { ReferenceBy.MNEMONIC })
public class SWTBotLink extends AbstractSWTBotControl<Link> {

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotLink(Link w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotLink(Link w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	public AbstractSWTBot<Link> click() {
		return click(true);
	}

	/**
	 * Clicks on the hyperlink with the specified text.
	 * 
	 * @param hyperlinkText the text of the hyperlink in case there are more than one hyperlinks.
	 * @return itself.
	 */
	public AbstractSWTBot<Link> click(String hyperlinkText) {
		String text = getText();
		boolean isText = text.contains(">" + hyperlinkText + "<");
		Assert.isLegal(isText, "Link does not contain text (" + hyperlinkText + "). It contains (" + text + ")");

		hyperlinkText = extractHyperlinkTextOrHREF(hyperlinkText, text);
		notify(SWT.Selection, createHyperlinkEvent(hyperlinkText));

		return click(true);
	}

	private String extractHyperlinkTextOrHREF(String hyperlinkText, String text) {
		Pattern pattern = Pattern.compile(".*<[aA] [hH][rR][eE][fF]\\s*=\\s*['\"](.*)['\"]>" + hyperlinkText + "</[aA]>.*");
		Matcher matcher = pattern.matcher(text);
		return matcher.find() ? matcher.group(1) : hyperlinkText;
	}

	private Event createHyperlinkEvent(String hyperlinkText) {
		Event e = createEvent();
		e.text = hyperlinkText;
		return e;
	}
}
