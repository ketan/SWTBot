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

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ClassUtilsTest {

	public static final class MyClass {

	}

	@Test
	public void getsSimpleClassName() throws Exception {
		assertEquals("ClassUtilsTest", ClassUtils.simpleClassName(getClass()));
		assertEquals("ClassUtilsTest", ClassUtils.simpleClassName(this));
		assertEquals("ClassUtilsTest$MyClass", ClassUtils.simpleClassName(MyClass.class));
		assertEquals("ClassUtilsTest$MyClass", ClassUtils.simpleClassName(new MyClass()));
		assertEquals("FooClass", ClassUtils.simpleClassName("com.example.FooClass"));
		assertEquals("FooClass$InnerClass", ClassUtils.simpleClassName("com.example.FooClass$InnerClass"));
		assertEquals("", ClassUtils.simpleClassName((Object) null));
		assertEquals("", ClassUtils.simpleClassName(""));
		assertEquals("", ClassUtils.simpleClassName((Class) null));
	}

}
