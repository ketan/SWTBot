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



import static org.hamcrest.Matchers.anything;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.AbstractMenuExampleTest;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MenuFinderTest extends AbstractMenuExampleTest {

	public void testClicksMenuItem() throws Exception {
		List findControls = menuFinder.findMenus(anything());
		MenuItem menuItem = (MenuItem) findControls.get(1);
		try {
			menuItem.notifyListeners(SWT.Selection, null);
			fail("Expecting an SWTException");
		} catch (SWTException e) {
			pass();
		}
		assertText("&Edit", menuItem);
	}

	public void testFindsAllVisibleMenus() throws Exception {
		List findControls = menuFinder.findMenus(menuShell, anything(), true);
		assertEquals(25, findControls.size());
		assertText("&Find...\tCtrl+F", (Widget) findControls.get(21));
	}

	public void testDoesFindsAllVisibleTopLevelMenus() throws Exception {
		List findControls = menuFinder.findMenus(anything());
		assertEquals(4, findControls.size());
		assertText("&Edit", (Widget) findControls.get(1));
	}

}
