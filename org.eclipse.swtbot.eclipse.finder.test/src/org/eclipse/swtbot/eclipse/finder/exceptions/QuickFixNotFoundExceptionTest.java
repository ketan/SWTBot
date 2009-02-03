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
package org.eclipse.swtbot.eclipse.finder.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the quick fix not found exception.
 * 
 * @author @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 */
public class QuickFixNotFoundExceptionTest {

	/**
	 * Item under test.
	 */
	QuickFixNotFoundException	fix	= null;

	@Test
	public void quickFixNotFoundExceptionString() {
		fix = new QuickFixNotFoundException("Message");
		assertEquals("Message", fix.getMessage());
	}

	@Test
	public void quickFixNotFoundExceptionStringThrowable() {
		Throwable throwable = new Throwable();
		fix = new QuickFixNotFoundException(throwable);
		assertEquals(throwable, fix.getCause());
	}

	@Test
	public void quickFixNotFoundExceptionThrowable() {
		Throwable throwable = new Throwable();
		fix = new QuickFixNotFoundException("Message2", throwable);
		assertEquals("Message2", fix.getMessage());
		assertEquals(throwable, fix.getCause());
	}
}
