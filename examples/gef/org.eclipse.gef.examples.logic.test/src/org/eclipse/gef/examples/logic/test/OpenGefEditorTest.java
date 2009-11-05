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

package org.eclipse.gef.examples.logic.test;

import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;

public class OpenGefEditorTest extends SWTBotGefTestCase {


	private NewEmptyEmfProject emfProject = new NewEmptyEmfProject();
	private CreateLogicDiagram logicDiagram = new CreateLogicDiagram();



	@Override
	protected void setUp() throws Exception {
		super.setUp();
		closeWelcomePage();
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


	public void testCreateMFile() throws Exception {
		emfProject.createProject("testM");
		logicDiagram.createMFile("testM", "test.logicm");
		final SWTBotGefEditor editor = bot.gefEditor("Logic M Graphical Editor");	
		createContents(editor);
		saveCurrentEditor();
		bot.sleep(3000);	
	}

	public void testCreateFile() throws Exception {

		emfProject.createProject("test");
		logicDiagram.createFile("test", "test.logic");
		SWTBotGefEditor editor = bot.gefEditor("test.logic");
		createContents(editor);
		saveCurrentEditor();
		bot.sleep(3000);	
	}

	private void createContents(final SWTBotGefEditor editor) {

		editor.activateTool("Circuit");
		editor.mouseDrag(55, 55, 150, 100);

		editor.activateTool("Circuit");
		editor.mouseMoveLeftClick(150, 150);

		editor.activateTool("Connection");
		editor.mouseMoveLeftClick(150, 150);
		editor.mouseMoveLeftClick(55, 55);


		editor.activateTool("Or Gate");
		editor.mouseMoveLeftClick(200, 200);

		editor.activateTool("Connection");
		editor.mouseMoveLeftClick(150, 150);
		editor.mouseMoveLeftClick(200, 200);


		editor.mouseMoveLeftClick(200, 150);
		editor.mouseMoveLeftClick(210, 200);


		editor.mouseMoveLeftClick(200, 200);
		editor.mouseMoveLeftClick(230, 230);

	}
}
