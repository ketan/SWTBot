/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder;

import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPartName;
import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForEditor;
import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.waits.WaitForEditor;
import org.eclipse.swtbot.eclipse.finder.waits.WaitForView;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPartReference;
import org.hamcrest.Matcher;

/**
 * This extends the {@link SWTWorkbenchBot} and adds specific capabilities for writing Eclipse IDE tests.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @deprecated use {@link SWTWorkbenchBot}
 */
public class SWTEclipseBot extends SWTWorkbenchBot {

	/**
	 * Constructs an eclipse bot.
	 */
	public SWTEclipseBot() {
		super();
	}

	/**
	 * Attempts to locate the editor matching the given name. If no match is found an exception will be thrown. The name
	 * is the name as displayed on the editor's tab in eclipse.
	 * 
	 * @param fileName the name of the file.
	 * @return an editor for the specified fileName.
	 * @throws WidgetNotFoundException if the editor is not found.
	 */
	public SWTBotEclipseEditor editor(String fileName) throws WidgetNotFoundException {
		return editor(fileName, 0);
	}

	/**
	 * Attempts to locate the editor matching the given name. If no match is found an exception will be thrown. The name
	 * is the name as displayed on the editor's tab in eclipse.
	 * 
	 * @param fileName the name of the file.
	 * @param index in case of multiple views with the same fileName.
	 * @return an editor for the specified fileName.
	 * @throws WidgetNotFoundException if the editor is not found.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public SWTBotEclipseEditor editor(String fileName, int index) throws WidgetNotFoundException {
		Matcher matcher = allOf(instanceOf(IEditorReference.class), withPartName(fileName));
		WaitForEditor waitForEditor = waitForEditor(matcher);
		waitUntilWidgetAppears(waitForEditor);
		return new SWTBotEclipseEditor(waitForEditor.get(index), this);
	}

	/**
	 * Attempts to find the view matching the given label. If no match is found then an exception will be thrown. The
	 * name is the name as displayed on the editor's tab in eclipse.
	 * 
	 * @param label the label of the view.
	 * @return a view with the specified label.
	 * @throws WidgetNotFoundException if the view is not found.
	 */
	public SWTBotView view(String label) throws WidgetNotFoundException {
		return view(label, 0);
	}

	/**
	 * Attempts to find the view matching the given label. If no match is found then an exception will be thrown. The
	 * name is the name as displayed on the editor's tab in eclipse.
	 * 
	 * @param label the label of the view.
	 * @param index in case of multiple views with the same label.
	 * @return a view with the specified label.
	 * @throws WidgetNotFoundException if the view is not found.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public SWTBotView view(String label, int index) throws WidgetNotFoundException {
		Matcher matcher = allOf(instanceOf(IViewReference.class), withPartName(label));
		WaitForView waitForView = waitForView(matcher);
		waitUntilWidgetAppears(waitForView);
		return new SWTBotView(waitForView.get(index), this);
	}

	/**
	 * Returns the list of all the open editors found in the active workbench.
	 * 
	 * @return all the editors in the workbench.
	 * @throws WidgetNotFoundException if there are errors finding editors.
	 */
	@SuppressWarnings("unchecked")
	public List<SWTBotEclipseEditor> editors() throws WidgetNotFoundException {
		Matcher matcher = allOf(instanceOf(IEditorReference.class));
		WaitForEditor waitForEditor = waitForEditor(matcher);
		waitUntilWidgetAppears(waitForEditor);

		List<IEditorReference> editors = waitForEditor.getAllMatches();
		List<SWTBotEclipseEditor> result = new ArrayList<SWTBotEclipseEditor>(editors.size());

		for (IWorkbenchPartReference editor : editors) {
			result.add(new SWTBotEclipseEditor((IEditorReference) editor, this));
		}
		return result;
	}

	/**
	 * Returns the list of all the open views found in the active workbench.
	 * 
	 * @return all the views in the workbench.
	 * @throws WidgetNotFoundException if the views are not found.
	 */
	@SuppressWarnings("unchecked")
	public List<SWTBotView> views() throws WidgetNotFoundException {
		Matcher matcher = allOf(instanceOf(IViewReference.class));
		WaitForView waitForView = waitForView(matcher);
		waitUntilWidgetAppears(waitForView);

		List<IViewReference> editors = waitForView.getAllMatches();
		List<SWTBotView> result = new ArrayList<SWTBotView>(editors.size());

		for (IWorkbenchPartReference editor : editors) {
			result.add(new SWTBotView((IViewReference) editor, this));
		}
		return result;
	}

	/**
	 * Return the active editor.
	 * 
	 * @return the active editor, if any
	 * @throws WidgetNotFoundException if there is no active editor.
	 * @since 1.1
	 */
	public SWTBotEclipseEditor activeEditor() throws WidgetNotFoundException {
		return super.activeEditor().toTextEditor();
	}
	
}
