/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=108
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=112
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;


/**
 * Holds the preferences for the SWT Bot.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotPreferences.java 1195 2008-12-02 07:48:40Z kpadegaonkar $
 * @since 1.1
 */
public class SWTBotPreferences {

	/** The default key used to match SWT widgets.
	 * @since 2.0*/
	public static final String	DEFAULT_KEY	= "org.eclipse.swtbot.widget.key";

	/**
	 * Gets the timeout. To set use the system property {@code org.eclipse.swtbot.search.timeout}.
	 *
	 * @return the timeout value.
	 * @since 1.3
	 */
	public static long getTimeout() {
		return toLong(System.getProperty("org.eclipse.swtbot.search.timeout", "5000"));
	}

	/**
	 * Gets the recorder file name. To set use the system property {@code org.eclipse.swtbot.recorder.file.name}.
	 *
	 * @return the recorder file name.
	 */
	public static String recorderFileName() {
		return System.getProperty("org.eclipse.swtbot.recorder.file.name");
	}

	/**
	 * Gets the playback delay. This can be set using the system property {@code org.eclipse.swtbot.playback.delay}.
	 *
	 * @return the playback delay.
	 */
	public static long playbackDelay() {
		return toLong(System.getProperty("org.eclipse.swtbot.playback.delay", "0"));
	}

    /**
     * Gets the maximum number of screenshots that SWTBot should capture. This can be set using the system property {@code org.eclipse.swtbot.maximum.screenshots}.
     *
     * @return the maximum screenshots.
     * @since 1.3
     */
	public static int getMaximumScreenshots() {
	    return toInt(System.getProperty("org.eclipse.swtbot.maximum.screenshots", "100"));
	}

    /**
     * Gets the screenshot image format to be used. This can be set using the system property {@code org.eclipse.swtbot.screenshot.format}.<br>
     * It must be one these: BMP, GIF, ICO, JPEG, JPG, PNG or TIFF.<br>
     * JPEG will be used as the default if this property is not set.
     * @return the screenshot image format.
     * @since 1.3
     */
	public static String getScreenshotFormat() {
	    return System.getProperty("org.eclipse.swtbot.screenshot.format", "jpeg");
	}

	private static long toLong(String timeoutValue) {
		try {
			Long timeout = Long.valueOf(timeoutValue);
			return timeout.longValue();
		} catch (Exception e) {
			return 0;
		}
	}

    private static int toInt(String value) {
        try {
            Integer integerValue = Integer.valueOf(value);
            return integerValue.intValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
