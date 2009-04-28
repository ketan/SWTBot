/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeyboardTest extends AbstractSWTTestCase {

	private SWTBotStyledText styledText;
	private SWTBotText listeners;
	private Keyboard keyboard;

	@Test
	public void canTypeShiftKey() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.SHIFT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=332938450 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=332938540 data=null character='\\0' keyCode=131072 stateMask=131072 doit=true}");
	}

	@Test
	public void canTypeCTRLKey() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.CTRL);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334095974 data=null character='\\0' keyCode=262144 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334096084 data=null character='\\0' keyCode=262144 stateMask=262144 doit=true}");
	}

	@Test
	public void canTypeAltKey() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.ALT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337927063 data=null character='\\0' keyCode=65536 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337927163 data=null character='\\0' keyCode=65536 stateMask=65536 doit=true}");
	}

	@Test
	public void canType_CTRL_SHIFT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.CTRL, Keystrokes.SHIFT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334163451 data=null character='\\0' keyCode=262144 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334163611 data=null character='\\0' keyCode=131072 stateMask=262144 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334163782 data=null character='\\0' keyCode=131072 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334163832 data=null character='\\0' keyCode=262144 stateMask=262144 doit=true}");
	}

	@Test
	public void canType_SHIFT_CTRL_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.SHIFT, Keystrokes.CTRL);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334173686 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334173996 data=null character='\\0' keyCode=262144 stateMask=131072 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334174277 data=null character='\\0' keyCode=262144 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334174347 data=null character='\\0' keyCode=131072 stateMask=131072 doit=true}");
	}

	@Test
	public void canType_CTRL_ALT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.CTRL, Keystrokes.ALT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334364901 data=null character='\\0' keyCode=262144 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334365191 data=null character='\\0' keyCode=65536 stateMask=262144 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334365331 data=null character='\\0' keyCode=65536 stateMask=327680 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334365492 data=null character='\\0' keyCode=262144 stateMask=262144 doit=true}");
	}

	@Test
	public void canType_ALT_CTRL_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.ALT, Keystrokes.CTRL);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334374164 data=null character='\\0' keyCode=65536 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334374324 data=null character='\\0' keyCode=262144 stateMask=65536 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334374475 data=null character='\\0' keyCode=262144 stateMask=327680 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334374495 data=null character='\\0' keyCode=65536 stateMask=65536 doit=true}");
	}

	@Test
	public void canType_SHIFT_ALT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.SHIFT, Keystrokes.ALT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334403126 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334403236 data=null character='\\0' keyCode=65536 stateMask=131072 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334403366 data=null character='\\0' keyCode=65536 stateMask=196608 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334403426 data=null character='\\0' keyCode=131072 stateMask=131072 doit=true}");
	}

	@Test
	public void canType_ALT_SHIFT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.ALT, Keystrokes.SHIFT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334417126 data=null character='\\0' keyCode=65536 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=334417456 data=null character='\\0' keyCode=131072 stateMask=65536 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334417526 data=null character='\\0' keyCode=131072 stateMask=196608 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=334417577 data=null character='\\0' keyCode=65536 stateMask=65536 doit=true}");
	}

	@Test
	public void canType_SHIFT_CTRL_ALT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.SHIFT, Keystrokes.CTRL, Keystrokes.ALT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=336998217 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=336998548 data=null character='\\0' keyCode=262144 stateMask=131072 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=336998858 data=null character='\\0' keyCode=65536 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=336998938 data=null character='\\0' keyCode=65536 stateMask=458752 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337000000 data=null character='\\0' keyCode=262144 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337000330 data=null character='\\0' keyCode=131072 stateMask=131072 doit=true}");
	}

	@Test
	public void canType_SHIFT_ALT_CTRL_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.SHIFT, Keystrokes.ALT, Keystrokes.CTRL);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337024085 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337024595 data=null character='\\0' keyCode=65536 stateMask=131072 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337025316 data=null character='\\0' keyCode=262144 stateMask=196608 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337026738 data=null character='\\0' keyCode=262144 stateMask=458752 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337027700 data=null character='\\0' keyCode=65536 stateMask=196608 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337028090 data=null character='\\0' keyCode=131072 stateMask=131072 doit=true}");
	}

	@Test
	public void canType_CTRL_SHIFT_ALT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.CTRL, Keystrokes.SHIFT, Keystrokes.ALT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337073666 data=null character='\\0' keyCode=262144 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337074086 data=null character='\\0' keyCode=131072 stateMask=262144 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337074527 data=null character='\\0' keyCode=65536 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337074928 data=null character='\\0' keyCode=65536 stateMask=458752 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337075999 data=null character='\\0' keyCode=131072 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337076370 data=null character='\\0' keyCode=262144 stateMask=262144 doit=true}");
	}

	@Test
	public void canType_CTRL_ALT_SHIFT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.CTRL, Keystrokes.ALT, Keystrokes.SHIFT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337084111 data=null character='\\0' keyCode=262144 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337084592 data=null character='\\0' keyCode=65536 stateMask=262144 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337085162 data=null character='\\0' keyCode=131072 stateMask=327680 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337085753 data=null character='\\0' keyCode=131072 stateMask=458752 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337086184 data=null character='\\0' keyCode=65536 stateMask=327680 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337086584 data=null character='\\0' keyCode=262144 stateMask=262144 doit=true}");
	}

	@Test
	public void canType_ALT_CTRL_SHIFT_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.ALT, Keystrokes.CTRL, Keystrokes.SHIFT);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337516292 data=null character='\\0' keyCode=65536 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337516753 data=null character='\\0' keyCode=262144 stateMask=65536 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337517113 data=null character='\\0' keyCode=131072 stateMask=327680 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337517985 data=null character='\\0' keyCode=131072 stateMask=458752 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337518335 data=null character='\\0' keyCode=262144 stateMask=327680 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337518686 data=null character='\\0' keyCode=65536 stateMask=65536 doit=true}");
	}

	@Test
	public void canType_ALT_SHIFT_CTRL_Keys() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(Keystrokes.ALT, Keystrokes.SHIFT, Keystrokes.CTRL);
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337527028 data=null character='\\0' keyCode=65536 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337527649 data=null character='\\0' keyCode=131072 stateMask=65536 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=337528209 data=null character='\\0' keyCode=262144 stateMask=196608 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337531534 data=null character='\\0' keyCode=262144 stateMask=458752 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337532275 data=null character='\\0' keyCode=131072 stateMask=196608 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=337532666 data=null character='\\0' keyCode=65536 stateMask=65536 doit=true}");
	}
	
	@Test
	public void canTypeCTRL_SHIFT_T() throws Exception {
		styledText.setFocus();
		keyboard.pressShortcut(keys(Keystrokes.CTRL, Keystrokes.SHIFT, Keystrokes.create('t')));
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=356391804 data=null character='\\0' keyCode=262144 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=356392194 data=null character='\\0' keyCode=131072 stateMask=262144 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=356393156 data=null character='' keyCode=116 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=356392495 data=null character='' keyCode=116 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=356393987 data=null character='\\0' keyCode=131072 stateMask=393216 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=356394307 data=null character='\\0' keyCode=262144 stateMask=262144 doit=true}");
	}
	
	@Test
	public void canTypeSmallCharacters() throws Exception {
		styledText.setFocus();
		keyboard.pressKeys("ab");
		assertEventMatches(listeners.getText(), "Verify [25]: VerifyEvent{StyledText {} time=358259489 data=null character='\\0' keyCode=0 stateMask=0 doit=true start=0 end=0 text=a}");
		assertEventMatches(listeners.getText(), "Modify [24]: ModifyEvent{StyledText {} time=358259489 data=null}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=358259489 data=null character='a' keyCode=97 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=358259560 data=null character='a' keyCode=97 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "Verify [25]: VerifyEvent{StyledText {} time=358264617 data=null character='\\0' keyCode=0 stateMask=0 doit=true start=1 end=1 text=b}");
		assertEventMatches(listeners.getText(), "Modify [24]: ModifyEvent{StyledText {} time=358264617 data=null}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=358264617 data=null character='b' keyCode=98 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=358259810 data=null character='b' keyCode=98 stateMask=0 doit=true}");
	}
	
	@Test
	public void canTypeCapitalCharacters() throws Exception {
		styledText.setFocus();
		keyboard.pressKeys("A");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=359800165 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=359800165 data=null character='\\0' keyCode=131072 stateMask=0 doit=true}");
		assertEventMatches(listeners.getText(), "Verify [25]: VerifyEvent{StyledText {} time=359800285 data=null character='\\0' keyCode=0 stateMask=0 doit=true start=0 end=0 text=A}");
		assertEventMatches(listeners.getText(), "Modify [24]: ModifyEvent{StyledText {} time=359800285 data=null}");
		assertEventMatches(listeners.getText(), "KeyDown [1]: KeyEvent{StyledText {} time=359800285 data=null character='A' keyCode=97 stateMask=131072 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=359800405 data=null character='A' keyCode=97 stateMask=131072 doit=true}");
		assertEventMatches(listeners.getText(), "KeyUp [2]: KeyEvent{StyledText {} time=359800485 data=null character='\\0' keyCode=131072 stateMask=131072 doit=true}");
	}

	private List<KeyStroke> keys(KeyStroke ctrl, KeyStroke shift, KeyStroke[] keyStrokes) {
		List<KeyStroke> keys = new ArrayList<KeyStroke>();
		keys.add(ctrl);
		keys.add(shift);
		keys.addAll(Arrays.asList(keyStrokes));
		return keys;
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		keyboard = new Keyboard(display);
		bot.shell("SWT Custom Controls").activate();
		bot.tabItem("StyledText").activate();
		bot.checkBox("Horizontal Fill").select();
		bot.checkBox("Vertical Fill").select();
		bot.checkBox("Listen").deselect();
		bot.checkBox("Listen").select();
		styledText = bot.styledTextInGroup("StyledText");
		styledText.setText("");
		bot.button("Clear").click();
		listeners = bot.textInGroup("Listeners");
	}

}
