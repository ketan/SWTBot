/*******************************************************************************
 * Copyright (c) 2009 Obeo and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/

package org.eclipse.swtbot.eclipse.gef.finder.widgets;

import org.eclipse.draw2d.EventDispatcher;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBotControl;

/**
 * TODO comment
 * @author mchauvin
 */
public class SWTBotGefFigureCanvas extends AbstractSWTBotControl<FigureCanvas>{

    protected EventDispatcher eventDispatcher;

    //TODO should be documented
    public SWTBotGefFigureCanvas(FigureCanvas w) throws WidgetNotFoundException {
        super(w);
        eventDispatcher = w.getLightweightSystem().getRootFigure().internalGetEventDispatcher();
    }

    public void mouseMoveDoubleClick(int xPosition, int yPosition) {
        org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(xPosition, yPosition, 0, 0, 0);
        eventDispatcher.dispatchMouseMoved(meMove);
        org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1);
        eventDispatcher.dispatchMousePressed(meDown);
        org.eclipse.swt.events.MouseEvent meDoubleClick = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1);
        eventDispatcher.dispatchMouseDoubleClicked(meDoubleClick);
    }
    
    private org.eclipse.swt.events.MouseEvent wrapMouseEvent(int x, int y, int button, int stateMask, int count) {
        return new org.eclipse.swt.events.MouseEvent(createMouseEvent(x, y, button, stateMask, count));
    }

    public void mouseDrag(int fromXPosition, int fromYPosition, int toXPosition, int toYPosition) {
        org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(fromXPosition, fromYPosition, 0, 0, 0);
        eventDispatcher.dispatchMouseMoved(meMove);
        org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(fromXPosition, fromYPosition, 1, SWT.BUTTON1, 1);
        eventDispatcher.dispatchMousePressed(meDown);
        org.eclipse.swt.events.MouseEvent meMoveTarget = wrapMouseEvent(toXPosition, toYPosition, 1, SWT.BUTTON1, 0);
        eventDispatcher.dispatchMouseMoved(meMoveTarget);
        org.eclipse.swt.events.MouseEvent meUp = wrapMouseEvent(toXPosition, toYPosition, 1 , SWT.BUTTON1, 1);
        eventDispatcher.dispatchMouseReleased(meUp);
    }
    
    public void mouseMoveLeftClick(final int xPosition, final int yPosition) {
        org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(xPosition, yPosition, 0, 0, 0);
        eventDispatcher.dispatchMouseMoved(meMove);
        org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1);
        eventDispatcher.dispatchMousePressed(meDown);
        org.eclipse.swt.events.MouseEvent meUp = wrapMouseEvent(xPosition, yPosition, 1 , SWT.BUTTON1, 1);
        eventDispatcher.dispatchMouseReleased(meUp);
    }
    
    public void mouseEnterLeftClickAndExit(int xPosition, int yPosition) {
        eventDispatcher.dispatchMouseEntered(wrapMouseEvent(xPosition, yPosition, 0, SWT.BUTTON1, 0));
        eventDispatcher.dispatchMouseMoved(wrapMouseEvent(xPosition, yPosition, 0, SWT.BUTTON1, 0));
        eventDispatcher.dispatchMousePressed(wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1));
        eventDispatcher.dispatchMouseReleased(wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1));
        eventDispatcher.dispatchMouseExited(wrapMouseEvent(xPosition, yPosition, 0, SWT.BUTTON1, 0));
    }
    
}
