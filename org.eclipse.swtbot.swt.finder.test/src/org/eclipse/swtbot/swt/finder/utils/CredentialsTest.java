/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
public class CredentialsTest extends TestCase {

	@Test
	public void testEquals() {
		assertEquals(new Credentials(null, null), new Credentials(null, null));
		assertEquals(new Credentials("foo", null), new Credentials("foo", null));
		assertEquals(new Credentials(null, "foo"), new Credentials(null, "foo"));
		assertEquals(new Credentials("foo", "bar"), new Credentials("foo", "bar"));

		assertFalse(new Credentials("foo", null).equals(new Credentials("foo", "bar")));
		assertFalse(new Credentials(null, "bar").equals(new Credentials("foo", "bar")));
	}

	@Test
	public void testHashCode() {
		assertEquals(new Credentials(null, null).hashCode(), new Credentials(null, null).hashCode());
		assertEquals(new Credentials("foo", null).hashCode(), new Credentials("foo", null).hashCode());
		assertEquals(new Credentials(null, "foo").hashCode(), new Credentials(null, "foo").hashCode());
		assertEquals(new Credentials("foo", "bar").hashCode(), new Credentials("foo", "bar").hashCode());

		assertFalse(new Credentials("foo", null).hashCode() == new Credentials("foo", "bar").hashCode());
		assertFalse(new Credentials(null, "bar").hashCode() == new Credentials("foo", "bar").hashCode());
	}

}
