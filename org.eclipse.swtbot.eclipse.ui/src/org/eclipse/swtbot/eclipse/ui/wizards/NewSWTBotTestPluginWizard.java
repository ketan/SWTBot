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

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swtbot.eclipse.ui.project.ProjectCreator;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewSWTBotTestPluginWizard extends Wizard implements INewWizard {

	private final NewPluginProjectWizardPage			projectWizardPage;
//	private final LaunchConfigPropertiesWizardPage	launchConfigWizardPage;

	public NewSWTBotTestPluginWizard() {
		setWindowTitle("New SWTBot Test Plugin");
		projectWizardPage = new NewPluginProjectWizardPage();
//		launchConfigWizardPage = new LaunchConfigPropertiesWizardPage();
	}

	public void addPages() {
		addPage(projectWizardPage);
//		addPage(launchConfigWizardPage);
	}

	@Override
	public boolean performFinish() {
		String pluginName = projectWizardPage.pluginName();
		String pluginId = projectWizardPage.pluginId();
		String pluginVersion = projectWizardPage.pluginVersion();
		String pluginProvider = projectWizardPage.pluginProvider();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ProjectCreator projectCreator = new ProjectCreator(pluginId, pluginName, pluginVersion, pluginProvider, root);
		try {
			projectCreator.create();
			return true;
		} catch (CoreException e) {
			projectCreator.delete();
			return false;
		}
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

}
