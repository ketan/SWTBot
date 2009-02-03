/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Hans Schwaebli - initial API and implementation (Bug 259787)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.junit4_3;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Captures screenshots on failure notifications.
 * 
 * @author Hans Schwaebli (Bug 259787)
 * @version $Id$
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
final class ScreenshotCaptureListener extends RunListener {
	/** The logger. */
	private static Logger	log					= Logger.getLogger(SWTBotApplicationLauncherClassRunner.class);

	/** Counts the screenshots to determine if maximum number is reached. */
	private static int		screenshotCounter	= 0;

	public void testFailure(Failure failure) throws Exception {
		captureScreenshot(failure);
	}

	private void captureScreenshot(Failure failure) {
		try {
			int maximumScreenshots = SWTBotPreferences.getMaximumScreenshots();
			String fileName = "screenshots/" + failure.getTestHeader() + "." + SWTBotPreferences.getScreenshotFormat().toLowerCase(); //$NON-NLS-1$
			if (++screenshotCounter <= maximumScreenshots) {
				new File("screenshots").mkdirs(); //$NON-NLS-1$ 
				captureScreenshot(fileName);
			} else {
				log.info("No screenshot captured for '" + failure.getTestHeader() + "' because maximum number of screenshots reached: " //$NON-NLS-1$ 
						+ maximumScreenshots);
			}
		} catch (Exception e) {
			log.warn("Could not capture screenshot", e); //$NON-NLS-1$
		}
	}

	private boolean captureScreenshot(String fileName) {
		return SWTUtils.captureScreenshot(fileName);
	}

}
