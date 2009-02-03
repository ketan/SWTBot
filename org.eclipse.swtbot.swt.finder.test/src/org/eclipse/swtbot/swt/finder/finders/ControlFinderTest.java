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

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertNotSameWidget;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.TreePath;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTabItem;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ControlFinderTest extends AbstractSWTTestCase {

	private ControlFinder	finder;

	@Test
	public void findsAGroup() throws Exception {
		final List frames = finder.findControls(instanceOf(Group.class));
		assertEquals(12, frames.size());
		assertText("Image Buttons", (Widget) frames.get(2));
	}

	@Test
	public void findsAllTabItem() throws Exception {
		List tabItems = finder.findControls(instanceOf(TabItem.class));
		assertEquals(24, tabItems.size());
	}

	@Test
	public void findsAShell() throws Exception {
		List shells = finder.findShells("SWT Controls");
		assertFalse(shells.isEmpty());
		assertEquals(1, shells.size());
		assertEquals(controlShell, shells.get(0));
	}

	@Test
	public void findsATabItem() throws Exception {
		List tabItems = finder.findControls(allOf(instanceOf(TabItem.class), withText("Dialog")));
		final TabItem items[] = new TabItem[] { null };
		display.syncExec(new Runnable() {
			public void run() {
				items[0] = ((TabFolder) controlShell.getChildren()[0]).getItems()[5];
			}
		});

		assertEquals(1, tabItems.size());
		assertEquals(items[0], tabItems.get(0));
	}

	@Test
	public void findsText() throws Exception {
		List textBoxes = finder.findControls(instanceOf(Text.class));
		assertEquals(1, textBoxes.size());
	}

	@Test
	public void findsTwoButtons() throws Exception {
		final List buttons = finder.findControls(allOf(instanceOf(Button.class), withText("One")));
		assertEquals(2, buttons.size());
		assertText("One", (Widget) buttons.get(0));
		assertText("One", (Widget) buttons.get(1));
	}

	@Test
	public void getsControlPath() throws Exception {
		List labels = finder.findControls(allOf(instanceOf(Button.class), withText("One")));
		Widget w = (Widget) labels.get(0);
		TreePath path = finder.getPath(w);
		assertEquals(7, path.getSegmentCount());
	}

	@Test
	public void getsControlPathToTabItem() throws Exception {
		List tabItems = finder.findControls(allOf(instanceOf(TabItem.class), withText("Dialog")));
		TreePath path = finder.getPath((Widget) tabItems.get(0));
		assertEquals(3, path.getSegmentCount());
	}

	@Test
	public void getsOnlyVisibleControls() throws Exception {
		// use the default tab
		List textBoxesOnButtonTab = finder.findControls(instanceOf(Text.class));
		assertEquals(1, textBoxesOnButtonTab.size());
		assertText("", (Widget) textBoxesOnButtonTab.get(0));

		// switch to another tab
		List tabItems = finder.findControls(allOf(instanceOf(TabItem.class), withText("Text")));
		new SWTBotTabItem((TabItem) tabItems.get(0)).activate();

		// should get different tabs this time
		List textBoxesOnTextTab = finder.findControls(instanceOf(Text.class));
		assertEquals(2, textBoxesOnTextTab.size());
		assertTextContains("The quick brown fox jumps over the lazy dog", (Widget) textBoxesOnTextTab.get(0));
		assertText("", (Widget) textBoxesOnTextTab.get(1));
		assertNotSameWidget((Widget) textBoxesOnButtonTab.get(0), (Widget) textBoxesOnTextTab.get(0));
	}

	public void setUp() throws Exception {
		super.setUp();
		finder = new ControlFinder();
		new SWTBot().tabItem("Button").activate();
	}

}
