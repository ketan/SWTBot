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
package org.eclipse.swtbot.swt.finder;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.WidgetNotFoundException;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBot2Test extends AbstractSWTTestCase {

	private SWTBot	bot;

	public void testFindsTextBox() throws Exception {
		UIThreadRunnable.syncExec(display, new VoidResult() {
			public void run() {
				Shell shell2 = new Shell(display);
				shell2.setText("Hello Shell");
				shell2.setLayout(new FillLayout());
				new Label(shell2, SWT.NONE).setText("My TextBox");
				new Text(shell2, SWT.NONE).setText("Hello World");
				shell2.open();
			}
		});

		try {
			bot.shell("SWT Controls").activate();
			bot.shell("Hello Shell").activate();
			SWTBotText text = bot.textWithLabel("My TextBox");
			assertEquals("Hello World", text.getText());
			text.setText("good bye world");
			assertEquals("good bye world", text.getText());
		} finally {
			// a hacked tear down
			UIThreadRunnable.syncExec(display, new VoidResult() {
				public void run() {
					try {
						bot.shell("Hello Shell").widget.dispose();
					} catch (WidgetNotFoundException e) {
						// do nothing
					}
				}
			});
		}
	}

	public void testIsEnabled() throws Exception {
		assertEnabled(bot.radio("Left"));
		assertEnabled(bot.button("Clear"));

		assertNotEnabled(bot.radio("Up"));
		assertNotEnabled(bot.radio("Down"));
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
	}

}
