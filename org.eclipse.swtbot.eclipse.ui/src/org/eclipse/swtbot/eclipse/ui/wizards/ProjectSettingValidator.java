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
package org.eclipse.swtbot.eclipse.ui.wizards;

import java.util.List;

import org.osgi.framework.Version;

public class ProjectSettingValidator {

	private final String				pluginId;
	private final String				pluginName;
	private final String				pluginVersion;
	private final WizardPageSettings	wizardPage;
	private final List<String>			projectNames;

	public ProjectSettingValidator(String pluginId, String pluginName, String pluginVersion, List<String> projectNames,
			WizardPageSettings wizardPage) {
		this.pluginId = pluginId;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.projectNames = projectNames;
		this.wizardPage = wizardPage;
	}

	public void validate() {

		wizardPage.setPageComplete(true);
		wizardPage.setErrorMessage(null);

		if (pluginName.trim().length() == 0) {
			wizardPage.setErrorMessage("Plugin name cannot be empty!");
			wizardPage.setPageComplete(false);
			return;
		}

		if (projectNames.contains(pluginName)) {
			wizardPage.setErrorMessage("A project by that name already exists!");
			wizardPage.setPageComplete(false);
			return;
		}

		if (pluginId.trim().length() == 0) {
			wizardPage.setErrorMessage("You did not set the plugin id!");
			wizardPage.setPageComplete(false);
			return;
		}

		if (!isValidCompositeID3_0(pluginId)) {
			wizardPage.setErrorMessage("Invalid plugin id! Legal characters are A-Z a-z 0-9 . _ -");
			wizardPage.setPageComplete(false);
			return;
		}

		try {
			new Version(pluginVersion);
		} catch (IllegalArgumentException ex) {
			wizardPage
					.setErrorMessage("The specified version does not have the correct format (major.minor.micro.qualifier) or contains invalid characters!");
			wizardPage.setPageComplete(false);
			return;
		}
	}

	// copied from IdUtil from PDE.
	private boolean isValidCompositeID3_0(String name) {
		if (name.length() <= 0) {
			return false;
		}
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if ((c < 'A' || 'Z' < c) && (c < 'a' || 'z' < c) && (c < '0' || '9' < c) && c != '_' && c != '-') {
				if (i == 0 || i == name.length() - 1 || c != '.') {
					return false;
				}
			}
		}
		return true;
	}

}
