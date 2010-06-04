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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBotControl;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link ImageHyperlink} widget.
 * 
 * @author Chris Aniszczyk &lt;caniszczyk [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = ImageHyperlink.class, preferredName = "imagehyperlink", referenceBy = { ReferenceBy.MNEMONIC })
public class SWTBotImageHyperlink extends AbstractSWTBotControl<ImageHyperlink> {

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotImageHyperlink(ImageHyperlink w) throws WidgetNotFoundException {
		super(w);
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotImageHyperlink(ImageHyperlink w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}
	
	public AbstractSWTBot<ImageHyperlink> click() {
		return click(true);
	}

	/**
	 * Return the ImageHyperlink's image or <code>null</code>.
	 * 
	 * @return the image of the image hyperlink or <code>null</code>.
	 */
	public Image image() {
		return syncExec(new Result<Image>() {
			public Image run() {
				return widget.getImage();
			}
		});
	}
	
	/**
	 * Return the ImageHyperlink's hover image or <code>null</code>.
	 * 
	 * @return the hover image of the image hyperlink or <code>null</code>.
	 */
	public Image hoverImage() {
		return syncExec(new Result<Image>() {
			public Image run() {
				return widget.getHoverImage();
			}
		});
	}
	
	/**
	 * Return the ImageHyperlink's active image or <code>null</code>.
	 * 
	 * @return the active image of the image hyperlink or <code>null</code>.
	 */
	public Image activeImage() {
		return syncExec(new Result<Image>() {
			public Image run() {
				return widget.getActiveImage();
			}
		});
	}

	/**
	 * Clicks on the image hyperlink with the specified text.
	 * 
	 * @param hyperlinkText the text of the image hyperlink in case there are more than one hyperlinks.
	 * @return itself.
	 */
	public AbstractSWTBot<ImageHyperlink> click(String hyperlinkText) {
		log.debug(MessageFormat.format("Clicked on {0}", SWTUtils.getText(widget))); //$NON-NLS-1$
		String text = getText();
		boolean isText = text.contains(">" + hyperlinkText + "<");
		Assert.isLegal(isText, "Link does not contain text (" + hyperlinkText + "). It contains (" + text + ")");

		hyperlinkText = extractHyperlinkTextOrHREF(hyperlinkText, text);
		notify(SWT.Selection, createHyperlinkEvent(hyperlinkText));

		log.debug(MessageFormat.format("Clicked on {0}", SWTUtils.getText(widget))); //$NON-NLS-1$
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
