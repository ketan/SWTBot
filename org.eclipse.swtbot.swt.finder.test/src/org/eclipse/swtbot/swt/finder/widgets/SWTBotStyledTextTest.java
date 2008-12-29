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


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.utils.Position;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotStyledTextTest extends AbstractSWTTestCase {

	private SWTBot bot;
	private SWTBotStyledText styledText;

	public void testFindsStyledText() throws Exception {
		assertTextContains("The quick brown fox", styledText.widget);
	}

	public void testSelectsRange() throws Exception {
		styledText.setText("hello world\n" + "it is a very good day today\n" + "good bye world\n" + "it was nice to meet you");
		styledText.selectRange(1, 0, 27);
		assertEquals("it is a very good day today", styledText.getSelection());
	}

	public void testFindsTextStyle() throws Exception {
		setStyles();
		StyleRange range = styledText.getStyle(1, 2);
		assertEquals(SWT.BOLD, range.fontStyle);
		assertTrue(range.underline);
	}

	public void testFindsStylesRangeInARange() throws Exception {
		setStyles();
		StyleRange[] styles = styledText.getStyles(1, 0, 30);
		assertEquals(2, styles.length);
		assertTrue(styles[0].underline);
		assertEquals(SWT.BOLD, styles[0].fontStyle);
		assertEquals(SWT.ITALIC, styles[1].fontStyle);
	}

	/**
	 * @throws WidgetNotFoundException
	 */
	private void setStyles() throws WidgetNotFoundException {
		styledText.setText("hello world\n" + "it is a very good day today\n" + "good bye world\n" + "it was nice to meet you");
		styledText.selectRange(1, 0, 27);
		bot.buttonWithLabel("Bold").click();
		styledText.selectRange(1, 0, 27);
		bot.buttonWithLabel("Underline").click();
		styledText.selectRange(2, 1, 10);
		bot.buttonWithLabel("Italic").click();
	}

	public void testSetsText() throws Exception {
		styledText.setText("hello world");
		assertTextContains("hello world", styledText.widget);
	}

	public void testSends_CTRL_1_Notification() throws Exception {
		try {
			bot.checkBox("Listen").click();
			SWTBotStyledText styledText = bot.styledTextInGroup("StyledText");
			styledText.notifyKeyboardEvent(SWT.CTRL, '1');
			String text = bot.textInGroup("Listeners").getText();
			assertContains("KeyDown [1]: KeyEvent{StyledText {} ", text);
			assertContains("character='1' keyCode=0 stateMask=262144 doit=true", text);
			assertContains("KeyUp [2]: KeyEvent{StyledText {} ", text);

		} finally {
			// a hacked cleanup
			bot.checkBox("Listen").click();
		}
	}

	public void testNavigatesToAParticularLocation() throws Exception {
		styledText.setText("hello world\n" + "it is a very good day today\n" + "good bye world\n" + "it was nice to meet you");
		styledText.navigateTo(1, 17);
		assertEquals(new Position(1, 17), styledText.cursorPosition());
	}

	public void testTypesTextAtALocation() throws Exception {
		styledText.typeText(1, 0, "---typed text---\n");
		assertTextContains("---typed text---", styledText.widget);
	}


	public void testInsertsTextAtALocation() throws Exception {
		styledText.insertText(1, 0, "---inserted text---\n");
		assertTextContains("---inserted text---", styledText.widget);
	}

	// FIXME on windows
	public void _testTypesTextAtALocation() throws Exception {
		styledText.typeText(1, 0, "---\n\typed text\n---\n");
		assertTextContains("---\n\typed text\n---\n", styledText.widget);
	}

	public void testGetsTextOnCurrentLine() throws Exception {
		styledText.setText("hello world\n" + "it is a very good day today\n" + "good bye world\n" + "it was nice to meet you");
		styledText.navigateTo(1, 0);
		assertEquals("it is a very good day today", styledText.getTextOnCurrentLine());
	}

	public void testGetsBullet() throws Exception {

		styledText.setText("hello world\n" + "it is a very good day today\n" + "good bye world\n" + "it was nice to meet you");
		styledText.navigateTo(1, 0);
		assertFalse(styledText.hasBulletOnCurrentLine());

		display.syncExec(new Runnable() {
			public void run() {
				StyledText t = styledText.widget;
				StyleRange style = new StyleRange();
				style.metrics = new GlyphMetrics(0, 0, 1);
				t.setLineBullet(1, 1, new Bullet(style));
			}
		});

		assertTrue(styledText.hasBulletOnCurrentLine());
	}

	protected void setUp() throws Exception {
		super.setUp();
		bot = new  SWTBot();
		bot.shell("SWT Custom Controls").activate();
		bot.tabItem("StyledText").activate();
		styledText = bot.styledTextInGroup("StyledText");
		bot.checkBox("Horizontal Fill").select();
		bot.checkBox("Vertical Fill").select();
	}

	protected Shell getFocusShell() {
		return customControlShell;
	}
}
