/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

/**
 * Constants used by {@link SWTBotPreferences}.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public interface SWTBotPreferenceConstants {

	/** @see SWTBotPreferences#DEFAULT_KEY */
	public static final String	KEY_DEFAULT_KEY					= "org.eclipse.swtbot.search.defaultKey";
	/** @see SWTBotPreferences#TIMEOUT */
	public static final String	KEY_TIMEOUT						= "org.eclipse.swtbot.search.timeout";
	/** @see SWTBotPreferences#RECORDER_FILE_NAME */
	public static final String	KEY_RECORER_FILE_NAME			= "org.eclipse.swtbot.recorder.file.name";

	/** @see SWTBotPreferences#PLAYBACK_DELAY */
	public static final String	KEY_PLAYBACK_DELAY				= "org.eclipse.swtbot.playback.delay";
	/** @see SWTBotPreferences#DEFAULT_POLL_DELAY */
	public static final String	KEY_DEFAULT_POLL_DELAY			= "org.eclipse.swtbot.playback.poll.delay";

	/** @see SWTBotPreferences#MAX_ERROR_SCREENSHOT_COUNT */
	public static final String	KEY_MAX_ERROR_SCREENSHOT_COUNT	= "org.eclipse.swtbot.screenshots.error.maxcount";
	/** @see SWTBotPreferences#SCREENSHOTS_DIR */
	public static final String	KEY_SCREENSHOTS_DIR				= "org.eclipse.swtbot.screenshots.dir";
	/** @see SWTBotPreferences#SCREENSHOT_FORMAT */
	public static final String	KEY_SCREENSHOT_FORMAT			= "org.eclipse.swtbot.screenshots.format";

	/** @see SWTBotPreferences#KEYBOARD_LAYOUT */
	public static final String	KEY_KEYBOARD_LAYOUT				= "org.eclipse.swtbot.keyboard.layout";
	/** @see SWTBotPreferences#TYPE_INTERVAL */
	public static final String	KEY_TYPE_INTERVAL				= "org.eclipse.swtbot.keyboard.interval";
	/** @see SWTBotPreferences#KEYBOARD_STRATEGY */
	public static final String	KEY_KEYBOARD_STRATEGY			= "org.eclipse.swtbot.keyboard.strategy";

}
