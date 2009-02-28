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
package org.eclipse.swtbot.swt.recorder.widgets.text;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withStyle;
import static org.hamcrest.Matchers.allOf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.Style;
import org.eclipse.swtbot.swt.finder.utils.ClassUtils;
import org.eclipse.swtbot.swt.finder.utils.SWTBotEvents;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAction;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
abstract class AbstractTextBasedRecorderListener implements Listener {

	private final ActionList	eventList;
	protected final SWTBot		bot;
	private final Class<?>		widgetType;
	private SWTBotWidget		annotation;
	private final Logger		log;

	public AbstractTextBasedRecorderListener(Class<?> widgetType, ActionList eventList, SWTBot bot) {
		this.widgetType = widgetType;
		this.eventList = eventList;
		this.bot = bot;
		this.annotation = widgetType.getAnnotation(SWTBotWidget.class);
		this.log = Logger.getLogger(getClass());
	}

	public void handleEvent(Event event) {
		try {
			if (!canHandleEvent(event))
				return;
			doHandle(event);
		} catch (Exception e) {
			log.fatal("Could not record event " + SWTBotEvents.toString(event), e); //$NON-NLS-1$
		}
	}

	protected abstract SWTBotEvent createEvent(Event event);

	protected final SWTBotAccessor createAccessor(Event event) {
		Widget widget = getWidget(event);
		String text = getText(widget);
		if (!StringUtils.isEmptyOrNull(text)) {
			Matcher<?> matcher = createMatcher(text);
			List<? extends Widget> similarWidgets = similarWidgets(matcher, widget);
			int index = similarWidgets.indexOf(widget);
			return new SWTBotAccessor("bot", annotation.preferredName(), text, index); //$NON-NLS-1$
		}
		return null;
	}

	protected Matcher<Widget> createMatcher(String text) {
		return allOf(typeMatcher(), mnemonicTextMatcher(text), styleMatcher());
	}

	protected Widget getWidget(Event event) {
		return event.widget;
	}

	private void doHandle(Event event) {
		SWTBotAction action = new SWTBotAction(createAccessor(event), createEvent(event));
		eventList.add(action);
	}

	private boolean canHandleEvent(Event event) {
		return hasSWTBotAnnotation() && canHandleWidget(event);
	}

	private final boolean canHandleWidget(Event event) {
		return matchesWidgetType(getWidget(event)) && matchesWidgetStyle(getWidget(event)) && !SWTUtils.isEmptyOrNullText(getWidget(event))
				&& doCanHandleEvent(event);
	}

	protected abstract boolean doCanHandleEvent(Event event);

	protected List<? extends Widget> similarWidgets(Matcher<?> matcher, Widget widget) {
		return bot.widgets(matcher, getShell(widget));
	}

	private final String getText(Widget widget) {
		return SWTUtils.getText(widget).replaceAll("&", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected final Matcher<? extends Widget> mnemonicTextMatcher(String text) {
		return withMnemonic(text);
	}

	protected final Matcher<? extends Widget> typeMatcher() {
		return widgetOfType(this.annotation.clasz());
	}

	protected Shell getShell(Widget widget) {
		if (widget instanceof Control)
			return ((Control) widget).getShell();
		throw new IllegalArgumentException("Cannot find the shell for widgets of type: " + ClassUtils.simpleClassName(widget)); //$NON-NLS-1$
	}

	private final Matcher<? extends Widget> styleMatcher() {
		Style style = annotation.style();
		return withStyle(style.value(), style.name());
	}

	protected boolean matchesWidgetStyle(Widget widget) {
		return SWTUtils.hasStyle(widget, this.annotation.style().value());
	}

	private boolean matchesWidgetType(Widget widget) {
		if (widget == null)
			return false;
		return this.annotation.clasz().equals(widget.getClass());
	}

	private boolean hasSWTBotAnnotation() {
		return this.annotation != null;
	}
}
