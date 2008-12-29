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


import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.PathGenerator;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.TreePath;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * Tells if a particular widget is within a {@link Group} with the specified text.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class InGroup<T extends Widget> extends AbstractMatcher<T> {

	/**
	 * The mnemonic text matcher instance to use.
	 */
	private final Matcher<?>	matcher;

	/**
	 * Matches a widget that has the specified Label.
	 *
	 * @param labelText the label.
	 */
	public InGroup(String labelText) {
		matcher = new WithMnemonic<Widget>(labelText);
	}

	/**
	 * Matches a widget in a group, if the matcher evaluates to true for the group.
	 *
	 * @param matcher the matcher.
	 */
	public InGroup(Matcher matcher) {
		this.matcher = matcher;
	}

	protected boolean doMatch(Object obj) {
		Widget previousWidget = SWTUtils.previousWidget((Widget) obj);
		TreePath path = new PathGenerator().getPath((Widget) obj);
		int segmentCount = path.getSegmentCount();
		for (int i = 1; i < segmentCount; i++) {
			previousWidget = (Widget) path.getSegment(segmentCount - i - 1);
			if ((previousWidget instanceof Group) && matcher.matches(previousWidget))
				return true;
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("in group (").appendDescriptionOf(matcher).appendText(")");
	}

	/**
	 * Matches a widget that belongs to the specified group
	 *
	 * @param labelText the label.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static Matcher<? extends Widget> inGroup(String labelText) {
		return new InGroup<Widget>(labelText);
	}

	/**
	 * Matches a widget in a group, if the matcher evaluates to true for the group.
	 *
	 * @param matcher the matcher.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static Matcher<? extends Widget> inGroup(Matcher<?> matcher) {
		return new InGroup<Widget>(matcher);
	}

}
