/*******************************************************************************
 * Copyright (c) 2004, 2010 MAKE Technologies Inc and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    MAKE Technologies Inc - initial API and implementation
 *    Mariot Chauvin <mariot.chauvin@obeo.fr> - Improvements and bug fixes
 *    Steve Monnier <steve.monnier@obeo.fr> - Improvements and bug fixes
 *    Nathalie Lepine <nathalie.lepine@obeo.fr> - Improvements and bug fixes
 *    Pascal Gelinas <pascal.gelinas @nuecho.com> - Improvements and bug fixes
 *    Mickael Istria <mickael.istria@bonitasoft.com> - Improvements and bug fixes
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.widgets;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.hamcrest.Matcher;

/**
 * represent a graphical editor that uses the GEF framework. The underlying editor must adapt to a
 * {@link GraphicalViewer} which must extend {@link GraphicalViewerImpl} (this is the default for all GEF-based editors
 * that extend {@link org.eclipse.gef.ui.parts.GraphicalEditor}). Unlike most of SWTBot, this editor uses
 * {@link org.eclipse.gef.EditPart edit parts} to target UI events instead of SWT widgets. This is due to the fact that
 * GEF editors paint on a canvas and rarely use widgets at all.
 * 
 * @author David Green
 */
public class SWTBotGefEditor extends SWTBotEditor {

	protected final SWTBotGefViewer viewer;

