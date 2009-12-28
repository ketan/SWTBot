package org.eclipse.swtbot.swt.recorder.ui;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BootstrapperTest {
	
	private long	original;

	@Before
	public void setup(){
		original = SWTBotRecorderWindow.DISPLAY_WAIT_TIMEOUT;
		SWTBotRecorderWindow.DISPLAY_WAIT_TIMEOUT = 0;
	}
	
	@After public void teardown(){
		SWTBotRecorderWindow.DISPLAY_WAIT_TIMEOUT = original;
	}
	
	
	@Test
	public void invokesMain() throws Exception {
		try {
			new Bootstrapper("org.eclipse.swtbot.swt.recorder.ui.BootstrapperTest$MyMain", "my", "args").start();
		} catch (IllegalStateException e) {
			assertEquals("Could not find a display", e.getMessage());
			assertEquals(2, MyMain.args.length);
			assertEquals("my", MyMain.args[0]);
			assertEquals("args", MyMain.args[1]);
		}
	}
	
	public static class MyMain {
		private static String[]	args;

		public static void main(String[] args) {
			MyMain.args = args;
		}
	}
}
