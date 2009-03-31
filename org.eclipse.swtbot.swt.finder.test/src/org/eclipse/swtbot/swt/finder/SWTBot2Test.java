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
package org.eclipse.swtbot.swt.finder;

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertEnabled;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertNotEnabled;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertSameWidget;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.Traverse;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBot2Test extends AbstractSWTTestCase {

	private SWTBot	bot;

	@Test
	public void findsTextBox() throws Exception {
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

	@Test
	public void isEnabled() throws Exception {
		assertEnabled(bot.radio("Left"));
		assertEnabled(bot.button("Clear"));

		assertNotEnabled(bot.radio("Up"));
		assertNotEnabled(bot.radio("Down"));
	}

	@Test
	public void getsActiveControl() throws Exception {
		bot.button("Two").setFocus();
		assertFalse(bot.button("One").isActive());
		assertTrue(bot.button("Two").isActive());

		assertSameWidget(bot.button("Two").widget, bot.getFocusedWidget());
	}

	@Test
	public void tabKeyTraversalSetsFocusOnTheNextControlAndSendsTraverseEvents() throws Exception {
		bot.checkBox("Listen").select();
		bot.button("Clear").click();
		bot.button("One").setFocus();
		assertTrue(bot.button("One").isActive());
		bot.button("One").traverse(Traverse.TAB_NEXT);

		SWTBotText textInGroup = bot.textInGroup("Listeners");

		assertTextContains("Traverse [31]: TraverseEvent{Button {One} ", textInGroup);
		assertTextContains("data=null character='\\0' keyCode=0 stateMask=0 doit=true detail=16}", textInGroup);
		assertTextContains("FocusOut [16]: FocusEvent{Button {One} time=", textInGroup);
		assertTextContains("FocusIn [15]: FocusEvent{Button {Two} time=", textInGroup);
		assertTrue(bot.button("Two").isActive());
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		bot.checkBox("Listen").deselect();
		bot.button("Clear").click();
	}

}
