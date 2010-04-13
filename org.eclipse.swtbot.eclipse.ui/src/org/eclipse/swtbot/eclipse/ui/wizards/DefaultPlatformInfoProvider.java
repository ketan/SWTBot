package org.eclipse.swtbot.eclipse.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.pde.core.plugin.TargetPlatform;

public class DefaultPlatformInfoProvider implements PlatformInfoProvider {

	public String[] getApplications() {
		return TargetPlatform.getApplications();
	}

	public String getDefaultApplication() {
		return TargetPlatform.getDefaultApplication();
	}

	public String getDefaultProduct() {
		return TargetPlatform.getDefaultProduct();
	}

	public String[] getProducts() {
		return TargetPlatform.getProducts();
	}

	public List<String> getProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		ArrayList<String> result = new ArrayList<String>();

		for (IProject project : projects) {
			result.add(project.getName());
		}
		return result;
	}

}
