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
package org.eclipse.swtbot.forms.finder.test.widgets;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.forms.finder.finders.SWTFormsBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.junit.After;
import org.junit.BeforeClass;

public abstract class AbstractSWTBotFormsTest {

	protected static SWTFormsBot		bot			= new SWTFormsBot();
	protected static SWTWorkbenchBot	workbench	= new SWTWorkbenchBot();

	@BeforeClass
	public static void beforeClass() {
		closeWelcomePage();
	}

	private static void closeWelcomePage() {
		try {
			System.setProperty("org.eclipse.swtbot.search.timeout", "0");
			workbench.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
			// do nothing
		} finally {
			System.setProperty("org.eclipse.swtbot.search.timeout", "5000");
		}
	}

	@After
	public void tearDown() throws Exception {
		saveAndCloseAllEditors();
	}

	/**
	 * @throws WidgetNotFoundException
	 */
	private void saveAndCloseAllEditors() {
		List<? extends SWTBotEditor> editors = workbench.editors();
		for (SWTBotEditor editor : editors) {
			editor.saveAndClose();
		}
	}
}
