/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.ui.project;

public class Templatizer {

	private final String	pluginId;
	private final String	pluginName;
	private final String	pluginVersion;
	private final String	pluginProvider;

	public Templatizer(String pluginId, String pluginName, String pluginVersion, String pluginProvider) {
		this.pluginId = pluginId;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.pluginProvider = pluginProvider;
	}

	public String templatize(String contents) {
		return 
		contents
		.replaceAll("@PLUGIN_NAME@", pluginName)
		.replaceAll("@PLUGIN_ID@", pluginId)
		.replaceAll("@PLUGIN_VERSION@", pluginVersion)
		.replaceAll("@PLUGIN_PROVIDER@", pluginProvider);
	}

}