	/**
	 * Create a new bot GEF editor instance.
	 * 
	 * @param reference the editor reference
	 * @param bot the workbench bot
	 * @throws WidgetNotFoundException if widget could not be found
	 */
	public SWTBotGefEditor(final IEditorReference reference, final SWTWorkbenchBot bot) throws WidgetNotFoundException {
		super(reference, bot);
		GraphicalViewer graphicalViewer = UIThreadRunnable.syncExec(new Result<GraphicalViewer>() {
			public GraphicalViewer run() {
				final IEditorPart editor = partReference.getEditor(true);
				return (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
			}
		});
		viewer = new SWTBotGefViewer(graphicalViewer);
		viewer.init();
	}

	/**
	 * Get the wrapped SWTBotGefViewer instance.
	 * @return a SWTBotGefViewer instance
	 */
	public SWTBotGefViewer getSWTBotGefViewer() {
		return viewer;
	}
	
	/**
	 * clear the cache of edit parts
	 */
	public void clear() {
		viewer.clear();
	}

	public SWTBotGefEditPart mainEditPart() throws WidgetNotFoundException {
		return viewer.mainEditPart();
	}

	/**
	 * retrieve the root edit part.
	 * 
	 * @return the root edit part
	 * @throws WidgetNotFoundException if root edit part could not be found
	 * @see {@link GraphicalViewer#getRootEditPart()}
	 */
	public SWTBotGefEditPart rootEditPart() throws WidgetNotFoundException {
		return viewer.rootEditPart();
	}

	/**
	 * Get the selected edit parts.
	 * @return the selected edit parts
	 */
	public List<SWTBotGefEditPart> selectedEditParts() {
		return viewer.selectedEditParts();
	}
	
	/**
	 * lazily creates a {@link SWTBotGefEditPart} if this edit part does not exist yet. If an instance encapsulating the
	 * specified edit part has been created before, that instance is returned.
	 * 
	 * @param part the edit part to create a {@link SWTBotGefEditPart} for
	 * @return the created {@link SWTBotGefEditPart}
	 */
	protected SWTBotGefEditPart createEditPart(final EditPart part) {
		return viewer.createEditPart(part);
	}

	/**
	 * lazily creates a {@link SWTBotGefConnectionEditPart} if this edit part does not exist yet. If an instance
	 * encapsulating the specified edit part has been created before, that instance is returned.
	 * 
	 * @param part a connection edit part connecting graphical nodes
	 * @return a {@link SWTBotGefConnectionEditPart} encapsulating the connection edit part
	 */
	protected SWTBotGefConnectionEditPart createEditPart(ConnectionEditPart part) {
		return viewer.createEditPart(part);
	}

	/**
	 * Get the active tool.
	 * @return the active tool
	 */
	public ToolEntry getActiveTool() {
		return viewer.getActiveTool();
	}
	
	/**
	 * Activate the default tool.
	 */
	public void activateDefaultTool() {
		viewer.activateDefaultTool();
	}

	/**
	 * Activate the tool with the specified label. If there is many tools with the same label the first one will be
	 * used. See {@link SWTBotGefEditor#activateTool(String, int)}
	 * 
	 * @param label the label of the tool to activate
	 * @return the editor bot
	 * @throws WidgetNotFoundException if the tool with label specified could not be found
	 */
	public SWTBotGefEditor activateTool(final String label) throws WidgetNotFoundException {
		viewer.activateTool(label);
		return this;
	}

	/**
	 * Activate the tool with the specified label and the specified index. This method should be used only if there is
	 * many tools with the same label. See {@link SWTBotGefEditor#activateTool(String)}
	 * 
	 * @param label the label of the tool to activate
	 * @param index the index to use in order to make the selection.
	 * @return the editor bot
	 * @throws WidgetNotFoundException if the tool with label specified could not be found
	 */
	public SWTBotGefEditor activateTool(final String label, int index) throws WidgetNotFoundException {
		viewer.activateTool(label, index);
		return this;
	}

	/**
	 * type the given text into the graphical editor, presuming that it is already in 'direct edit' mode.
	 * 
	 * @param text the text to type.
	 * @throws WidgetNotFoundException
	 */
	public void directEditType(String text) throws WidgetNotFoundException {
		viewer.directEditType(text);
	}

	/**
	 * @param matcher the matcher that matches on {@link org.eclipse.gef.EditPart}
	 * @return a collection of {@link SWTBotGefEditPart}
	 * @throws WidgetNotFoundException
	 */
	public List<SWTBotGefEditPart> editParts(Matcher<? extends EditPart> matcher) throws WidgetNotFoundException {
		return viewer.editParts(matcher);
	}

	/**
	 * select this edit part as a single selection
	 */
	public SWTBotGefEditor select(SWTBotGefEditPart... parts) {
		return select(Arrays.asList(parts));
	}

	/**
	 * select this edit part as a single selection
	 */
	public SWTBotGefEditor select(final Collection<SWTBotGefEditPart> parts) {
		viewer.select(parts);
		return this;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor#setFocus()
	 */
	@Override
	public void setFocus() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				IEditorPart editor = partReference.getEditor(false);
				if (editor != null) {
					editor.setFocus();
				}
			}
		});
	}

	public SWTBotGefEditor clickContextMenu(String text) throws WidgetNotFoundException {
		viewer.clickContextMenu(text);
		return this;
	}

	/**
	 * Click on the editor at the specified location.
	 * 
	 * @param xPosition the x relative position
	 * @param yPosition the y relative position
	 */
	public void click(final int xPosition, final int yPosition) {
		viewer.click(xPosition, yPosition);
	}

	/**
	 * Click on the specified edit part at the top left hand corner of its bounds.
	 * 
	 * @param editPart the edit part to click on
	 */
	public void click(final SWTBotGefEditPart editPart) {
		viewer.click(editPart);
	}

	/**
	 * Click on the edit part which owns the specified label at the top left hand corner of its bounds.
	 * 
	 * @param label the label to retrieve edit part to click on
	 */
	public void click(final String label) {
		viewer.click(label);
	}

	/**
	 * Double click on the editor at the specified location.
	 * 
	 * @param xPosition the x relative position
	 * @param yPosition the y relative position
	 */
	public void doubleClick(final int xPosition, final int yPosition) {
		viewer.doubleClick(xPosition, yPosition);
	}

	/**
	 * Double click on the edit part which owns the specified label at the top left hand corner (with an offset) of its
	 * bounds.
	 * 
	 * @param editPart the edit part to double click on
	 */
	public void doubleClick(final SWTBotGefEditPart editPart) {
		viewer.doubleClick(editPart);
	}

	/**
	 * Double click on the edit part which owns the specified label at the top left hand corner (with an offset) of its
	 * bounds.
	 * 
	 * @param label the label to retrieve edit part to double click on
	 */
	public void doubleClick(final String label) {
		viewer.doubleClick(label);
	}

	/**
	 * Drag and drop from the specified to the specified location.
	 * 
	 * @param toXPosition the x relative location
	 * @param toYPosition the y relative location
	 */
	public void drag(final int fromXPosition, final int fromYPosition, final int toXPosition, final int toYPosition) {
		viewer.drag(fromXPosition, fromYPosition, toXPosition, toYPosition);
	}

	/**
	 * Drag and drop the specified edit part to the specified location.
	 * 
	 * @param editPart the edit part to drag and drop
	 * @param toXPosition the x relative location
	 * @param toYPosition the y relative location
	 */
	public void drag(final SWTBotGefEditPart editPart, final int toXPosition, final int toYPosition) {
		viewer.drag(editPart, toXPosition, toYPosition);
	}

	/**
	 * Drag and drop the edit part which owns the specified label to the specified location
	 * 
	 * @param label the label to retrieve the edit part to drag and drop
	 * @param toXPosition the x relative position
	 * @param toYPosition the y relative position
	 */
	public void drag(final String label, final int toXPosition, final int toYPosition) {
		viewer.drag(label, toXPosition, toYPosition);
	}

	/**
	 * select the edit part with the label as a single selection.
	 */
	public SWTBotGefEditor select(String label) {
		viewer.select(label);
		return this;
	}

	/**
	 * get this edit part with the label as a single selection.
	 */
	public SWTBotGefEditPart getEditPart(String label) {
		return viewer.getEditPart(label);
	}

	// FIXME should moved in a finder
	@Deprecated
	/*
	 * * get this edit part with the label as a single selection
	 */
	public SWTBotGefEditPart getEditpart(String label, List<SWTBotGefEditPart> allEditParts) {
		return viewer.getEditpart(label, allEditParts);
	}

	/* deprecated methods -> keeped for compatibility */

	@Deprecated
	public void mouseDrag(final int fromXPosition, final int fromYPosition, final int toXPosition, final int toYPosition) {
		drag(fromXPosition, fromYPosition, toXPosition, toYPosition);
	}

	@Deprecated
	public void mouseDrag(final String label, final int toXPosition, final int toYPosition) {
		drag(label, toXPosition, toYPosition);
	}

	@Deprecated
	public void mouseMoveDoubleClick(final int xPosition, final int yPosition) {
		doubleClick(xPosition, yPosition);
	}

	@Deprecated
	public void mouseMoveDoubleClick(final String label) {
		doubleClick(label);
	}

	@Deprecated
	public void mouseMoveLeftClick(final int xPosition, final int yPosition) {
		click(xPosition, yPosition);
	}

	@Deprecated
	public void mouseMoveLeftClick(String label) {
		click(label);
	}
}
