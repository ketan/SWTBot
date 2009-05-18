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

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.utils.ClassUtils;
import org.eclipse.swtbot.swt.finder.utils.SWTBotEvents;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAction;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.eclipse.swtbot.swt.recorder.widgets.AccessorCreatorStrategy;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class AbstractTextBasedRecorderListener implements Listener {

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

	private final SWTBotAccessor createAccessor(Event event) {
		return new AccessorCreatorStrategy(event, this, annotation, bot).create();
	}

	public Widget getWidget(Event event) {
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
		Widget widget = getWidget(event);
		return matchesWidgetType(widget) && matchesWidgetStyle(widget) && !SWTUtils.isEmptyOrNullText(widget) && doCanHandleEvent(event);
	}

	protected abstract boolean doCanHandleEvent(Event event);

	protected Shell getShell(Widget widget) {
		if (widget instanceof Control)
			return ((Control) widget).getShell();
		throw new IllegalArgumentException("Cannot find the shell for widgets of type: " + ClassUtils.simpleClassName(widget)); //$NON-NLS-1$
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
