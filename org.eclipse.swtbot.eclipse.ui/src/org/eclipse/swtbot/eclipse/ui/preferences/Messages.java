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
package org.eclipse.swtbot.eclipse.ui.preferences;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SuppressWarnings("all")
public class Messages extends NLS {
	private static final String	BUNDLE_NAME	= "org.eclipse.swtbot.eclipse.ui.preferences.messages"; //$NON-NLS-1$
	public static String		Enable_Additional_Autocomplete_Options;
	public static String		SWTBotPreferencePage_SWTBot_Preference_Dialog_Description;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
