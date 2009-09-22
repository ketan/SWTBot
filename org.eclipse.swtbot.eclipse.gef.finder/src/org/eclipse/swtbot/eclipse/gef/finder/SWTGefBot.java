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

package org.eclipse.swtbot.eclipse.gef.finder;

import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPartName;
import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForEditor;
import static org.hamcrest.Matchers.allOf;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.WaitForEditor;
import org.eclipse.swtbot.eclipse.gef.finder.matchers.IsInstanceOf;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;
import org.hamcrest.Matcher;

public class SWTGefBot extends SWTWorkbenchBot {
	
	/**
	 * Attempts to locate the Gef editor matching the given name. If no match is found an exception will be thrown. The name
	 * is the name as displayed on the editor's tab in eclipse.
	 * 
	 * @param fileName the name of the file.
	 * @return an editor for the specified fileName.
	 * @throws WidgetNotFoundException if the editor is not found.
	 */
	public SWTBotGefEditor gefEditor(String fileName) throws WidgetNotFoundException {
		return gefEditor(fileName, 0);
	}
	
	/**
	 * Attempts to locate the editor matching the given name. If no match is found an exception will be thrown. The name
	 * is the name as displayed on the editor's tab in eclipse.
	 * 
	 * @param fileName the name of the file.
	 * @param index in case of multiple views with the same fileName.
	 * @return an editor for the specified fileName.
	 * @throws WidgetNotFoundException if the editor is not found.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotGefEditor gefEditor(String fileName, int index) throws WidgetNotFoundException {
		Matcher<IEditorReference> withPartName = withPartName(fileName);
		Matcher<IEditorReference> matcher = allOf(IsInstanceOf.instanceOf(IEditorReference.class), withPartName);
		WaitForEditor waitForEditor = waitForEditor(matcher);
		waitUntilWidgetAppears(waitForEditor);
		return new SWTBotGefEditor(waitForEditor.get(index), this);
	}
	
	
	
}
