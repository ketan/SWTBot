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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class ProjectSettingValidatorTest {

	private static final class StubWizardSettings implements WizardPageSettings {
		private boolean	complete;
		private String	newMessage;

		public void setPageComplete(boolean complete) {
			this.complete = complete;
		}

		public void setErrorMessage(String newMessage) {
			this.newMessage = newMessage;
		}
	}

	@Test
	public void canValidateWhenEverythingIsCorrect() throws Exception {
		StubWizardSettings page = new StubWizardSettings();
		ProjectSettingValidator validator = new ProjectSettingValidator("org.eclipse.plugin", "foo plugin", "1.0.0", new ArrayList<String>(), page);
		validator.validate();
		assertTrue(page.complete);
		assertEquals(null, page.newMessage);
	}

	@Test
	public void canValidateInValidPluginId() throws Exception {
		StubWizardSettings page = new StubWizardSettings();
		ProjectSettingValidator validator = new ProjectSettingValidator("org.eclipse.plugin*", "foo plugin", "1.0.0",
				new ArrayList<String>(), page);
		validator.validate();
		assertFalse(page.complete);
		assertEquals("Invalid plugin id! Legal characters are A-Z a-z 0-9 . _ -", page.newMessage);
	}
	
	@Test
	public void canValidateInValidPluginVersion() throws Exception {
		StubWizardSettings page = new StubWizardSettings();
		ProjectSettingValidator validator = new ProjectSettingValidator("org.eclipse.plugin", "foo plugin", "-1.0.0",
				new ArrayList<String>(), page);
		validator.validate();
		assertFalse(page.complete);
		assertEquals("The specified version does not have the correct format (major.minor.micro.qualifier) or contains invalid characters!", page.newMessage);
	}
	
	@Test
	public void canValidateInValidPluginName() throws Exception {
		StubWizardSettings page = new StubWizardSettings();
		ProjectSettingValidator validator = new ProjectSettingValidator("org.eclipse.plugin", "", "-1.0.0",
				new ArrayList<String>(), page);
		validator.validate();
		assertFalse(page.complete);
		assertEquals("Plugin name cannot be empty!", page.newMessage);
	}
	
	@Test
	public void canValidateInValidProjectName() throws Exception {
		StubWizardSettings page = new StubWizardSettings();
		ArrayList<String> projects = new ArrayList<String>();
		projects.add("foo plugin");
		ProjectSettingValidator validator = new ProjectSettingValidator("org.eclipse.plugin", "foo plugin", "1.0.0",
				projects, page);
		validator.validate();
		assertFalse(page.complete);
		assertEquals("A project by that name already exists!", page.newMessage);
	}

}
