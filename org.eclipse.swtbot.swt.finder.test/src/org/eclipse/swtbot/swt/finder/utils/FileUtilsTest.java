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

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertContains;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Test;

public class FileUtilsTest {

	private static final File	FILE	= new File("somefile.txt");

	@Test
	public void canWriteToAFile() throws Exception {
		FileUtils.write("some text", FILE);
		String read = FileUtils.read(FILE.toURL());
		assertEquals("some text", read);
	}

	@Test
	public void canReadFromFile() throws Exception {
		String read = FileUtils.read("somefile.txt");
		assertContains("some text", read);
	}

	@Test
	public void canReadFromURL() throws Exception {
		String read = FileUtils.read(FILE.toURL());
		assertContains("some text", read);
	}

	@AfterClass
	public static void cleanup() {
		File file = FILE;
		if (file.exists())
			file.delete();
	}
}
