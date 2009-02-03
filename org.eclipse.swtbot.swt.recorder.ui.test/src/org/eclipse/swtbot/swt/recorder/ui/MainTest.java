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
package org.eclipse.swtbot.swt.recorder.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MainTest {

	public static class MyMain {
		private static String[]	args;

		public static void main(String[] args) {
			MyMain.args = args;
		}
	}

	@Test
	public void noArgumentsThrowsException() throws Exception {
		try {
			Main.main(new String[] {});
			fail("expecting IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("Usage: java org.eclipse.swtbot.swt.recorder.ui.Main com.your.MainClass [arguments to your main...]", e
					.getMessage());
		}
	}

	@Test
	public void invokesMain() throws Exception {
		try {
			new Main(new String[] { "org.eclipse.swtbot.swt.recorder.ui.MainTest$MyMain", "my", "args" }).start();
		} catch (IllegalStateException e) {
			assertEquals("Could not find a display", e.getMessage());
			assertEquals(2, MyMain.args.length);
			assertEquals("my", MyMain.args[0]);
			assertEquals("args", MyMain.args[1]);
		}
	}

}
