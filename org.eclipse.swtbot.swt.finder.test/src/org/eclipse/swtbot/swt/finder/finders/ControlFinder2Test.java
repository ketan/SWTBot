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

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.TreePath;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ControlFinder2Test extends AbstractSWTTestCase {

	private ControlFinder	finder;

	public void testFindsAllTabItem() throws Exception {
		selectCTabFolder();
		List tabItems = finder.findControls(instanceOf(CTabItem.class));
		Assert.assertEquals(3, tabItems.size());
	}

	public void testGetsControlPath() throws Exception {
		selectCTabFolder();
		List labels = finder.findControls(allOf(instanceOf(CTabItem.class), withText(
				"CTabItem 1")));
		Widget w = (Widget) labels.get(0);
		TreePath path = finder.getPath(w);
		Assert.assertEquals(8, path.getSegmentCount());
	}

	private void selectCTabFolder() {
		UIThreadRunnable.syncExec(display, new VoidResult() {
			public void run() {
				List findControls = finder.findControls(instanceOf(TabFolder.class));
				TabFolder folder = (TabFolder) findControls.get(0);
				folder.setSelection(2);
			}
		});
	}

	protected void setUp() throws Exception {
		super.setUp();
		finder = new ControlFinder();
	}

	protected Shell getFocusShell() {
		return customControlShell;
	}
}
