/*******************************************************************************
 * Copyright (c) 2004, 2010 MAKE Technologies Inc and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     MAKE Technologies Inc - initial API and implementation
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - Improvements and bug fixes
 *     Pascal Gelinas <pascal.gelinas @nuecho.com> - Improvements and bug fixes
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.widgets;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ScrollBar;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.hamcrest.Matcher;

/**
 * represent an edit part of a graphical viewer.
 * 
 * @author David Green
 * @see SWTBotGefEditor
 */
public class SWTBotGefEditPart {
	protected final EditPart		part;
	protected final SWTBotGefViewer	viewer;

	/**
	 * @param veiwer viewer
	 * @param parent the parent, or null if this is the root edit part
	 * @param part the GEF part
	 */
	SWTBotGefEditPart(final SWTBotGefViewer viewer, final EditPart part) {
		this.viewer = viewer;
		this.part = part;
	}

	/**
	 * get the parent, or null if this is the root edit part.
	 */
	public SWTBotGefEditPart parent() {
		return UIThreadRunnable.syncExec(new Result<SWTBotGefEditPart>() {
			public SWTBotGefEditPart run() {
				return viewer.createEditPart(part.getParent());
			}
		});
	}

	/**
	 * Get the children of this edit part.
	 * 
	 * @return the edit part's children
	 */
	@SuppressWarnings("unchecked")
	public List<SWTBotGefEditPart> children() {
		return UIThreadRunnable.syncExec(new Result<List<SWTBotGefEditPart>>() {
			public List<SWTBotGefEditPart> run() {
				List<SWTBotGefEditPart> children = new ArrayList<SWTBotGefEditPart>();
				for (org.eclipse.gef.EditPart child : ((List<org.eclipse.gef.EditPart>) part.getChildren())) {
					children.add(viewer.createEditPart(child));
				}
				return children;
			}
		});
	}

	/**
	 * find descendants that match.
	 * 
	 * @param matcher the matcher that matches against {@link org.eclipse.gef.EditPart}
	 * @return a list of matches or an empty list if there are none
	 */
	@SuppressWarnings("unchecked")
	public List<SWTBotGefEditPart> descendants(final Matcher<? extends EditPart> matcher) {
		return UIThreadRunnable.syncExec(new Result<List<SWTBotGefEditPart>>() {
			public List<SWTBotGefEditPart> run() {
				List<SWTBotGefEditPart> descendants = new ArrayList<SWTBotGefEditPart>();
				Stack<SWTBotGefEditPart> parts = new Stack<SWTBotGefEditPart>();
				parts.push(SWTBotGefEditPart.this);
				while (!parts.isEmpty()) {
					SWTBotGefEditPart part = parts.pop();
					for (org.eclipse.gef.EditPart child : ((List<org.eclipse.gef.EditPart>) part.part.getChildren())) {
						SWTBotGefEditPart childPart = viewer.createEditPart(child);
						if (matcher.matches(child)) {
							descendants.add(childPart);
						}
						parts.push(childPart);
					}
				}
				return descendants;
			}
		});
	}

	/**
	 * get the underlying wrapped {@link EditPart} instance
	 * 
	 * @return the wrapped {@link EditPart}.
	 */
	public EditPart part() {
		return part;
	}

