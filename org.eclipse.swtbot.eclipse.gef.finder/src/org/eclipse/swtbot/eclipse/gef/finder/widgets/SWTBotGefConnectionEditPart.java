/*******************************************************************************
 * Copyright (c) 2004, 2009 MAKE Technologies Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     MAKE Technologies Inc - initial API and implementation
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - refactoring
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.widgets;


import org.eclipse.gef.ConnectionEditPart;
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
     * @param graphicalEditor the graphical editor
     * @param part the {@link ConnectionEditPart} to wrap 
     */
	SWTBotGefConnectionEditPart(SWTBotGefEditor graphicalEditor, org.eclipse.gef.ConnectionEditPart part) {
		super(graphicalEditor, part);
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
				return graphicalEditor.createEditPart(source);
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
				return graphicalEditor.createEditPart(target);
			}
		});
	}
}
