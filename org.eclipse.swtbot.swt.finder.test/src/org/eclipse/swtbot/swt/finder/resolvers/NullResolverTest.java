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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class NullResolverTest {

	@Test
	public void getChildren() {
		List children = new NullResolver().getChildren(null);
		assertTrue(children.isEmpty());
	}

	@Test
	public void resolvesEverything() throws Exception {
		assertTrue(new NullResolver().canResolve(null));
	}

	@Test
	public void getParent() {
		assertNull(new NullResolver().getParent(null));
	}

	@Test
	public void hasChildren() {
		assertFalse(new NullResolver().hasChildren(null));
	}

	@Test
	public void hasParent() {
		assertFalse(new NullResolver().hasParent(null));
	}

}
