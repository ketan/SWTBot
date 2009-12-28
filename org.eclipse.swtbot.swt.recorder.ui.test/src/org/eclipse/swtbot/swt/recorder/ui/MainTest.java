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

}
