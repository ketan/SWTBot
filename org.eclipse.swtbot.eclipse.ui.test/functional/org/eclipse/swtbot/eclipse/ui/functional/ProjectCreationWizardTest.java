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
package org.eclipse.swtbot.eclipse.ui.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class ProjectCreationWizardTest {

	private static SWTWorkbenchBot	bot	= new SWTWorkbenchBot();

	@Test
	public void canCreateSWTBotProject() throws Exception {
		new SWTBotProject().create("com.example");
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("com.example");
		assertTrue(project.exists());

		assertEquals("" +
				"Manifest-Version: 1.0\n" +
				"Bundle-ManifestVersion: 2\n" +
				"Bundle-Name: com.example\n" +
				"Bundle-SymbolicName: com.example;singleton:=true\n" +
				"Bundle-Version: 1.0.0.qualifier\n" +
				"Bundle-ActivationPolicy: lazy\n" +
				"Bundle-Vendor: ACME Corp.\n" +
				"Bundle-RequiredExecutionEnvironment: J2SE-1.5\n" +
				"Require-Bundle: org.eclipse.swtbot.go\n" +
				"", contentsOf(project, "META-INF/MANIFEST.MF"));

		assertEquals("" +
				"source.. = src/\n" +
				"output.. = bin/\n" +
				"bin.includes = META-INF/,\\\n" +
				"               .\n" +
				"", contentsOf(project, "build.properties"));

		assertEquals("" +
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<projectDescription>\n" +
				"	<name>com.example</name>\n" +
				"	<comment></comment>\n" +
				"	<projects>\n" +
				"	</projects>\n" +
				"	<buildSpec>\n" +
				"		<buildCommand>\n" +
				"			<name>org.eclipse.jdt.core.javabuilder</name>\n" +
				"			<arguments>\n" +
				"			</arguments>\n" +
				"		</buildCommand>\n" +
				"		<buildCommand>\n" +
				"			<name>org.eclipse.pde.ManifestBuilder</name>\n" +
				"			<arguments>\n" +
				"			</arguments>\n" +
				"		</buildCommand>\n" +
				"		<buildCommand>\n" +
				"			<name>org.eclipse.pde.SchemaBuilder</name>\n" +
				"			<arguments>\n" +
				"			</arguments>\n" +
				"		</buildCommand>\n" +
				"	</buildSpec>\n" +
				"	<natures>\n" +
				"		<nature>org.eclipse.pde.PluginNature</nature>\n" +
				"		<nature>org.eclipse.jdt.core.javanature</nature>\n" +
				"	</natures>\n" +
				"</projectDescription>\n" +
				"", contentsOf(project, ".project"));

		assertEquals("" +
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<classpath>\n" +
						"	<classpathentry kind=\"con\" " +
						"path=\"org.eclipse.jdt.launching.JRE_CONTAINER/" +
						"org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5\"/>\n" +
						"	<classpathentry kind=\"con\" path=\"org.eclipse.pde.core.requiredPlugins\"/>\n" +
						"	<classpathentry kind=\"src\" path=\"src\"/>\n" +
						"	<classpathentry kind=\"output\" path=\"bin\"/>\n" +
						"</classpath>\n" +
						"", contentsOf(project, ".classpath"));

	}

	private String contentsOf(IProject project, String name) throws CoreException {
		return FileUtils.read(project.getFile(name).getContents());
	}

	@BeforeClass
	public static void oneTimeSetup() {
		List<SWTBotView> views = bot.views();
		for (SWTBotView view : views) {
			if (view.getTitle().equals("Welcome"))
				view.close();
		}
	}

}
