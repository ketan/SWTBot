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
package org.eclipse.swtbot.swt.finder.resolvers;

import java.util.List;

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: NullResolverTest.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 */
public class NullResolverTest extends TestCase {

	public void testGetChildren() {
		List children = new NullResolver().getChildren(null);
		assertTrue(children.isEmpty());
	}

	public void testResolvesEverything() throws Exception {
		assertTrue(new NullResolver().canResolve(null));
	}

	public void testGetParent() {
		assertNull(new NullResolver().getParent(null));
	}

	public void testHasChildren() {
		assertFalse(new NullResolver().hasChildren(null));
	}

	public void testHasParent() {
		assertFalse(new NullResolver().hasParent(null));
	}

}
