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
package org.eclipse.swtbot.swt.finder.collections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class OrderedSetTest {

	@Test
	public void addsItemsInOrder() throws Exception {
		OrderedSet<String> set = new OrderedSet<String>();
		set.add("foo");
		set.add("baz");
		set.add("bar");
		set.add("foo");

		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("foo");
		arrayList.add("baz");
		arrayList.add("bar");
		assertEquals(set, arrayList);

	}
}
