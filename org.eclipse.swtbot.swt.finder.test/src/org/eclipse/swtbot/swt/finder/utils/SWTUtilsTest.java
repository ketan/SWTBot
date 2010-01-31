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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTUtilsTest extends AbstractSWTTestCase {

	@Test
	public void findsRightIndexOfControlInParentWithNoParent() throws Exception {
		assertEquals(-1, SWTUtils.widgetIndex(controlShell));
	}

	@Test
	public void findsIndexOfArbritryControl() throws Exception {
		assertEquals(5, SWTUtils.widgetIndex(getChildren()[5]));
		assertEquals(0, SWTUtils.widgetIndex(getChildren()[0]));
		assertEquals(-1, SWTUtils.widgetIndex(null));
	}

	@Test
	public void findsNextWidget() throws Exception {
		assertSame(getChildren()[1], SWTUtils.nextWidget(getChildren()[0]));
	}

	@Test
	public void nextWidgetOnLastWidgetIsNull() throws Exception {
		final Control[] children = getChildren();
		assertSame(null, SWTUtils.nextWidget(children[children.length - 1]));
	}

	@Test
	public void findsPreviousWidget() throws Exception {
		assertSame(getChildren()[2], SWTUtils.previousWidget(getChildren()[3]));
	}

	@Test
	public void getsCorrectStyle() throws Exception {
		assertTrue(SWTUtils.hasStyle(bot.button("One").widget, SWT.PUSH));
		assertTrue(SWTUtils.hasStyle(bot.radio("SWT.RADIO").widget, SWT.RADIO));
		assertTrue(SWTUtils.hasStyle(bot.checkBox("SWT.FLAT").widget, SWT.CHECK));

		assertTrue(SWTUtils.hasStyle(bot.checkBox("SWT.FLAT").widget, SWT.NONE));
		assertFalse(SWTUtils.hasStyle(null, SWT.CHECK));
	}

	@Test
	public void previousWidgetOnFirstWidget() throws Exception {
		assertSame(null, SWTUtils.previousWidget(getChildren()[0]));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		bot.tabItem("Button").activate();
	}

	@Test
	public void getsToString() throws Exception {
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
