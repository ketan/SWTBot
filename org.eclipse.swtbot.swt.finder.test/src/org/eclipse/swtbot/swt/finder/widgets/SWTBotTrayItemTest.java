/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Toby Weston - initial API and implementation (Bug 259860)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.junit.Test;

public class SWTBotTrayItemTest extends AbstractSWTTestCase {

	private Menu	tray_item_menu;
	private Tray	tray;

	public void setUp() throws Exception {
		super.setUp();
		tray = getSystemTray();
		createTrayItems();
	}

	public void tearDown() throws Exception {
		destroyTrayItems();
		super.tearDown();
	}

	@Test
	public void firstTrayItemIsReturned() {
		assertEquals("Tray Item 1", bot.trayItem().getText());
	}

	@Test
	public void indexedTrayItemIsReturned() {
		assertEquals("Tray Item 1", bot.trayItem(0).getText());
		assertEquals("Tray Item 2", bot.trayItem(1).getText());
	}

	@Test
	public void trayItemWithTooltipIsReturned() {
		assertEquals("Tray Item 1", bot.trayItemWithTooltip("Tray Item 1").getText());
		assertEquals("Tray Item 3", bot.trayItemWithTooltip("Tray Item 3").getText());
		assertEquals("Tray Item 2", bot.trayItemWithTooltip("Tray Item 2").getText());
	}

	@Test
	public void listOfTrayItemsIsReturned() {
		assertEquals(3, bot.trayItems().size());
	}

	@Test
	public void menuCanBeAccessedOnTrayItem() {
		bot.trayItem(2).menu("Menu").click();
		assertTrue(menuSelected);
	}

	public void createTrayItems() throws SystemTrayNotSupportedException {
		createTrayItem(tray, "Tray Item 1", SWT.ICON_INFORMATION);
		createTrayItem(tray, "Tray Item 2", SWT.ICON_WARNING);
		createTrayItem(tray, "Tray Item 3", SWT.ICON_ERROR);
	}

	private final Listener	popupMenu				= new Listener() {
														public void handleEvent(Event event) {
															tray_item_menu = new Menu(controlShell, SWT.POP_UP);
															MenuItem item = new MenuItem(tray_item_menu, SWT.PUSH);
															item.setText("Menu");
															item.addListener(SWT.Selection, menuSelectedListener);
															tray_item_menu.setDefaultItem(item);
															tray_item_menu.setVisible(true);
														}
													};

	private boolean			menuSelected			= false;

	private final Listener	menuSelectedListener	= new Listener() {
														public void handleEvent(Event event) {
															SWTBotTrayItemTest.this.menuSelected = true;
														}
													};

	private Tray getSystemTray() {
		Tray tray = UIThreadRunnable.syncExec(new WidgetResult<Tray>() {
			public Tray run() {
				return display.getSystemTray();
			}
		});
		if (tray == null) {
			throw new SystemTrayNotSupportedException();
		}
		return tray;
	}

	private TrayItem createTrayItem(final Tray tray, final String text, final int icon) {
		return UIThreadRunnable.syncExec(new WidgetResult<TrayItem>() {
			public TrayItem run() {
				TrayItem trayItem = new TrayItem(tray, SWT.NONE);
				trayItem.setText(text);
				trayItem.setToolTipText(text);
				trayItem.setImage(display.getSystemImage(icon));
				if (text == "Tray Item 3") {
					trayItem.addListener(SWT.MenuDetect, popupMenu);
				}
				return trayItem;
			}
		});
	}

	private void destroyTrayItems() {
		final Tray tray = getSystemTray();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				tray.dispose();
			}
		});

	}

	public class SystemTrayNotSupportedException extends RuntimeException {
		private static final long	serialVersionUID	= 1053812131263043591L;
	}
}
