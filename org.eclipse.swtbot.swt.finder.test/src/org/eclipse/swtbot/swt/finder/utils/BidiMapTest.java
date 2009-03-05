/*******************************************************************************
 * Copyright (c) 2008-2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class BidiMapTest {

	BidiMap<String, Integer>	map	= new BidiMap<String, Integer>();

	@Test
	public void canFindObjectBasedOnValue() throws Exception {
		map.put("foo", 10);
		map.put("bar", 20);

		assertEquals("foo", map.getKey(10));
		assertEquals("bar", map.getKey(20));
	}

	@Test
	public void canFindObjectBasedOnKey() throws Exception {
		map.put("foo", 10);
		map.put("bar", 20);

		assertEquals(20, (int) map.getValue("bar"));
		assertEquals(10, (int) map.getValue("foo"));
	}

	@Test
	public void canIterateThroughAllElements() throws Exception {
		map.put("foo", 10);
		map.put("bar", 20);

		for (Entry<String, Integer> entry : map) {
			assertTrue(
					(entry.getKey().equals("foo") && entry.getValue().equals(10))
					||
					(entry.getKey().equals("bar") && entry.getValue().equals(20))
		);
		}
	}
}
