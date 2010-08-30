/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo
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
import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForView;
import static org.hamcrest.Matchers.allOf;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.WaitForEditor;
import org.eclipse.swtbot.eclipse.finder.waits.WaitForView;
import org.eclipse.swtbot.eclipse.gef.finder.matchers.IsInstanceOf;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefViewer;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
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
		return createEditor(waitForEditor.get(index), this);
	}
	
	/**
	 * Create the editor. Subclass if necessary to instance your own GEF editor.
	 * @param reference the editor reference
	 * @param bot the bot
	 * @return the created editor instance
	 */
	protected SWTBotGefEditor createEditor(final IEditorReference reference, final SWTWorkbenchBot bot) {
		return new SWTBotGefEditor(reference, bot);
	}

	/**
	 * Attempts to locate the view matching the given name. If no match is found an exception will be thrown. The name
	 * is the name as displayed on the view in eclipse.
	 * 
	 * @param viewName the name of the view.
	 * @return a view with the specified name.
	 * @throws WidgetNotFoundException if the view is not found.
	 */
	public SWTBotGefView gefView(String viewName) throws WidgetNotFoundException {
		return gefView(viewName, 0);
	}
	
	
	/**
	 * Attempts to locate the view matching the given name. If no match is found an exception will be thrown. The name
	 * is the name as displayed on the view in eclipse.
	 * 
	 * @param viewName the name of the view.
	 * @param index in case of multiple views with the same fileName.
	 * @return a view with the specified name.
	 * @throws WidgetNotFoundException if the view is not found.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotGefView gefView(String viewName, int index) throws WidgetNotFoundException {
		Matcher<IViewReference> withPartName = withPartName(viewName);
		Matcher<IViewReference> matcher = allOf(IsInstanceOf.instanceOf(IViewReference.class), withPartName);
		WaitForView waitForView = waitForView(matcher);
		waitUntilWidgetAppears(waitForView);
		return createView(waitForView.get(index), this);
	}

	
	/**
	 * Create the view. Subclass if necessary to instance your own GEF view.
	 * @param reference the view reference
	 * @param bot the bot
	 * @return the created view instance
	 */
	protected SWTBotGefView createView(final IViewReference reference, final SWTWorkbenchBot bot) {
		return new SWTBotGefView(reference, bot);
	}
		
	/**
	 * Attempts to locate a gef viewer that is embedded in a workbench part with the specified title.
	 * 
	 * @param partTitle the workbench part title
	 * @return The gef viewer
	 * @throws WidgetNotFoundException if a workbench part with the specified title cannot be found.
	 */
	public SWTBotGefViewer gefViewer(String partTitle) throws WidgetNotFoundException {
		
		Object editorOrView = null;
		try {
			editorOrView = gefEditor(partTitle);
		} catch (WidgetNotFoundException exception) {
			/* NOOP, means that it is not an editor. Search for a view instead then.*/
			try {
				editorOrView = gefView(partTitle);
			} catch (WidgetNotFoundException e) {
				/* It's not a view either! */
				throw new WidgetNotFoundException("Unable to find a part with title " + partTitle);
			}
		}
		if (editorOrView instanceof SWTBotGefEditor) {
			return ((SWTBotGefEditor) editorOrView).getSWTBotGefViewer();
		} else {
			return ((SWTBotGefView) editorOrView).getSWTBotGefViewer();
		}
	}
}
	