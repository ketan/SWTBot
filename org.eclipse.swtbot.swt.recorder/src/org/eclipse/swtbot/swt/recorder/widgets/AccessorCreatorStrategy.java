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
package org.eclipse.swtbot.swt.recorder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withStyle;
import static org.hamcrest.Matchers.allOf;

import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.Style;
import org.eclipse.swtbot.swt.finder.utils.ClassUtils;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.widgets.text.AbstractTextBasedRecorderListener;
import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class AccessorCreatorStrategy {

	private final Event								event;
	private final SWTBotWidget						annotation;
	private final SWTBot							bot;
	private final AbstractTextBasedRecorderListener	eventListener;

	/**
	 * @param event the generated event
	 * @param eventListener the listener which recorded the event
	 * @param annotation the annotation on the SWTBot widget that
	 * @param bot the bot used to find widgets
	 */
	public AccessorCreatorStrategy(Event event, AbstractTextBasedRecorderListener eventListener, SWTBotWidget annotation, SWTBot bot) {
		this.event = event;
		this.eventListener = eventListener;
		this.annotation = annotation;
		this.bot = bot;
	}

	public SWTBotAccessor create() {
		Widget widget = getWidget();
		String text = getText(widget);
		if (!StringUtils.isEmptyOrNull(text)) {
			Matcher<Widget> matcher = createMatcher(text);
			List<? extends Widget> similarWidgets = similarWidgets(matcher, widget);
			int index = similarWidgets.indexOf(widget);
			return new SWTBotAccessor("bot", annotation.preferredName(), text, index); //$NON-NLS-1$
		}
		return null;
	}

	private Widget getWidget() {
		return eventListener.getWidget(event);
	}

	private final String getText(Widget widget) {
		return SWTUtils.getText(widget).replaceAll("&", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Matcher<Widget> createMatcher(String text) {
		return allOf(typeMatcher(), mnemonicTextMatcher(text), styleMatcher());
	}

	private final Matcher<? extends Widget> typeMatcher() {
		return widgetOfType(this.annotation.clasz());
	}

	private final Matcher<? extends Widget> mnemonicTextMatcher(String text) {
		return withMnemonic(text);
	}

	private <T extends Widget> List<? extends T> similarWidgets(Matcher<T> matcher, Widget widget) {
		return bot.widgets(matcher);
	}

	private final Matcher<? extends Widget> styleMatcher() {
		Style style = annotation.style();
		return withStyle(style.value(), style.name());
	}

	private Shell getShell(Widget widget) {
		if (widget instanceof Control)
			return ((Control) widget).getShell();
		throw new IllegalArgumentException("Cannot find the shell for widgets of type: " + ClassUtils.simpleClassName(widget)); //$NON-NLS-1$
	}
}
