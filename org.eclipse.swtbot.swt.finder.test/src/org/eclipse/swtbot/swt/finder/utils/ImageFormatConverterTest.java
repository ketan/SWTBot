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

import org.eclipse.swt.SWT;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ImageFormatConverterTest extends TestCase {

	public void testTranslatesFromStringsToIds() throws Exception {
		assertEquals(SWT.IMAGE_PNG, new ImageFormatConverter().imageTypeOf("png"));
		assertEquals(SWT.IMAGE_JPEG, new ImageFormatConverter().imageTypeOf("jpg"));
		assertEquals(SWT.IMAGE_JPEG, new ImageFormatConverter().imageTypeOf("jpeg"));
		assertEquals(SWT.IMAGE_BMP, new ImageFormatConverter().imageTypeOf("bmp"));
		assertEquals(SWT.IMAGE_TIFF, new ImageFormatConverter().imageTypeOf("tiff"));
		assertEquals(SWT.IMAGE_GIF, new ImageFormatConverter().imageTypeOf("gif"));
		assertEquals(SWT.IMAGE_ICO, new ImageFormatConverter().imageTypeOf("ico"));
	}

}
