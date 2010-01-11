/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Paulin - http://swtbot.org/bugzilla/show_bug.cgi?id=36
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link Text} widget.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = Text.class, preferredName = "text", referenceBy = { ReferenceBy.LABEL, ReferenceBy.TEXT, ReferenceBy.TOOLTIP, ReferenceBy.MESSAGE })
public class SWTBotText extends AbstractSWTBot<Text> {

	/**
	 * Constructs a new instance of this object.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotText(Text w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs a new instance of this object.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotText(Text w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	/**
	 * Sets the text of the widget.
	 *
	 * @param text the text to be set.
	 * @return the same instance.
	 */
	public SWTBotText setText(final String text) {
		waitForEnabled();
		asyncExec(new VoidResult() {
			public void run() {
				widget.setText(text);
			}
		});
		return this;
	}

	/**
	 * Types the string in the text box.
	 *
	 * @param text the text to be typed.
	 * @return the same instance.
	 * @since 1.2
	 */
	public SWTBotText typeText(final String text) {
		return typeText(text, SWTBotPreferences.TYPE_INTERVAL);
	}

	/**
	 * Types the string in the text box.
	 *
	 * @param text the text to be typed.
	 * @param interval the interval between consecutive key strokes.
	 * @return the same instance.
	 * @since 1.2
	 */
	public SWTBotText typeText(final String text, int interval) {
		log.debug(MessageFormat.format("Inserting text:{0} into text {1}", text, this)); //$NON-NLS-1$
		setFocus();
		keyboard().typeText(text, interval);
		return this;
	}

	/**
	 * Notifies of the keyboard event.
	 * <p>
	 * FIXME need some work for CTRL|SHIFT + 1 the 1 is to be sent as '!' in this case.
	 * </p>
	 *
	 * @param modificationKeys the modification keys.
	 * @param c the character.
	 * @see Event#character
	 * @see Event#stateMask
	 * @deprecated use {@link #pressShortcut(int, char)} instead. This api will be removed.
	 */
	@Deprecated
	public void notifyKeyboardEvent(int modificationKeys, char c) {
		keyboard().pressShortcut(modificationKeys, c);
	}

	/**
	 * Notifies of keyboard event.
	 *
	 * @param modificationKeys the modification key.
	 * @param c the character.
	 * @param keyCode any special keys (function keys, arrow or navigation keys etc.)
	 * @see Event#keyCode
	 * @see Event#character
	 * @see Event#stateMask
	 * @deprecated use {@link #pressShortcut(int, int, char)} instead. This api will be removed.
	 */
	@Deprecated
	public void notifyKeyboardEvent(int modificationKeys, char c, int keyCode) {
		pressShortcut(modificationKeys, keyCode, c);
	}

	/**
	 * Select the contents of the entire widget.
	 * @return the same instance.
	 */
	public SWTBotText selectAll() {
		syncExec(new VoidResult() {
			public void run() {
				widget.selectAll();
			}
		});
		return this;
	}

}
