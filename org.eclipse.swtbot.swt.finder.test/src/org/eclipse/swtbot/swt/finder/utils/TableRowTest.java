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
package org.eclipse.swtbot.swt.finder.utils;

import junit.framework.TestCase;

public class TableRowTest extends TestCase  {

	public void testHashCodeIsSameForSameTableRows() throws Exception {
		assertEquals(emptyTableRow().hashCode(), emptyTableRow().hashCode());
		assertEquals(emptyTableRow().hashCode(), emptyTableRow().hashCode());
		assertEquals(helloWorldRow().hashCode(), helloWorldRow().hashCode());
	}

	public void testHashCodeIsDifferentForDifferentTableRows() throws Exception {
		assertFalse(helloWorldRow().hashCode() == goodbyWorldRow().hashCode());
	}

	public void testEqualsForEqualTableRows() throws Exception {
		final TableRow tableRow = emptyTableRow();
		assertTrue(tableRow.equals(tableRow));
		assertFalse(tableRow.equals(null));
		assertFalse(tableRow.equals(this));

		assertTrue(tableRow.equals(emptyTableRow()));
	}

	public void testEqualsForUnequalTableRows() throws Exception {
		assertFalse(helloWorldRow().equals(goodbyWorldRow()));
	}

	public void testToString() throws Exception {
		assertEquals("[Hello, world]", helloWorldRow().toString());
	}

	private TableRow emptyTableRow() {
		return new TableRow();
	}

	private TableRow helloWorldRow() {
		return new TableRow(new String[] { "Hello", "world" });
	}

	private TableRow goodbyWorldRow() {
		return new TableRow(new String[] { "goodbye", "world" });
	}
}
