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
package org.eclipse.swtbot.swt.finder.widgets;

import static org.hamcrest.Matchers.anything;

import java.util.List;


import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.swt.finder.AbstractMenuExampleTest;
import org.eclipse.swtbot.swt.finder.SWTBot;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotMenuTest extends AbstractMenuExampleTest {

	private SWTBot	bot;

	public void testFindsMenus() throws Exception {
		assertText("&File", bot.menu("File").widget);
	}

	public void testFindsSubMenus() throws Exception {
		SWTBotMenu menu = bot.menu("File").menu("Exit");
		assertText("E&xit", menu.widget);
	}

	public void testClicksSubMenus() throws Exception {
		display.syncExec(new Runnable() {
			public void run() {
				menuExample.addAddressBook(new String[] { "last2", "first", "business phone", "home phone",
						"email@addres.ss", "fax number" });
				menuExample.addAddressBook(new String[] { "last6", "first", "business phone", "home phone",
						"email@addres.ss", "fax number" });
				menuExample.addAddressBook(new String[] { "last4", "first", "business phone", "home phone",
						"email@addres.ss", "fax number" });
			}
		});

		List findControls = menuFinder.findMenus(anything());
		MenuItem menuItem = (MenuItem) findControls.get(14);
		assertText("Last Name", menuItem);

		bot.menu("Last Name").click();

		final TableItem[][] treeItems = new TableItem[][] { null };
		display.syncExec(new Runnable() {
			public void run() {
				treeItems[0] = menuExample.getTreeItems();
			}
		});
		TableItem[] tableItems = treeItems[0];
		assertEquals(3, tableItems.length);
		assertText("last2", tableItems[0]);
		assertText("last4", tableItems[1]);
		assertText("last6", tableItems[2]);

		new SWTBotMenu(menuItem).click();

		display.syncExec(new Runnable() {
			public void run() {
				treeItems[0] = menuExample.getTreeItems();
			}
		});

		assertEquals(3, tableItems.length);
		assertText("last6", tableItems[0]);
		assertText("last4", tableItems[1]);
		assertText("last2", tableItems[2]);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
	}
}
