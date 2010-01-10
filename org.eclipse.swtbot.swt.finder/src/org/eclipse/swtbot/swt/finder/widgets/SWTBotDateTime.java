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

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.hamcrest.SelfDescribing;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = DateTime.class, preferredName = "dateTime", referenceBy = { ReferenceBy.LABEL })
public class SWTBotDateTime extends AbstractSWTBotControl<DateTime> {

	/**
	 * Constructs an instance of this object with the given widget.
	 *
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotDateTime(DateTime w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an instance of this object with the given widget.
	 *
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotDateTime(DateTime w, SelfDescribing description) throws WidgetNotFoundException {
		super(w, description);
	}

	/**
	 * Gets the date of this widget.
	 *
	 * @return the date/time set into the widget.
	 */
	public Date getDate() {
		return syncExec(new Result<Date>() {
			public Date run() {
				int year = widget.getYear();
				int month = widget.getMonth();
				int day = widget.getDay();

				int hours = widget.getHours();
				int minutes = widget.getMinutes();
				int seconds = widget.getSeconds();
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(0);
				calendar.set(year, month, day, hours, minutes, seconds);
				return calendar.getTime();
			}

		});
	}

	/**
	 * Sets the date.
	 *
	 * @param toSet the date to set into the control.
	 */
	public void setDate(final Date toSet) {
		log.debug(MessageFormat.format("Setting date on control: {0} to {1}", this, toSet)); //$NON-NLS-1$
		waitForEnabled();
		syncExec(new VoidResult() {
			@SuppressWarnings("deprecation")
			public void run() {
				widget.setYear(toSet.getYear() + 1900);
				widget.setDay(toSet.getDate());
				widget.setMonth(toSet.getMonth());

				widget.setHours(toSet.getHours());
				widget.setMinutes(toSet.getMinutes());
				widget.setSeconds(toSet.getSeconds());
			}
		});
		notify(SWT.Selection);
	}
}
