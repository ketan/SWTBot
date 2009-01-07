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
package org.eclipse.swtbot.swt.demo;

import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotDemo {

	private final Class	mainClass;
	private SWTBot		bot;

	/**
	 * @param mainClass
	 */
	public SWTBotDemo(Class mainClass) {
		this.mainClass = mainClass;
	}

	public static void main(String[] args) throws Exception {
		new SWTBotDemo(ControlExample.class).start(args);
	}

	private void start(final String[] args) throws Exception {
		Thread applicationThread = new Thread() {
			public void run() {
				try {
					mainClass.getMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { args }); //$NON-NLS-1$
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		applicationThread.start();
		perform(waitForDisplayToAppear(5000));
		Thread.sleep(1000);
		System.exit(0);
		applicationThread.join();
	}

	private Display waitForDisplayToAppear(long timeOut) {
		long endTime = System.currentTimeMillis() + timeOut;
		Display display;
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		while (System.currentTimeMillis() < endTime)
			for (int i = 0; i < threads.length; i++) {
				Thread thread = threads[i];
				display = Display.findDisplay(thread);
				if (display != null)
					return display;
			}
		return null;
	}

	private void perform(Display display) throws InterruptedException, Exception {
		bot = new SWTBot();

		playWithRadioControls();
		playWithListeners();
		playWithFillControls();
		playWithEnableAndVisibliity();
		playWithTabs();
		playWithShells();
		playWithCombos();
	}

	/**
	 * 
	 */
	private void playWithCombos() throws Exception {
		bot.tabItem("Combo").activate(); //$NON-NLS-1$
		bot.checkBox("Listen").click(); //$NON-NLS-1$
		SWTBotCombo comboBox = bot.comboBox("Line 3"); //$NON-NLS-1$
		bot.button("Clear").click(); //$NON-NLS-1$
		comboBox.setSelection("Line 7"); //$NON-NLS-1$
		bot.checkBox("Listen").click(); //$NON-NLS-1$
		bot.tabItem("Button").activate(); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	private void playWithShells() throws Exception {
		bot.tabItem("Shell").activate(); //$NON-NLS-1$

		bot.checkBox("SWT.NO_TRIM").click(); //$NON-NLS-1$
		createAndCloseShell();

		bot.checkBox("SWT.TITLE").click(); //$NON-NLS-1$
		createAndCloseShell();

		bot.checkBox("SWT.RESIZE").click(); //$NON-NLS-1$
		createAndCloseShell();

		bot.checkBox("Image").click(); //$NON-NLS-1$
		bot.checkBox("Background Image").click(); //$NON-NLS-1$
		createAndCloseShell();

	}

	/**
	 * 
	 */
	private void createAndCloseShell() throws Exception {
		bot.button("Create Shell").click(); //$NON-NLS-1$
		bot.shell("Title:0").activate(); //$NON-NLS-1$
		bot.button("Close").click(); //$NON-NLS-1$
		bot.shell("SWT Controls").activate(); //$NON-NLS-1$
		bot.button("Close All Shells").click(); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	private void playWithTabs() throws Exception {
		bot.tabItem("TabFolder").activate(); //$NON-NLS-1$
		bot.tabItem("Tab 2").activate(); //$NON-NLS-1$
		bot.tabItem("Tab 1").activate(); //$NON-NLS-1$
		bot.tabItem("Tab 0").activate(); //$NON-NLS-1$
		bot.tabItem("Button").activate(); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	private void playWithListeners() throws Exception {
		bot.button("Select Listeners").click(); //$NON-NLS-1$
		SWTBotShell shell = bot.shell("Select Listeners"); //$NON-NLS-1$
		shell.activate();
		bot.button("Deselect All").click(); //$NON-NLS-1$
		bot.button("Select All").click(); //$NON-NLS-1$
		shell.close();
		bot.checkBox("Listen").click(); //$NON-NLS-1$
		bot.button("One").click(); //$NON-NLS-1$
		bot.checkBox("Listen").click(); //$NON-NLS-1$
		bot.button("Clear").click(); //$NON-NLS-1$
	}

	/**
	 * @param bot
	 */
	private void playWithRadioControls() throws Exception {
		bot.radio("SWT.PUSH").click(); //$NON-NLS-1$
		bot.radio("SWT.CHECK").click(); //$NON-NLS-1$
		bot.radio("SWT.RADIO").click(); //$NON-NLS-1$
		bot.radio("SWT.TOGGLE").click(); //$NON-NLS-1$
	}

	/**
	 * @param bot
	 */
	private void playWithEnableAndVisibliity() throws Exception {
		bot.checkBox("Enabled").click(); //$NON-NLS-1$
		bot.checkBox("Visible").click(); //$NON-NLS-1$
		bot.checkBox("Visible").click(); //$NON-NLS-1$
		bot.checkBox("Enabled").click(); //$NON-NLS-1$
	}

	/**
	 * @param bot
	 */
	private void playWithFillControls() throws Exception {
		bot.checkBox("Horizontal Fill").click(); //$NON-NLS-1$
		bot.checkBox("Vertical Fill").click(); //$NON-NLS-1$
		bot.checkBox("Horizontal Fill").click(); //$NON-NLS-1$
		bot.checkBox("Vertical Fill").click(); //$NON-NLS-1$
	}

}
