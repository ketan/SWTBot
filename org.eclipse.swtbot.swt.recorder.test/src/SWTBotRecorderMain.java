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

import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.recorder.SWTBotRecorder;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotRecorderMain {

	public static void main(String[] args) {
		try {
			TestSuite testSuite = new TestSuite(RunnerTest.class);
			testSuite.run(new TestResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class RunnerTest extends AbstractSWTTestCase {

		private SWTBotRecorder	recorder;

		public void setUp() throws Exception {
			super.setUp();
			System.setProperty("org.eclipse.swtbot.recorder.actions.print", "true");
			recorder = new SWTBotRecorder(display, new SWTBot());
			recorder.start();

			// display.syncExec(new Runnable() {
			// public void run() {
			// while (!controlShell.isDisposed())
			// if (!display.readAndDispatch())
			// display.sleep();
			// display.dispose();
			// }
			// });
		}

		// @Test public void registeringListenerAddsToListenerTable() throws Exception {
		// AbstractSWTBotRecorderListener listener = new ShellEventListener(null, new SWTBot());
		// assertEquals(0, recorder.registeredListeners.size());
		// recorder.registerListener(SWT.Activate, listener);
		// assertEquals(1, recorder.registeredListeners.size());
		// assertTrue(recorder.registeredListeners.contains(new ListenerSet(SWT.Activate, listener)));
		// }
		//
		// @Test public void unRegisteringListenerRemovesFromListenerTable() throws Exception {
		// SWTBotRecorderListener listener = new ShellEventListener(null, new SWTBot());
		// recorder.registerListener(SWT.Activate, listener);
		// assertEquals(1, recorder.registeredListeners.size());
		// recorder.unregisterAllListeners();
		// assertTrue(recorder.registeredListeners.isEmpty());
		// }

		@Test
		public void doNothing() throws Exception {

		}

		public void _testRecords() throws Exception {
			display.syncExec(new Runnable() {
				public void run() {
					while (!controlShell.isDisposed())
						if (!display.readAndDispatch())
							display.sleep();
					display.dispose();
				}
			});
			recorder.stop();
		}

		public void tearDown() throws Exception {
			// recorder.unregisterAllListeners();
			super.tearDown();
		}
	}
}
