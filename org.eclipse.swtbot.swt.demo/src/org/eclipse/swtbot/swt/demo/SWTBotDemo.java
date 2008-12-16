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
 * @version $Id: SWTBotDemo.java 1193 2008-12-02 07:22:51Z kpadegaonkar $
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
					mainClass.getMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { args });
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
		bot.tabItem("Combo").activate();
		bot.checkBox("Listen").click();
		SWTBotCombo comboBox = bot.comboBox("Line 3");
		bot.button("Clear").click();
		comboBox.setSelection("Line 7");
		bot.checkBox("Listen").click();
		bot.tabItem("Button").activate();
	}

	/**
	 * 
	 */
	private void playWithShells() throws Exception {
		bot.tabItem("Shell").activate();

		bot.checkBox("SWT.NO_TRIM").click();
		createAndCloseShell();

		bot.checkBox("SWT.TITLE").click();
		createAndCloseShell();

		bot.checkBox("SWT.RESIZE").click();
		createAndCloseShell();

		bot.checkBox("Image").click();
		bot.checkBox("Background Image").click();
		createAndCloseShell();

	}

	/**
	 * 
	 */
	private void createAndCloseShell() throws Exception {
		bot.button("Create Shell").click();
		bot.shell("Title:0").activate();
		bot.button("Close").click();
		bot.shell("SWT Controls").activate();
		bot.button("Close All Shells").click();
	}

	/**
	 * 
	 */
	private void playWithTabs() throws Exception {
		bot.tabItem("TabFolder").activate();
		bot.tabItem("Tab 2").activate();
		bot.tabItem("Tab 1").activate();
		bot.tabItem("Tab 0").activate();
		bot.tabItem("Button").activate();
	}

	/**
	 * 
	 */
	private void playWithListeners() throws Exception {
		bot.button("Select Listeners").click();
		SWTBotShell shell = bot.shell("Select Listeners");
		shell.activate();
		bot.button("Deselect All").click();
		bot.button("Select All").click();
		shell.close();
		bot.checkBox("Listen").click();
		bot.button("One").click();
		bot.checkBox("Listen").click();
		bot.button("Clear").click();
	}

	/**
	 * @param bot
	 */
	private void playWithRadioControls() throws Exception {
		bot.radio("SWT.PUSH").click();
		bot.radio("SWT.CHECK").click();
		bot.radio("SWT.RADIO").click();
		bot.radio("SWT.TOGGLE").click();
	}

	/**
	 * @param bot
	 */
	private void playWithEnableAndVisibliity() throws Exception {
		bot.checkBox("Enabled").click();
		bot.checkBox("Visible").click();
		bot.checkBox("Visible").click();
		bot.checkBox("Enabled").click();
	}

	/**
	 * @param bot
	 */
	private void playWithFillControls() throws Exception {
		bot.checkBox("Horizontal Fill").click();
		bot.checkBox("Vertical Fill").click();
		bot.checkBox("Horizontal Fill").click();
		bot.checkBox("Vertical Fill").click();
	}

}
