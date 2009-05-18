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
package org.eclipse.swtbot.swt.finder.keyboard;

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeyboardFactoryTest extends AbstractSWTTestCase {

	@Test
	public void createsKeyboardForAWTKeyboardStrategy() throws Exception {
		assertEquals(AWTKeyboardStrategy.class, new KeyboardFactory(AWTKeyboardStrategy.class.getName()).strategyClass);
	}

	@Test
	public void createsKeyboardForSWTKeyboardStrategy() throws Exception {
		assertEquals(SWTKeyboardStrategy.class, new KeyboardFactory(SWTKeyboardStrategy.class.getName()).strategyClass);
	}

	@Test
	public void createsKeyboardForMockKeyboardStrategy() throws Exception {
		assertEquals(MockKeyboardStrategy.class, new KeyboardFactory(MockKeyboardStrategy.class.getName()).strategyClass);
	}

}
