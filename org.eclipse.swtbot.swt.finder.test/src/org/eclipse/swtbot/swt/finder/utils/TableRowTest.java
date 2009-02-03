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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableRowTest {

	@Test
	public void hashCodeIsSameForSameTableRows() throws Exception {
		assertEquals(emptyTableRow().hashCode(), emptyTableRow().hashCode());
		assertEquals(emptyTableRow().hashCode(), emptyTableRow().hashCode());
		assertEquals(helloWorldRow().hashCode(), helloWorldRow().hashCode());
	}

	@Test
	public void hashCodeIsDifferentForDifferentTableRows() throws Exception {
		assertFalse(helloWorldRow().hashCode() == goodbyWorldRow().hashCode());
	}

	@Test
	public void equalsForEqualTableRows() throws Exception {
		final TableRow tableRow = emptyTableRow();
		assertTrue(tableRow.equals(tableRow));
		assertFalse(tableRow.equals(null));
		assertFalse(tableRow.equals(this));

		assertTrue(tableRow.equals(emptyTableRow()));
	}

	@Test
	public void equalsForUnequalTableRows() throws Exception {
		assertFalse(helloWorldRow().equals(goodbyWorldRow()));
	}

	@Test
	public void getsToString() throws Exception {
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
