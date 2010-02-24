/*******************************************************************************
 * Copyright (c) 2004, 2010 MAKE Technologies Inc and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    MAKE Technologies Inc - initial API and implementation
 *    Mariot Chauvin <mariot.chauvin@obeo.fr> - refactoring
 *    Steve Monnier <steve.monnier@obeo.fr> - add mouseMoveDoubleClick action
 *    Nathalie Lepine <nathalie.lepine@obeo.fr> - add mouseMoveDoubleClick action
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.finders.PaletteFinder;
import org.eclipse.swtbot.eclipse.gef.finder.matchers.IsInstanceOf;
import org.eclipse.swtbot.eclipse.gef.finder.matchers.ToolEntryLabelMatcher;
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

	protected GraphicalViewer					graphicalViewer;

	protected EditDomain						editDomain;

	protected SWTBotGefFigureCanvas				canvas;

	private Map<EditPart, SWTBotGefEditPart>	editPartMapping	= new WeakHashMap<EditPart, SWTBotGefEditPart>();

	/**
	 * Create a new bot GEF editor instance.
	 * 
	 * @param reference the editor reference
	 * @param bot the workbench bot
	 * @throws WidgetNotFoundException if widget could not be found
	 */
	public SWTBotGefEditor(final IEditorReference reference, final SWTWorkbenchBot bot) throws WidgetNotFoundException {
		super(reference, bot);
		init();
	}

	/**
	 * clear the cache of edit parts
	 */
	public void clear() {
		editPartMapping.clear();
	}

	protected void init() throws WidgetNotFoundException {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				final IEditorPart editor = partReference.getEditor(true);
				graphicalViewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
				final Control control = graphicalViewer.getControl();
				if (control instanceof FigureCanvas) {
					canvas = new SWTBotGefFigureCanvas((FigureCanvas) control);
				}
				editDomain = graphicalViewer.getEditDomain();
			}
		});

		if (graphicalViewer == null) {
			throw new WidgetNotFoundException("Editor does not adapt to a GraphicalViewer");
		}
	}

	public SWTBotGefEditPart mainEditPart() throws WidgetNotFoundException {
		List<SWTBotGefEditPart> children = rootEditPart().children();
		if (children.size() != 1) {
			throw new WidgetNotFoundException(String.format("Root edit part has %s children", children.size()));
		}
		return children.get(0);
	}

	/**
	 * retrieve the root edit part.
	 * 
	 * @return the root edit part
	 * @throws WidgetNotFoundException if root edit part could not be found
	 * @see {@link GraphicalViewer#getRootEditPart()}
	 */
	public SWTBotGefEditPart rootEditPart() throws WidgetNotFoundException {
		Object o = UIThreadRunnable.syncExec(new Result<Object>() {
			public Object run() {
				return createEditPart(graphicalViewer.getRootEditPart());
			}
		});
		if (o instanceof WidgetNotFoundException) {
			throw (WidgetNotFoundException) o;
		}
		return (SWTBotGefEditPart) o;
	}

	/**
	 * lazily creates a {@link SWTBotGefEditPart} if this edit part does not exist yet. If an instance encapsulating the
	 * specified edit part has been created before, that instance is returned.
	 * 
	 * @param part the edit part to create a {@link SWTBotGefEditPart} for
	 * @return the created {@link SWTBotGefEditPart}
	 */
	protected SWTBotGefEditPart createEditPart(final EditPart part) {
		SWTBotGefEditPart editPart = editPartMapping.get(part);
		if (editPart == null) {
			if (part instanceof ConnectionEditPart) {
				editPart = new SWTBotGefConnectionEditPart(this, (ConnectionEditPart) part);
			} else {
				editPart = new SWTBotGefEditPart(this, part);
			}
			editPartMapping.put(part, editPart);
		}
		return editPart;
	}

	/**
	 * lazily creates a {@link SWTBotGefConnectionEditPart} if this edit part does not exist yet. If an instance
	 * encapsulating the specified edit part has been created before, that instance is returned.
	 * 
	 * @param part a connection edit part connecting graphical nodes
	 * @return a {@link SWTBotGefConnectionEditPart} encapsulating the connection edit part
	 */
	protected SWTBotGefConnectionEditPart createEditPart(ConnectionEditPart part) {
		return (SWTBotGefConnectionEditPart) createEditPart((EditPart) part);
	}

	/**
	 * Activate the default tool.
	 */
	public void activateDefaultTool() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				final EditDomain editDomain = getEditDomain();
				editDomain.setActiveTool(editDomain.getDefaultTool());
			}
		});
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
		activateTool(Pattern.compile(Pattern.quote(label)), 0);
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
		activateTool(Pattern.compile(Pattern.quote(label)), index);
		return this;
	}

	private SWTBotGefEditor activateTool(final Pattern labelMatcher, final int index) throws WidgetNotFoundException {
		final WidgetNotFoundException[] exception = new WidgetNotFoundException[1];
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				final EditDomain editDomain = getEditDomain();
				final List<PaletteEntry> entries = new PaletteFinder(editDomain).findEntries(new ToolEntryLabelMatcher(labelMatcher));
				if (entries.size() > 0) {
					final PaletteEntry paletteEntry = entries.get(index);
					if (paletteEntry instanceof ToolEntry) {
						editDomain.getPaletteViewer().setActiveTool((ToolEntry) paletteEntry);
					} else {
						exception[0] = new WidgetNotFoundException(String.format("%s is not a tool entry, it's a %s", labelMatcher
								.toString(), paletteEntry.getClass().getName()));
					}
				} else {
					exception[0] = new WidgetNotFoundException(labelMatcher.toString());
				}
			}
		});
		if (exception[0] != null) {
			throw exception[0];
		}
		return this;
	}

	/**
	 * call on UI thread only
	 */
	private EditDomain getEditDomain() {
		return editDomain;
	}

	/**
	 * type the given text into the graphical editor, presuming that it is already in 'direct edit' mode.
	 * 
	 * @param text the text to type.
	 * @throws WidgetNotFoundException
	 */
	public void directEditType(String text) throws WidgetNotFoundException {
		/* wait until text widget appears */
		bot.text();
		/* find it now */
		List<Text> controls = bot.getFinder().findControls(getWidget(), new IsInstanceOf<Text>(Text.class), true);
		if (controls.size() == 1) {
			final Text textControl = controls.get(0);
			canvas.typeText(textControl, text);
		} else {
			throw new WidgetNotFoundException(String.format(
					"Expected to find one text control, but found %s.  Is the editor in direct-edit mode?", controls.size()));
		}
	}

	/**
	 * @param matcher the matcher that matches on {@link org.eclipse.gef.EditPart}
	 * @return a collection of {@link SWTBotGefEditPart}
	 * @throws WidgetNotFoundException
	 */
	public List<SWTBotGefEditPart> editParts(Matcher<? extends EditPart> matcher) throws WidgetNotFoundException {
		return rootEditPart().descendants(matcher);
	}

	/**
	 * Get the canvas to do low-level operations.
	 * 
	 * @return the canvas
	 */
	protected SWTBotGefFigureCanvas getCanvas() {
		return canvas;
	}

	protected Control getControl() {
		return graphicalViewer.getControl();
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
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				List<EditPart> selectParts = new ArrayList<EditPart>(parts.size());
				for (SWTBotGefEditPart p : parts) {
					selectParts.add(p.part);
				}
				graphicalViewer.setFocus(selectParts.get(0));
				graphicalViewer.setSelection(new StructuredSelection(selectParts));
			}
		});
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
		new SWTBotGefContextMenu(getControl(), text).click();
		return this;
	}

	/**
	 * Click on the editor at the specified location.
	 * 
	 * @param xPosition the x relative position
	 * @param yPosition the y relative position
	 */
	public void click(final int xPosition, final int yPosition) {
		canvas.mouseMoveLeftClick(xPosition, yPosition);
	}

	/**
	 * Click on the specified edit part at the top left hand corner of its bounds.
	 * 
	 * @param editPart the edit part to click on
	 */
	public void click(final SWTBotGefEditPart editPart) {
		Rectangle bounds = ((GraphicalEditPart) editPart.part()).getFigure().getBounds();
		click(bounds.x, bounds.y);
	}

	/**
	 * Click on the edit part which owns the specified label at the top left hand corner of its bounds.
	 * 
	 * @param label the label to retrieve edit part to click on
	 */
	public void click(final String label) {
		SWTBotGefEditPart selectedEP = getEditPart(label);
		if (selectedEP == null) {
			throw new WidgetNotFoundException(String.format("Expected to find widget %s", label));
		}
		click(selectedEP);
	}

	/**
	 * Double click on the editor at the specified location.
	 * 
	 * @param xPosition the x relative position
	 * @param yPosition the y relative position
	 */
	public void doubleClick(final int xPosition, final int yPosition) {
		canvas.mouseMoveDoubleClick(xPosition, yPosition);
	}

	/**
	 *  Double click on the edit part which owns the specified label at the top left hand corner (with an offset) of its bounds.
	 * @param editPart the edit part to double click on
	 */
	public void doubleClick(final SWTBotGefEditPart editPart) {
		Rectangle bounds = ((GraphicalEditPart) editPart.part()).getFigure().getBounds();
		/*
		 * Note that a move is required before double clicking in order to update the mouse cursor with the target
		 * editpart. As we can not double click on the corner, we move the double click position
		 */
		int move = 3;
		doubleClick(bounds.x, bounds.y + move);
	}

	/**
	 * Double click on the edit part which owns the specified label at the top left hand corner (with an offset) of its bounds.
	 * 
	 * @param label the label to retrieve edit part to double click on
	 */
	public void doubleClick(final String label) {
		SWTBotGefEditPart selectedEP = getEditPart(label);
		if (selectedEP == null) {
			throw new WidgetNotFoundException(String.format("Expected to find widget %s", label));
		}
		doubleClick(selectedEP);
	}

	/**
	 * Drag and drop the specified edit part to the specified location. 
	 * @param editPart the edit part to drag and drop 
	 * @param toXPosition the x relative location
	 * @param toYPosition the y relative location
	 */
	public void drag(final SWTBotGefEditPart editPart, final int toXPosition, final int toYPosition) {
		Rectangle bounds = ((GraphicalEditPart) editPart.part()).getFigure().getBounds();
		canvas.mouseDrag(bounds.x, bounds.y, toXPosition, toYPosition);
	}

	/**
	 * Drag and drop the edit part which owns the specified label to the specified location
	 * @param label the label to retrieve the edit part to drag and drop
	 * @param toXPosition the x relative position
	 * @param toYPosition the y relative position
	 */
	public void drag(final String label, final int toXPosition, final int toYPosition) {
		SWTBotGefEditPart selectedEP = getEditPart(label);
		if (selectedEP == null) {
			throw new WidgetNotFoundException(String.format("Expected to find widget %s", label));
		}
		drag(selectedEP, toXPosition, toYPosition);
	}

	/**
	 * select the edit part with the label as a single selection.
	 */
	public SWTBotGefEditor select(String label) {
		SWTBotGefEditPart selectedEP = getEditPart(label);
		if (selectedEP == null) {
			throw new WidgetNotFoundException(String.format("Expected to find widget %s", label));
		}
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(selectedEP);
		return select(selectedEP);
	}

	/**
	 * get this edit part with the label as a single selection.
	 */
	public SWTBotGefEditPart getEditPart(String label) {
		List<SWTBotGefEditPart> allEditParts = mainEditPart().children();
		allEditParts.addAll(mainEditPart().sourceConnections());
		return getEditpart(label, allEditParts);
	}

	// FIXME should moved in a finder
	@Deprecated
	/*
	 * * get this edit part with the label as a single selection
	 */
	public SWTBotGefEditPart getEditpart(String label, List<SWTBotGefEditPart> allEditParts) {
		for (SWTBotGefEditPart child : allEditParts) {
			IFigure figure = ((GraphicalEditPart) child.part()).getFigure();

			if (isLabel(figure, label)) {
				return child;
			}

			SWTBotGefEditPart childEditPart = getEditPart(child, label);
			if (childEditPart != null) {
				return childEditPart;
			}

			if (findLabelFigure(figure, label))
				return child;
		}
		return null;
	}

	/**
	 * get this edit part with the label as a single selection
	 */
	private SWTBotGefEditPart getEditPart(SWTBotGefEditPart editPart, String label) {
		if (editPart.children().isEmpty() && findLabelFigure(((GraphicalEditPart) editPart.part()).getFigure(), label)) {
			return editPart;
		}

		List<SWTBotGefEditPart> allEditParts = editPart.children();
		allEditParts.addAll(editPart.sourceConnections());
		return getEditpart(label, allEditParts);
	}

	// FIXME should moved in a finder
	/**
	 * @return if the figure is a label
	 */
	private boolean isLabel(IFigure figure, String label) {
		// case 1 : gef label
		if ((figure instanceof Label && ((Label) figure).getText().equals(label))) {
			return true;
		}

		// case 2 : no gef label
		if ((figure instanceof TextFlow && ((TextFlow) figure).getText().equals(label))) {
			return true;
		}
		return false;
	}

	// FIXME should moved in a finder
	/**
	 * @return if the figure or all its children contain the label
	 */
	private boolean findLabelFigure(IFigure figure, String label) {
		if (isLabel(figure, label)) {
			return true;
		}
		for (Object figureChild : figure.getChildren()) {
			if (isLabel((IFigure) figureChild, label) || findLabelFigure((IFigure) figureChild, label)) {
				return true;
			}
		}
		return false;
	}

	/* deprecated methods -> keeped for compatibility */

	@Deprecated
	public void mouseDrag(final int fromXPosition, final int fromYPosition, final int toXPosition, final int toYPosition) {
		canvas.mouseDrag(fromXPosition, fromYPosition, toXPosition, toYPosition);
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
