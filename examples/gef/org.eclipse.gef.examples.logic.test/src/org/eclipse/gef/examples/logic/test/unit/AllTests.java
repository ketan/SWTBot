/*******************************************************************************
 * Copyright (c) 2009 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/

package org.eclipse.gef.examples.logic.test.unit;

import org.eclipse.gef.examples.logic.test.CreateLogicDiagram;
import org.eclipse.gef.examples.logic.test.NewEmptyEmfProject;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;

public class AllTests extends SWTBotGefForUnitTestsTestCase {


	private NewEmptyEmfProject emfProject = new NewEmptyEmfProject();
	
	private CreateLogicDiagram logicDiagram = new CreateLogicDiagram();

	private SWTBotGefForUnitTestsEditor editor;
	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		closeWelcomePage();
		emfProject.createProject("test");
		logicDiagram.createFile("test", "test.logic");
		editor = bot.gefEditor("test.logic");
	}

	@Override
	protected void tearDown() throws Exception {
		saveCurrentEditor();
		super.tearDown();
	}

	private void closeWelcomePage() {
		try {
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
			// do nothing
		}
	}

	public void saveCurrentEditor() throws Exception {
		bot.menu("File").menu("Save").click();
	}
	
	public void testActivateTool() {	
		editor.activateTool("Circuit");
		editor.forTestGetEditDomain().getActiveTool();
		
	}




}
