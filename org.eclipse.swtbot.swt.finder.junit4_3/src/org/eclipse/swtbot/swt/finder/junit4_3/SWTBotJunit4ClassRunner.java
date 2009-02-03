/*******************************************************************************
 * Copyright (c) 2008 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Hans Schwaebli - initial API and implementation (Bug 259787)
 *     Toby Weston  - initial API and implementation (Bug 259787)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.junit4_3;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TestClassRunner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;


/**
 * A runner that captures screenshots on test failures. If you wish to launch your application for your tests use
 * {@link SWTBotApplicationLauncherClassRunner}. Clients are supposed to subclass this. Typical usage is:
 * 
 * <pre>
 * &#064;RunWith(SWTBotJunit4ClassRunner.class)
 * public class FooTest {
 * 	&#064;Test
 * 	public void canSendEmail() {
 * 	}
 * }
 * </pre>
 * 
 * @author Hans Schwaebli (Bug 259787)
 * @author Toby Weston (Bug 259787)
 * @version $Id$
 * @see SWTBotApplicationLauncherClassRunner
 */
@SuppressWarnings("restriction")
public class SWTBotJunit4ClassRunner extends TestClassRunner {

	/**
	 * Creates a SWTBotRunner to run {@code klass}
	 * 
	 * @throws InitializationError if the test class is malformed.
	 */
	public SWTBotJunit4ClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	public void run(RunNotifier notifier) {
		RunListener failureSpy = new ScreenshotCaptureListener();
		notifier.addListener(failureSpy);
		try {
			super.run(notifier);
		} finally {
			notifier.removeListener(failureSpy);
		}
	}

}
