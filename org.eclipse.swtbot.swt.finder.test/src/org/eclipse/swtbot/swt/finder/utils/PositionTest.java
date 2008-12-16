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
package org.eclipse.swtbot.swt.finder.utils;

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PositionTest extends TestCase {

	public void testEqualsAndHashCode() throws Exception {
		assertEquals(position(), position());
		assertEquals(position().hashCode(), position().hashCode());

		Position position = position();
		assertEquals(position, position);
		assertEquals(position.hashCode(), position.hashCode());

		assertFalse(position.equals(""));
	}

	private Position position() {
		return new Position(1, 10);
	}
}
