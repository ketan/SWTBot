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
package org.eclipse.swtbot.eclipse.spy;



import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PlayBackThread extends Thread {

	private static final Logger	log	= Logger.getLogger(PlayBackThread.class);
	/** the e */
	private final Event			e;

	public PlayBackThread(Event e) {
		super("PlayBackThread"); //$NON-NLS-1$
		this.e = e;
	}

	public void run() {
		log.debug("running..."); //$NON-NLS-1$

		try {
			perform();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @throws WidgetNotFoundException
	 */
	private void perform() throws Exception {
		Display display = e.display;
		SWTEclipseBot bot = new SWTEclipseBot();

		createJavaProject(bot);
		createJavaClass(bot);
		Thread.sleep(1000);
		SWTBotEclipseEditor editor = bot.editor("HelloWorld.java"); //$NON-NLS-1$

		Thread.sleep(1000);
		editor.notifyKeyboardEvent(SWT.CTRL, '.');
		editor.quickfix("Add unimplemented methods"); //$NON-NLS-1$

		editor.navigateTo(7, 0);
		editor.autoCompleteProposal("sys", "sysout - print to standard out"); //$NON-NLS-1$ //$NON-NLS-2$

		editor.typeText("\"Hello World\""); //$NON-NLS-1$

		editor.navigateTo(3, 0);
		editor.autoCompleteProposal("main", "main - main method"); //$NON-NLS-1$ //$NON-NLS-2$

		editor.typeText("new Thread (new HelloWorld ());"); //$NON-NLS-1$
		if (true)
			return;
		editor.notifyKeyboardEvent(SWT.CTRL, '2');
		editor.notifyKeyboardEvent(SWT.NONE, 'L');
		editor.notifyKeyboardEvent(SWT.NONE, '\n');

		editor.typeText("\n"); //$NON-NLS-1$
		editor.typeText("thread.start();\n"); //$NON-NLS-1$
		editor.typeText("thread.join();"); //$NON-NLS-1$
		editor.quickfix("Add throws declaration"); //$NON-NLS-1$
		editor.notifyKeyboardEvent(SWT.NONE, (char) 27);
		editor.notifyKeyboardEvent(SWT.NONE, '\n');

		editor.notifyKeyboardEvent(SWT.CTRL, 's');

		editor.notifyKeyboardEvent(SWT.ALT | SWT.SHIFT, 'x');
		editor.notifyKeyboardEvent(SWT.NONE, 'j');

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param bot
	 */
	private void createJavaClass(SWTEclipseBot bot) throws Exception {
		bot.menu("File").menu("New").menu("Class").click(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		bot.shell("New Java Class").activate(); //$NON-NLS-1$
		bot.textWithLabel("Name:").setText("HelloWorld"); //$NON-NLS-1$ //$NON-NLS-2$
		bot.textWithLabel("Package:").setText("com.helloworld"); //$NON-NLS-1$ //$NON-NLS-2$
		implementsInterface(bot, "java.lang.Runnable"); //$NON-NLS-1$
		bot.checkBox("Inherited abstract methods").click(); //$NON-NLS-1$
		bot.button("Finish").click(); //$NON-NLS-1$
	}

	/**
	 * @param bot
	 */
	private void implementsInterface(final SWTEclipseBot bot, String interfaceClass) throws Exception {
		bot.button("Add...").click(); //$NON-NLS-1$
		bot.shell("Implemented Interfaces Selection").activate(); //$NON-NLS-1$
		bot.textWithLabel("Choose interfaces:").setText(interfaceClass); //$NON-NLS-1$
		bot.waitUntil(Conditions.tableHasRows(bot.table(), 1));
		bot.button("OK").click(); //$NON-NLS-1$
		bot.shell("New Java Class").activate(); //$NON-NLS-1$
	}

	/**
	 * @param bot
	 */
	private void createJavaProject(SWTBot bot) throws Exception {
		bot.menu("File").menu("New").menu("Java Project").click(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		bot.shell("New Java Project").activate(); //$NON-NLS-1$
		bot.textWithLabel("Project name:").setText("MyProject"); //$NON-NLS-1$ //$NON-NLS-2$
		bot.button("Finish").click(); //$NON-NLS-1$
	}
}
