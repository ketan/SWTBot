/*******************************************************************************
 * Copyright (c) 2004, 2010 MAKE Technologies Inc and others.
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


import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.waits.WaitForEditPartSelection;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;

/**
 * represent a connection edit part of a graphical viewer.
 * 
 * @author David Green
 * 
 * @see SWTBotGefEditPart
 * @see SWTBotGefEditor
 */
public class SWTBotGefConnectionEditPart extends SWTBotGefEditPart {

    /**
     * Construct a new {@link SWTBotGefConnectionEditPart} instance.
     * @param viewer the viewer
     * @param part the {@link ConnectionEditPart} to wrap 
     */
	SWTBotGefConnectionEditPart(SWTBotGefViewer viewer, org.eclipse.gef.ConnectionEditPart part) {
		super(viewer, part);
	}

	/**
	 * Create a new bendpoint for this connection to the given location,
	 * @param toXPosition x position of the bendpoint location
	 * @param toYPosition y position for the bendpoint location
	 */
	public void createBenpoint(final int toXPosition, final int toYPosition) {
		Point startMove = ((Connection) part().getFigure()).getPoints().getMidpoint().getCopy();
		viewer.click(startMove.x, startMove.y);		
		/* we need to wait element selection before proceed or drag will fail */
		viewer.bot().waitUntil(new WaitForEditPartSelection(this, viewer.graphicalViewer));
		
		viewer.drag(startMove.x, startMove.y, toXPosition, 250);
	}
	
	/*
	 * {@inheritDoc}
	 * @see SWTBotGefEditPart#part()
	 */
	@Override
	public org.eclipse.gef.ConnectionEditPart part() {
		return (org.eclipse.gef.ConnectionEditPart) super.part();
	}

	/*
	 * {@inheritDoc}
	 *@see ConnectionEditPart#getSource()
	 */
	public SWTBotGefEditPart source() {
		return UIThreadRunnable.syncExec(new Result<SWTBotGefEditPart>() {
			public SWTBotGefEditPart run() {
				org.eclipse.gef.EditPart source = part().getSource();
				return viewer.createEditPart(source);
			}
		});
	}
	
	/*
	 * {@inheritDoc}
	 *@see ConnectionEditPart#getTarget()
	 */
	public SWTBotGefEditPart target() {
		return UIThreadRunnable.syncExec(new Result<SWTBotGefEditPart>() {
			public SWTBotGefEditPart run() {
				org.eclipse.gef.EditPart target = part().getTarget();
				return viewer.createEditPart(target);
			}
		});
	}
}
