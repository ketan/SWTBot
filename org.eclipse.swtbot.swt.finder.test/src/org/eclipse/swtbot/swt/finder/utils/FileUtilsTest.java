package org.eclipse.swtbot.swt.finder.utils;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertContains;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Test;

public class FileUtilsTest {
	
	@Test
	public void canReadFromFile() throws Exception {
		String read = FileUtils.read("LICENSE.EPL");
		assertContains("DISCLAIMER OF LIABILITY", read);
	}
	
	@Test
	public void canReadFromURL() throws Exception {
		String read = FileUtils.read(new File("LICENSE.EPL").toURL());
		assertContains("DISCLAIMER OF LIABILITY", read);
	}
	
	@Test
	public void canWriteToAFile() throws Exception {
		FileUtils.write("some text", new File("somefile.txt"));
		String read = FileUtils.read(new File("somefile.txt").toURL());
		assertEquals("some text", read);
	}
	
	@After
	public void cleanup(){
		File file = new File("somefile.txt");
		if (file.exists())
			file.delete();
	}
}
