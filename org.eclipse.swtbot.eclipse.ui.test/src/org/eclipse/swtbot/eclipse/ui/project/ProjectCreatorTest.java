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

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Test;

public class ProjectCreatorTest {

	@Test
	public void canCreateAProject() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ProjectCreator creator = new ProjectCreator("org.eclipse.swtbot.testplugin", "test plugin", "1.2.3.4", "Eclipse.org", root);
		creator.create();

		IProject project = root.getProject("org.eclipse.swtbot.testplugin");
		IFolder metaInf = project.getFolder("META-INF");
		IFolder src = project.getFolder("src");

		assertTrue(project.exists());
		assertTrue(src.exists());
		assertTrue(metaInf.exists());
	}
}
