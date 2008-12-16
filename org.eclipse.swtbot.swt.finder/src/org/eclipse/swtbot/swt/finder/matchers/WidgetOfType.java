/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Factory;


/**
 * Tells if a particular widget is of a specified type.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class WidgetOfType<T extends Widget> extends AbstractMatcher<T> {

	/**
	 * The type of widget to match.
	 */
	private Class<? extends Widget>	type;

	/**
	 * Matches a widget that has the specified type
	 *
	 * @param type the type of the widget.
	 */
	public WidgetOfType(Class<? extends Widget> type) {
		this.type = type;
	}

	protected boolean doMatch(Object obj) {
		return type.equals(obj.getClass());
	}

	public void describeTo(Description description) {
		description.appendText("of type '").appendText(type.getSimpleName()).appendText("'");
	}

	/**
	 * Matches a widget that has the specified type
	 *
	 * @param type the type of the widget.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> widgetOfType(Class<? extends Widget> type) {
		return new WidgetOfType<Widget>(type);
	}

}
