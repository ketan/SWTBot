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
package org.eclipse.swtbot.swt.finder.utils;


import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTUtilsTest extends AbstractSWTTestCase {

	public void testFindsRightIndexOfControlInParentWithNoParent() throws Exception {
		assertEquals(-1, SWTUtils.widgetIndex(controlShell));
	}

	public void testFindsIndexOfArbritryControl() throws Exception {
		assertEquals(5, SWTUtils.widgetIndex(getChildren()[5]));
		assertEquals(0, SWTUtils.widgetIndex(getChildren()[0]));
		assertEquals(-1, SWTUtils.widgetIndex(null));
	}

	public void testFindsNextWidget() throws Exception {
		assertSame(getChildren()[1], SWTUtils.nextWidget(getChildren()[0]));
	}

	public void testNextWidgetOnLastWidgetIsNull() throws Exception {
		final Control[] children = getChildren();
		assertSame(null, SWTUtils.nextWidget(children[children.length - 1]));
	}

	public void testFindsPreviousWidget() throws Exception {
		assertSame(getChildren()[2], SWTUtils.previousWidget(getChildren()[3]));
	}

	public void testPreviousWidgetOnFirstWidget() throws Exception {
		assertSame(null, SWTUtils.previousWidget(getChildren()[0]));
	}

	public void testToString() throws Exception {
		assertEquals("TabFolder {}", SWTUtils.toString(controlExample.getTabFolder()));
	}

	private Control[] getChildren() {
		final Control[][] children = new Control[][] { null };
		display.syncExec(new Runnable() {
			public void run() {
				children[0] = controlExample.getTabFolder().getChildren();
			}
		});
		return children[0];
	}

}
