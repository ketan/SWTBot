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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.examples.logic.test.CreateLogicDiagram;
import org.eclipse.gef.examples.logic.test.NewEmptyEmfProject;
import org.eclipse.gef.examples.logicdesigner.edit.LogicLabelEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;

public class AllTests extends SWTBotGefForUnitTestsTestCase {

	private static final String PROJECT_NAME= "Test";
	
	private static final String FILE_NAME= "test.logic";
	
	private NewEmptyEmfProject emfProject = new NewEmptyEmfProject();
	
	private CreateLogicDiagram logicDiagram = new CreateLogicDiagram();

	private SWTBotGefForUnitTestsEditor editor;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		closeWelcomePage();
		emfProject.createProject(PROJECT_NAME);
		logicDiagram.createFile(PROJECT_NAME, FILE_NAME);
		editor = bot.gefEditor(FILE_NAME);
	}

	@Override
	protected void tearDown() throws Exception {		
		//saveCurrentEditor();
		editor.close();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		project.delete(true, null);
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
		assertEquals("Circuit", getActiveToolLabel());
		
		editor.activateTool("Connection");
		assertEquals("Connection", getActiveToolLabel());
		
		editor.activateTool("Or Gate");
		assertEquals("Or Gate", getActiveToolLabel());

		editor.activateTool("Circuit");
		assertEquals("Circuit", getActiveToolLabel());
	}

	private String getActiveToolLabel() {
		return  editor.forTestGetEditDomain().getPaletteViewer().getActiveTool().getLabel();
	}
	
	public void testGetEditPartWithLabelOnCanvas() throws Exception {
		editor.activateTool("Label");
		editor.click(10, 10);
		SWTBotGefEditPart botPart = editor.getEditPart("Label");
		assertNotNull(botPart);
		assertTrue(botPart.part() instanceof LogicLabelEditPart);
	}
	

	public void testGetEditPartWithLabelInsideNode() throws Exception {	
		editor.activateTool("Circuit");
		editor.click(10, 10);		
		editor.activateTool("Label");
		editor.click(10 + 3, 10 + 3);
		
		SWTBotGefEditPart botPart = editor.getEditPart("Label");
		assertNotNull(botPart);
		assertTrue(botPart.part() instanceof LogicLabelEditPart);
	}	
	

	public void testDrag() throws Exception {
		editor.activateTool("Label");
		editor.click(10, 10);				
		editor.drag("Label", 100, 110);
		
		Rectangle bounds = ((GraphicalEditPart) editor.getEditPart("Label").part()).getFigure().getBounds();
		assertEquals(100, bounds.x);
		assertEquals(110, bounds.y);
	}

	
	public void testDirectEdit() throws Exception {
		//TODO
	}
	
	/* Deprecated methods */
	
	public void testDeprecatedGetEditPartWithLabelOnCanvas() throws Exception {
		editor.activateTool("Label");
		editor.mouseMoveLeftClick(10, 10);
		SWTBotGefEditPart botPart = editor.getEditPart("Label");
		assertNotNull(botPart);
		assertTrue(botPart.part() instanceof LogicLabelEditPart);
	}
	

	public void testDeprecatedGetEditPartWithLabelInsideNode() throws Exception {	
		editor.activateTool("Circuit");
		editor.mouseMoveLeftClick(10, 10);		
		editor.activateTool("Label");
		editor.mouseMoveLeftClick(10 + 3, 10 + 3);
		
		SWTBotGefEditPart botPart = editor.getEditPart("Label");
		assertNotNull(botPart);
		assertTrue(botPart.part() instanceof LogicLabelEditPart);
	}	
	

	public void testDeprecatedDrag() throws Exception {
		editor.activateTool("Label");
		editor.mouseMoveLeftClick(10, 10);				
		editor.mouseDrag("Label", 100, 110);
		
		Rectangle bounds = ((GraphicalEditPart) editor.getEditPart("Label").part()).getFigure().getBounds();
		assertEquals(100, bounds.x);
		assertEquals(110, bounds.y);
	}

	
	
	


	
	
}
