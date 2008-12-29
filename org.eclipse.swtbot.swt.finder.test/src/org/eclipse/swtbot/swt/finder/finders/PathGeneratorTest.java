/*******************************************************************************
 * Copyright 2007 new SWTBot, http://swtbot.org/
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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;

import junit.framework.Assert;

import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.utils.TreePath;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PathGeneratorTest extends AbstractSWTTestCase {

	private ControlFinder	finder;

	public void testGeneratesStringFromPath() throws Exception {
		List tabItems = finder.findControls(allOf(instanceOf(TabItem.class), withText("Dialog")));
		Widget widget = (Widget) tabItems.get(0);
		String string = new PathGenerator().getPathAsString(widget);
		Assert.assertEquals("//Shell/-1//TabFolder/0//TabItem/5", string);
	}

	public void testGetsPathFromInvalidString() throws Exception {
		TreePath path = new PathGenerator().getPathFromString("//", display);
		Assert.assertEquals(null, path);
	}

	public void testGetsPathFromString() throws Exception {
		TreePath path = new PathGenerator().getPathFromString("//Shell/0//TabFolder/0//TabItem/5", display);
		List tabItems = finder.findControls(allOf(instanceOf(TabItem.class), withText("Dialog")));
		TreePath expected = finder.getPath((Widget) tabItems.get(0));

		Assert.assertEquals(expected, path);
	}

	protected void setUp() throws Exception {
		super.setUp();
		finder = new ControlFinder();
	}
}
