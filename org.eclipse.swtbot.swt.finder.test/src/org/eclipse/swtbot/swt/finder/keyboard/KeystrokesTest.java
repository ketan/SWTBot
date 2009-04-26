package org.eclipse.swtbot.swt.finder.keyboard;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.junit.Test;

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

	private KeyStroke shift() {
		return KeyStroke.getInstance(SWT.SHIFT, 0);
	}

	@Test
	public void getsKeyStrokeForSmallCharacters() throws Exception {
		// c should generate C
		assertEquals(key('C'), Keystrokes.create('c')[0]);
		// t should generate T
		assertEquals(key('T'), Keystrokes.create('t')[0]);
	}

	private KeyStroke key(char c) {
		return KeyStroke.getInstance(c);
	}

	@Test
	public void getsKeyStrokeForSpecialCharacters() throws Exception {
		// ! should generate SHIFT+1
		assertEquals(shift(), Keystrokes.create('!')[0]);
		assertEquals(KeyStroke.getInstance('1'), Keystrokes.create('!')[1]);

		// : should generate SHIFT+;
		assertEquals(shift(), Keystrokes.create(':')[0]);
		assertEquals(KeyStroke.getInstance(';'), Keystrokes.create(':')[1]);
	}

	@Test
	public void getsKeystrokesForCombinationOfCharacters() throws Exception {
		KeyStroke[] keys = Keystrokes.create("h$|P");
		assertEquals(key('H'), keys[0]);
		assertEquals(shift(), keys[1]);
		assertEquals(key('4'), keys[2]);
		assertEquals(shift(), keys[3]);
		assertEquals(key('\\'), keys[4]);
		assertEquals(shift(), keys[5]);
		assertEquals(key('P'), keys[6]);
	}
}
