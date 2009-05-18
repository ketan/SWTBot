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

import java.awt.im.InputContext;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardStrategy;

/**
 * Holds the preferences for the SWT Bot.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.1
 */
public class SWTBotPreferences {

	private static class KeyboardLayoutDetector {
		private static String detectKeyboard() {
			String keyboardLayout = "";
			if (isMac()) {
				keyboardLayout += "MAC.";
			}

			Locale locale = InputContext.getInstance().getLocale();
			String layout;
			layout = locale.getVariant();
			if (layout.equals(""))
				layout = locale.getCountry();
			if (layout.equals(""))
				layout = locale.getLanguage();
			if (layout.equals(""))
				throw new IllegalStateException("Could not determine keyboard layout.");
			keyboardLayout += layout.toUpperCase();

			return keyboardLayout;
		}

		private static boolean isMac() {
			String swtPlatform = SWT.getPlatform();
			return ("carbon".equals(swtPlatform) || "cocoa".equals(swtPlatform));
		}

	}

	/**
	 * The default key used to match SWT widgets.
	 * 
	 * @since 2.0
	 */
	public static final String	DEFAULT_KEY	= "org.eclipse.swtbot.widget.key";	//$NON-NLS-1$

	/**
	 * Gets the timeout. To set use the system property {@code org.eclipse.swtbot.search.timeout}.
	 * 
	 * @return the timeout value.
	 * @since 1.3
	 */
	public static long getTimeout() {
		return toLong(System.getProperty("org.eclipse.swtbot.search.timeout", "5000")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the recorder file name. To set use the system property {@code org.eclipse.swtbot.recorder.file.name}.
	 * 
	 * @return the recorder file name.
	 */
	public static String recorderFileName() {
		return System.getProperty("org.eclipse.swtbot.recorder.file.name"); //$NON-NLS-1$
	}

	/**
	 * Gets the playback delay. This can be set using the system property {@code org.eclipse.swtbot.playback.delay}.
	 * 
	 * @return the playback delay.
	 */
	public static long playbackDelay() {
		return toLong(System.getProperty("org.eclipse.swtbot.playback.delay", "0")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the maximum number of screenshots that SWTBot should capture. This can be set using the system property
	 * {@code org.eclipse.swtbot.maximum.screenshots}.
	 * 
	 * @return the maximum screenshots.
	 * @since 1.3
	 */
	public static int getMaximumScreenshots() {
		return toInt(System.getProperty("org.eclipse.swtbot.maximum.screenshots", "100")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the screenshot image format to be used. This can be set using the system property {@code
	 * org.eclipse.swtbot.screenshot.format}.<br>
	 * It must be one these: BMP, GIF, ICO, JPEG, JPG, PNG or TIFF.<br>
	 * JPEG will be used as the default if this property is not set.
	 * 
	 * @return the screenshot image format.
	 * @since 1.3
	 */
	public static String getScreenshotFormat() {
		return System.getProperty("org.eclipse.swtbot.screenshot.format", "jpeg"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the keyboard layout. This can be set using the system property {@code org.eclipse.swtbot.keyboardLayout}.
	 * <p>
	 * <strong>Note:</strong> the layout must be of the form [MAC.][upper-case-two-character-country-code]. Eg: "MAC.EN"
	 * or "MAC.GB" or just "FR" or "DE".
	 * </p>
	 * 
	 * @return the keyboard layout.
	 */
	public static String getKeyboardLayout() {
		return System.getProperty("org.eclipse.swtbot.keyboardLayout", KeyboardLayoutDetector.detectKeyboard());
	}

	/**
	 * Gets the the time interval in ms. between typing characters in a string. This can be set using the system
	 * property {@code org.eclipse.swtbot.keyboard.interval}.<br>
	 * 
	 * @return the time interval between typing characters.
	 */
	public static int typeInterval() {
		return toInt(System.getProperty("org.eclipse.swtbot.keyboard.interval", "50"));
	}

	/**
	 * Returns the default keyboard strategy. This can be configured using the system property {@code
	 * org.eclipse.swtbot.keyboard.strategy}. This property must be set to a subclass of {@link KeyboardStrategy}.
	 * 
	 * @see KeyboardStrategy
	 * @see Keyboard
	 * @return the default keyboard strategy to be used while sending key events.
	 */
	public static String keyboardStrategy() {
		return System.getProperty("org.eclipse.swtbot.keyboard.strategy", "org.eclipse.swtbot.swt.finder.keyboard.AWTKeyboardStrategy");
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
