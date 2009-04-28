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

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeystrokesTest {

	@Test
	public void getsKeyStrokeForCapitalCharacters() throws Exception {
		// C should generate SHIFT+C
		assertEquals(shift(), Keystrokes.create('C')[0]);
		assertEquals(key('C'), Keystrokes.create('C')[1]);
		// T should generate SHIFT+T
		assertEquals(shift(), Keystrokes.create('T')[0]);
		assertEquals(key('T'), Keystrokes.create('T')[1]);
	}

	@Test
	public void getsKeyStrokeForSmallCharacters() throws Exception {
		// c should generate C
		assertEquals(key('C'), Keystrokes.create('c')[0]);
		// t should generate T
		assertEquals(key('T'), Keystrokes.create('t')[0]);
	}

	@Test
	public void getsKeyStrokeForSpecialCharacters() throws Exception {
		// ! should generate SHIFT+1
		assertEquals(shift(), Keystrokes.create('!')[0]);
		assertEquals(key('1'), Keystrokes.create('!')[1]);

		// : should generate SHIFT+;
		assertEquals(shift(), Keystrokes.create(':')[0]);
		assertEquals(key(';'), Keystrokes.create(':')[1]);
	}

	@Test
	public void getsKeystrokesForModificationKeys() throws Exception {
		KeyStroke[] keys = Keystrokes.toKeys(SWT.CTRL | SWT.ALT | SWT.SHIFT | SWT.COMMAND, '\0');
		assertEquals(4, keys.length);
		assertEquals(alt(), keys[0]);
		assertEquals(cmd(), keys[1]);
		assertEquals(ctrl(), keys[2]);
		assertEquals(shift(), keys[3]);
	}

	@Test
	public void getsKeystrokesForModificationKey() throws Exception {
		KeyStroke[] keys = Keystrokes.toKeys(SWT.CTRL, 't');
		assertEquals(2, keys.length);
		assertEquals(ctrl(), keys[0]);
		assertEquals(key('T'), keys[1]);
	}

	@Test
	public void getsKeystrokesForModificationKeyWithShiftAndSmallCharacter() throws Exception {
		KeyStroke[] keys = Keystrokes.toKeys(SWT.CTRL | SWT.SHIFT, 't');
		assertEquals(3, keys.length);
		assertEquals(ctrl(), keys[0]);
		assertEquals(shift(), keys[1]);
		assertEquals(key('T'), keys[2]);
	}

	@Test
	public void getsKeystrokesForModificationKeyWithShiftAndCapitalCharacter() throws Exception {
		KeyStroke[] keys = Keystrokes.toKeys(SWT.CTRL | SWT.SHIFT, 'T');
		assertEquals(3, keys.length);
		assertEquals(ctrl(), keys[0]);
		assertEquals(shift(), keys[1]);
		assertEquals(key('T'), keys[2]);
	}

	@Test
	public void getsKeystrokesForModificationKeyForCapitalCharacter() throws Exception {
		KeyStroke[] keys = Keystrokes.toKeys(SWT.CTRL, 'T');
		assertEquals(3, keys.length);
		assertEquals(ctrl(), keys[0]);
		assertEquals(shift(), keys[1]);
		assertEquals(key('T'), keys[2]);
	}

	private KeyStroke shift() {
		return KeyStroke.getInstance(SWT.SHIFT, 0);
	}

	private KeyStroke key(char c) {
		return KeyStroke.getInstance(c);
	}

	private KeyStroke ctrl() {
		return KeyStroke.getInstance(SWT.CTRL, 0);
	}

	private KeyStroke alt() {
		return KeyStroke.getInstance(SWT.ALT, 0);
	}

	private KeyStroke cmd() {
		return KeyStroke.getInstance(SWT.COMMAND, 0);
	}
}
