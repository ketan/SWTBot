package org.eclipse.swtbot.eclipse.ui.wizards;

import java.util.List;

public interface PlatformInfoProvider {

	String[] getProducts();

	String getDefaultProduct();

	String[] getApplications();

	String getDefaultApplication();

	List<String> getProjects();

}
