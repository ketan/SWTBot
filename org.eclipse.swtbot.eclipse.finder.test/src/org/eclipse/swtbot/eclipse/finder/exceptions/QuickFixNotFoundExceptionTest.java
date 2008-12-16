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

import junit.framework.TestCase;

/**
 * Tests the quick fix not found exception.
 * 
 * @author @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id: QuickFixNotFoundExceptionTest.java 1194 2008-12-02 07:36:32Z kpadegaonkar $
 */
public class QuickFixNotFoundExceptionTest extends TestCase {

	/**
	 * Item under test.
	 */
	QuickFixNotFoundException	fix	= null;

	public void testQuickFixNotFoundExceptionString() {
		fix = new QuickFixNotFoundException("Message");
		assertEquals("Message", fix.getMessage());
	}

	public void testQuickFixNotFoundExceptionStringThrowable() {
		Throwable throwable = new Throwable();
		fix = new QuickFixNotFoundException(throwable);
		assertEquals(throwable, fix.getCause());
	}

	public void testQuickFixNotFoundExceptionThrowable() {
		Throwable throwable = new Throwable();
		fix = new QuickFixNotFoundException("Message2", throwable);
		assertEquals("Message2", fix.getMessage());
		assertEquals(throwable, fix.getCause());
	}
}
