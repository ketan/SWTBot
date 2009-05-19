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

	/**
	 * The default key used to match SWT widgets. Defaults to {@code org.eclipse.swtbot.widget.key}. To set another default use the system property {@code org.eclipse.swtbot.search.defaultKey}
	 */
	public static String	DEFAULT_KEY					= System.getProperty("org.eclipse.swtbot.search.defaultKey", "org.eclipse.swtbot.widget.key");	

	/**
	 * The timeout for finding widgets among other things. Defaults to 5000ms. To set another default use the system
	 * property {@code org.eclipse.swtbot.search.timeout}.
	 */
	public static long		TIMEOUT						= toLong(System.getProperty("org.eclipse.swtbot.search.timeout", "5000"), 5000);
	
	/**
	 * The name of the file in which the recorder records. Defaults to "swtbot.record.txt". To set another default, use
	 * the system property {@code org.eclipse.swtbot.recorder.file.name}.
	 */
	public static String	RECORDER_FILE_NAME			= System.getProperty("org.eclipse.swtbot.recorder.file.name", "swtbot.record.txt");
	
	/**
	 * The speed of playback in milliseconds. Defaults to 0. To set another default, use the system property {@code
	 * org.eclipse.swtbot.playback.delay}.
	 */
	public static long		PLAYBACK_DELAY				= toLong(System.getProperty("org.eclipse.swtbot.playback.delay", "0"), 0);
	
	/**
	 * The maximum number of screenshots that SWTBot should capture. Defaults to 100. To set another default use the
	 * system property {@code org.eclipse.swtbot.screenshots.maxcount}.
	 */
	public static int		MAX_ERROR_SCREENSHOT_COUNT	= toInt(System.getProperty("org.eclipse.swtbot.screenshots.error.maxcount", "100"), 100);
	
	/**
	 * The directory in which screenshots should be generated. Defaults to "screenshots". To set another default use the
	 * system property {@code org.eclipse.swtbot.screenshots.dir}.
	 */
	public static String	SCREENSHOTS_DIR				= System.getProperty("org.eclipse.swtbot.screenshots.dir", "screenshots");
	
	/**
	 * The screenshot image format to be used. Defaults to "jpeg". To set another default use the system property
	 * {@code org.eclipse.swtbot.screenshot.format}. The value must be one these: BMP, GIF, ICO, JPEG, JPG, PNG or TIFF.
	 */
	public static String	SCREENSHOT_FORMAT			= System.getProperty("org.eclipse.swtbot.screenshot.format", "jpeg");
	
	/**
	 * The keyboard layout. This value is autodetected at runtime. This can be set using the system property
	 * {@code org.eclipse.swtbot.keyboardLayout}.
	 * <p>
	 * <strong>Note:</strong> the layout must be of the form [MAC.][upper-case-two-character-country-code]. Eg: "MAC.EN"
	 * or "MAC.GB" or just "FR" or "DE".
	 * </p>
	 */
	public static String	KEYBOARD_LAYOUT				= System.getProperty("org.eclipse.swtbot.keyboardLayout", KeyboardLayoutDetector .detectKeyboard());

	/**
	 * The the time interval in milliseconds between typing characters in a string. Defaults to 50ms. To set another default
	 * use the system property {@code org.eclipse.swtbot.keyboard.interval}.
	 */
	public static int		TYPE_INTERVAL				= toInt(System.getProperty("org.eclipse.swtbot.keyboard.interval", "50"), 50);
	/**
	 * The default keyboard strategy. Defaults to org.eclipse.swtbot.swt.finder.keyboard.AWTKeyboardStrategy. To set
	 * another default use the system property {@code org.eclipse.swtbot.keyboard.strategy}. This property must be set
	 * to a subclass of {@link KeyboardStrategy}.
	 * 
	 * @see KeyboardStrategy
	 * @see Keyboard
	 */
	public static String	KEYBOARD_STRATEGY			= System.getProperty("org.eclipse.swtbot.keyboard.strategy", "org.eclipse.swtbot.swt.finder.keyboard.AWTKeyboardStrategy");

	private static long toLong(String timeoutValue, long defaultValue) {
		try {
			Long timeout = Long.valueOf(timeoutValue);
			return timeout.longValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private static int toInt(String value, int defaultValue) {
		try {
			Integer integerValue = Integer.valueOf(value);
			return integerValue.intValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

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
}
