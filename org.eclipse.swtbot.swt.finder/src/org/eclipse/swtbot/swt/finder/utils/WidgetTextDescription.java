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
package org.eclipse.swtbot.swt.finder.utils;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

/**
 * Describes the widget, by invoking {@link SWTUtils#getText(Object)} on the widget.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WidgetTextDescription implements SelfDescribing {

	private final Widget	widget;

	public WidgetTextDescription(Widget widget) {
		this.widget = widget;
	}

	public void describeTo(Description description) {
		description.appendText(ClassUtils.simpleClassName(widget) + " with text {" + SWTUtils.toString(widget) + "}");
	}
	
	public String toString() {
		return StringDescription.asString(this);
	}

}
