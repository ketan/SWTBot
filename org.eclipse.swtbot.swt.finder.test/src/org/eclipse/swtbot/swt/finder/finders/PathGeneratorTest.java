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
package org.eclipse.swtbot.swt.finder.finders;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.TreePath;
import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PathGeneratorTest extends AbstractSWTTestCase {

	private ControlFinder	finder;

	@SuppressWarnings("unchecked")
	// varargs and generics doesn't mix well!
	@Test
	public void generatesStringFromPath() throws Exception {
		Matcher<TabItem> withText = withText("Dialog");
		List<TabItem> tabItems = finder.findControls(allOf(widgetOfType(TabItem.class), withText));
		Widget widget = tabItems.get(0);
		String string = new PathGenerator().getPathAsString(widget);
		assertEquals("//Shell/-1//TabFolder/0//TabItem/5", string);
	}

	@Test
	public void getsPathFromInvalidString() throws Exception {
		TreePath path = new PathGenerator().getPathFromString("//", display);
		assertEquals(null, path);
	}

	@SuppressWarnings("unchecked")
	// varargs and generics doesn't mix well!
	@Test
	public void getsPathFromString() throws Exception {
		TreePath path = new PathGenerator().getPathFromString("//Shell/0//TabFolder/0//TabItem/5", display);
		Matcher<TabItem> withText = withText("Dialog");
		List<TabItem> tabItems = finder.findControls(allOf(widgetOfType(TabItem.class), withText));
		TreePath expected = finder.getPath(tabItems.get(0));

		assertEquals(expected, path);
	}

	public void setUp() throws Exception {
		super.setUp();
		finder = new ControlFinder();
	}
}
