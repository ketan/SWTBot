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
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardStrategy;
import org.eclipse.swtbot.swt.finder.waits.ICondition;

/**
 * Holds the preferences for the SWT Bot.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.1
 */
public class SWTBotPreferences implements SWTBotPreferenceConstants {

	/**
	 * The default key used to match SWT widgets. Defaults to {@code org.eclipse.swtbot.widget.key}. To set another
	 * default use the system property
	 * {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_DEFAULT_KEY}.
	 */
	public static String		DEFAULT_KEY					= System.getProperty(KEY_DEFAULT_KEY, "org.eclipse.swtbot.widget.key");

	/**
	 * The timeout for finding widgets among other things. Defaults to 5000ms. To set another default use the system
	 * property {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_TIMEOUT}.
	 */
	public static long			TIMEOUT						= toLong(System.getProperty(KEY_TIMEOUT, "5000"), 5000);

	/**
	 * The name of the file in which the recorder records. Defaults to "swtbot.record.txt". To set another default, use
	 * the system property {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_RECORER_FILE_NAME}.
	 */
	public static String		RECORDER_FILE_NAME			= System.getProperty(KEY_RECORER_FILE_NAME, "swtbot.record.txt");

	/**
	 * The speed of playback in milliseconds. Defaults to 0. To set another default, use the system property {@code
	 * org.eclipse.swtbot.playback.delay}.
	 */
	public static long			PLAYBACK_DELAY				= toLong(System.getProperty(KEY_PLAYBACK_DELAY, "0"), 0);

	/**
	 * The maximum number of screenshots that SWTBot should capture. Defaults to 100. To set another default use the
	 * system property
	 * {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_MAX_ERROR_SCREENSHOT_COUNT}.
	 */
	public static int			MAX_ERROR_SCREENSHOT_COUNT	= toInt(System.getProperty(KEY_MAX_ERROR_SCREENSHOT_COUNT, "100"), 100);

	/**
	 * The directory in which screenshots should be generated. Defaults to "screenshots". To set another default use the
	 * system property {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_SCREENSHOTS_DIR}.
	 */
	public static String		SCREENSHOTS_DIR				= System.getProperty(KEY_SCREENSHOTS_DIR, "screenshots");

	/**
	 * The screenshot image format to be used. Defaults to "jpeg". To set another default use the system property
	 * {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_SCREENSHOT_FORMAT}. The value must be
	 * one these: BMP, GIF, ICO, JPEG, JPG, PNG or TIFF.
	 */
	public static String		SCREENSHOT_FORMAT			= System.getProperty(KEY_SCREENSHOT_FORMAT, "jpeg");

	/**
	 * The keyboard layout. This value is autodetected at runtime. This can be set using the system property
	 * {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_KEYBOARD_LAYOUT}.
	 * <p>
	 * <strong>Note:</strong> the layout must be of the form foo.bar.baz.[MAC_][LANGUAGE_][COUNTRY_][VARIANT_] This
	 * expects a file named "foo/bar/baz/[MAC_][LANGUAGE_][COUNTRY_][VARIANT_].keyboard"
	 * </p>
	 * Eg:
	 * <table border="1">
	 * <tr>
	 * <th align="left"><b>Layout Name</b></th>
	 * <th align="left"><b>Configuration File</b></th>
	 * </tr>
	 * <tr>
	 * <td>com.foo.bar.MAC.EN.US</td>
	 * <td>com/foo/bar/MAC_EN_US.keyboard</td>
	 * </tr>
	 * <tr>
	 * <td>com.foo.bar.MAC.EN_GB</td>
	 * <td>com/foo/bar/MAC_EN_GB.keyboard</td>
	 * </tr>
	 * <tr>
	 * <td>com.foo.bar.FR_FR</td>
	 * <td>com/foo/bar/FR_FR.keyboard</td>
	 * </tr>
	 * <tr>
	 * <td>com.foo.bar.DE</td>
	 * <td>/com/foo/bar/DE.keyboard</td>
	 * </tr>
	 * </table>
	 * 
	 * @see Locale
	 */
	public static String		KEYBOARD_LAYOUT				= System.getProperty(KEY_KEYBOARD_LAYOUT, KeyboardLayoutDetector
																	.detectKeyboard());

	/**
	 * The the time interval in milliseconds between typing characters in a string. Defaults to 50ms. To set another
	 * default use the system property {@code org.eclipse.swtbot.keyboard.interval}.
	 */
	public static int			TYPE_INTERVAL				= toInt(System.getProperty(KEY_TYPE_INTERVAL, "50"), 50);
	/**
	 * The default keyboard strategy. Defaults to org.eclipse.swtbot.swt.finder.keyboard.AWTKeyboardStrategy. To set
	 * another default use the system property {@code org.eclipse.swtbot.keyboard.strategy}. This property must be set
	 * to a subclass of {@link KeyboardStrategy}.
	 * 
	 * @see KeyboardStrategy
	 * @see Keyboard
	 */
	public static String		KEYBOARD_STRATEGY			= System.getProperty(KEY_KEYBOARD_STRATEGY,
																	"org.eclipse.swtbot.swt.finder.keyboard.AWTKeyboardStrategy");

	/**
	 * The default time delay between successive polling while waiting for a condition to be evaluated. Defaults to
	 * 500ms. To set another default use the system property
	 * {@value org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants#KEY_DEFAULT_POLL_DELAY}.
	 * 
	 * @see SWTBot#waitUntil(ICondition, long, long)
	 * @see SWTBot#waitWhile(ICondition, long, long)
	 */
	public static final long	DEFAULT_POLL_DELAY			= toLong(System.getProperty(KEY_DEFAULT_POLL_DELAY, "500"), 500);

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

	public static class KeyboardLayoutDetector {
		public static String detectKeyboard() {
			String keyboardLayout = "";
			if (isMac()) {
				keyboardLayout += "MAC_";
			}

			Locale locale = InputContext.getInstance().getLocale();
			if (locale == null)
				locale = Locale.getDefault();

			String layout = StringUtils.join(new String[] { locale.getLanguage(), locale.getCountry(), locale.getVariant() }, "_");

			layout = layout.replaceAll("\\_\\_", "_");
			layout = layout.replaceAll("\\_$", "");

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
