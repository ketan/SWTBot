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
public class ClassUtilsTest extends TestCase {

	public static final class MyClass {

	}

	public void testGetsSimpleClassName() throws Exception {
		assertEquals("ClassUtilsTest", ClassUtils.simpleClassName(getClass()));
		assertEquals("ClassUtilsTest", ClassUtils.simpleClassName(this));
		assertEquals("ClassUtilsTest$MyClass", ClassUtils.simpleClassName(MyClass.class));
		assertEquals("ClassUtilsTest$MyClass", ClassUtils.simpleClassName(new MyClass()));
		assertEquals("", ClassUtils.simpleClassName((Object) null));
	}
}