	/**
	 * focus on this edit part
	 */
	public void focus() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				viewer.graphicalViewer.setFocus(part);
			}
		});
	}

	/**
	 * select this edit part as a single selection
	 */
	public SWTBotGefEditPart select() {
		viewer.select(this);
		return this;
	}

	/**
	 * click on the edit part.
	 */
	public SWTBotGefEditPart click() {
		final Rectangle bounds = getBounds();
		return click(bounds.getTopLeft());
	}

	/**
	 * click on the edit part at the specified location
	 */
	public SWTBotGefEditPart click(final Point location) {
		viewer.getCanvas().mouseEnterLeftClickAndExit(location.x, location.y);
		return this;
	}

	/**
	 * double click on the edit part.
	 */
	public SWTBotGefEditPart doubleClick() {
		final Rectangle bounds = getBounds();
		viewer.getCanvas().mouseMoveDoubleClick(bounds.x, bounds.y);
		return this;
	}

	
	/* this method is not finished. She will become public when finished, but API is not guaranteed */
	private void scrollUp() {
		final IFigure figure = ((GraphicalEditPart) part).getFigure();
		for (final Object child : figure.getChildren()) {
			if (child instanceof ScrollPane) {
				Collection<ScrollBar> scrollbars = getScrollBars((ScrollPane) child);
				Point pointToClick = scrollbars.iterator().next().getBounds().getCenter();
				viewer.getCanvas().mouseMoveLeftClick(pointToClick.x, pointToClick.y);
			}
		}
	}
	
	/* this method is not finished. She will become public when finished, but API is not guaranteed */
	private Collection<ScrollBar> getScrollBars(final ScrollPane scrollPane) {
		Set<ScrollBar> scrollbars = new HashSet<ScrollBar>();
		for (final Object child :scrollPane.getChildren()) {
			if (child instanceof ScrollBar) {
				scrollbars.add((ScrollBar) child);
			}
		}
		return scrollbars;
	}
	
	/**
	 * Resize the current edit part from the corner orientation to the new size. The direction is specified using using
	 * {@link PositionConstants#NORTH}, {@link PositionConstants#NORTH_EAST}, etc.
	 * 
	 * @param direction the direction
	 * @param width the new width
	 * @param height the new height
	 */
	public void resize(int direction, int width, int height) {
		Rectangle bounds = getBounds();
		int fromX = 0 ;
		int fromY = 0;
		int toX = 0;
		int toY = 0;
		
		switch(direction) {
		case PositionConstants.NORTH:
			fromX = bounds.x + bounds.width / 2; 
			fromY = bounds.y;
			toX=  bounds.x + bounds.width / 2;
			toY= bounds.y + bounds.height - height;
			break;
		case PositionConstants.SOUTH:
			fromX = bounds.x + bounds.width / 2; 
			fromY = bounds.y + bounds.height;
			toX=bounds.x + bounds.width / 2;
			toY=bounds.y +height;
			break;
		case PositionConstants.EAST:
			fromX = bounds.x; 
			fromY = bounds.y + bounds.height/2;
			toX = bounds.x + bounds.width - width;
			toY = bounds.y + bounds.height/2;
			break;
		case PositionConstants.WEST:
			fromX = bounds.x + bounds.width; 
			fromY = bounds.y + bounds.height/2;
			toX = bounds.x + width;
			toY = bounds.y + bounds.height/2;
			break;
		case PositionConstants.NORTH_EAST:
			fromX = bounds.x; 
			fromY = bounds.y;
			toX = bounds.x + bounds.width - width;
			toY= bounds.y + bounds.height - height;
			break;
		case PositionConstants.NORTH_WEST:
			fromX = bounds.x + bounds.width; 
			fromY = bounds.y;
			toX = bounds.x + width;
			toY= bounds.y + bounds.height - height;
			break;
		case PositionConstants.SOUTH_EAST:
			fromX = bounds.x; 
			fromY = bounds.y + bounds.height;
			toX =  bounds.x + bounds.width - width;
			toY = bounds.y +height;
			break;
		case PositionConstants.SOUTH_WEST:
			fromX = bounds.x + bounds.width; 
			fromY = bounds.y + bounds.height;
			toX = bounds.x + width;
			toY = bounds.y +height;
			break;
			default:
				new IllegalArgumentException("direction given is not a valid one");
		}
		viewer.drag(fromX, fromY, toX, toY);
	}

	private Rectangle getBounds() {
		final IFigure figure = ((GraphicalEditPart) part).getFigure();
		final Rectangle bounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(bounds);
		return bounds;
	}

	public SWTBotGefEditPart activateDirectEdit() {
		return activateDirectEdit(null);
	}

	public SWTBotGefEditPart activateDirectEdit(final Object feature) {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				DirectEditRequest request = new DirectEditRequest();
				if (feature != null)
					request.setDirectEditFeature(feature);
				part().performRequest(request);
			}
		});
		return this;
	}

	/**
	 * provide a description of this edit part that is useful for debugging purposes.
	 */
	public String toString() {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);

		describe(out, 0);

		out.close();
		return writer.toString();
	}

	private void describe(PrintWriter out, int indent) {
		out.print(indent(indent));
		out.print(part.getClass().getName());
		List<SWTBotGefEditPart> children = children();
		out.print(" children=" + children.size());
		out.println();
		for (SWTBotGefEditPart child : children) {
			child.describe(out, indent + 1);
		}
	}

	private String indent(int size) {
		if (size == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(size);
		for (int x = 0; x < size; ++x) {
			buf.append("\t");
		}
		return buf.toString();
	}

	@SuppressWarnings("unchecked")
	public List<SWTBotGefConnectionEditPart> sourceConnections() {
		return UIThreadRunnable.syncExec(new Result<List<SWTBotGefConnectionEditPart>>() {
			public List<SWTBotGefConnectionEditPart> run() {
				List<SWTBotGefConnectionEditPart> connections = new ArrayList<SWTBotGefConnectionEditPart>();
				List<org.eclipse.gef.ConnectionEditPart> sourceConnections = ((GraphicalEditPart) part).getSourceConnections();
				for (org.eclipse.gef.ConnectionEditPart c : sourceConnections) {
					connections.add(viewer.createEditPart(c));
				}
				return connections;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<SWTBotGefConnectionEditPart> targetConnections() {
		return UIThreadRunnable.syncExec(new Result<List<SWTBotGefConnectionEditPart>>() {
			public List<SWTBotGefConnectionEditPart> run() {
				List<SWTBotGefConnectionEditPart> connections = new ArrayList<SWTBotGefConnectionEditPart>();
				List<org.eclipse.gef.ConnectionEditPart> targetConnections = ((GraphicalEditPart) part).getTargetConnections();
				for (org.eclipse.gef.ConnectionEditPart c : targetConnections) {
					connections.add(viewer.createEditPart(c));
				}
				return connections;
			}
		});
	}
}
