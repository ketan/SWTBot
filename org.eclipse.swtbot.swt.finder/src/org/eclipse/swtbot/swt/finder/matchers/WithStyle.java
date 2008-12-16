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
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;


/**
 * Matches if the widget has the specified style bits set.
 *
 * @see Widget#getStyle()
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: WithStyle.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 * @since 2.0
 */
public class WithStyle<T extends Widget> extends AbstractMatcher<T> {

	private final int		style;
	private final String	styleDescription;

	/**
	 * Matches a widget that has the specified style bit set.
	 *
	 * @param style the style bits.
	 * @param styleDescription the description of the style bits.
	 */
	public WithStyle(int style, String styleDescription) {
		this.style = style;
		this.styleDescription = styleDescription;
	}

	protected boolean doMatch(Object obj) {
		return SWTUtils.hasStyle((Widget) obj, style);
	}

	public void describeTo(Description description) {
		description.appendText("with style '").appendText(styleDescription).appendText("'");
	}

	/**
	 * Matches a widget that has the specified style bit set.
	 *
	 * @param style the style bits.
	 * @param styleDescription the description of the style bits.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends Widget> withStyle(int style, String styleDescription) {
		return new WithStyle<Widget>(style, styleDescription);
	}

}
