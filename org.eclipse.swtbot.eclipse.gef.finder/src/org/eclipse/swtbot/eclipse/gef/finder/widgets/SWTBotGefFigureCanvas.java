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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
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

    /**
     * this method emits mouse events that handle a mouse move and double click to the specified position within the canvas.
     * @param xPosition the relative x position
     * @param yPosition the relative y position
     */
    public void mouseMoveDoubleClick(final int xPosition, final int yPosition) {
    UIThreadRunnable.asyncExec(new VoidResult() {
    		public void run() {
    	        org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(xPosition, yPosition, 0, 0, 0);
    	        eventDispatcher.dispatchMouseMoved(meMove);
    	        org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1);
    	        eventDispatcher.dispatchMousePressed(meDown);
    	        org.eclipse.swt.events.MouseEvent meUp = wrapMouseEvent(xPosition, yPosition, 1 , SWT.BUTTON1, 1);
    	        eventDispatcher.dispatchMouseReleased(meUp);
    	        org.eclipse.swt.events.MouseEvent meDown2 = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 2);
    	        eventDispatcher.dispatchMousePressed(meDown2);
    	        org.eclipse.swt.events.MouseEvent meDoubleClick = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 2);
    	        eventDispatcher.dispatchMouseDoubleClicked(meDoubleClick);
    	        org.eclipse.swt.events.MouseEvent meUp2 = wrapMouseEvent(xPosition, yPosition, 1 , SWT.BUTTON1, 2);
    	        eventDispatcher.dispatchMouseReleased(meUp2);
    		}
    	});
    }
    
    private org.eclipse.swt.events.MouseEvent wrapMouseEvent(int x, int y, int button, int stateMask, int count) {
        return new org.eclipse.swt.events.MouseEvent(createMouseEvent(x, y, button, stateMask, count));
    }

    
    /**
     * this method emits mouse events that handle drags within the canvas
     * 
     * @param fromXPosition the relative x position within the canvas to drag from
     * @param fromYPosition the relative y position within the canvas to drag from
     * @param toXPosition the relative x position within the canvas to drag to
     * @param toYPosition the relative y position within the canvas to drag to
     */
    public void mouseDrag(final int fromXPosition, final int fromYPosition, final int toXPosition, final int toYPosition) {
        UIThreadRunnable.asyncExec(new VoidResult() {
            public void run() {
            	org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(fromXPosition, fromYPosition, 0, 0, 0);
            	eventDispatcher.dispatchMouseMoved(meMove);
            	org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(fromXPosition, fromYPosition, 1, SWT.BUTTON1, 1);
            	eventDispatcher.dispatchMousePressed(meDown);
            	org.eclipse.swt.events.MouseEvent meMoveTarget = wrapMouseEvent(toXPosition, toYPosition, 1, SWT.BUTTON1, 0);
            	eventDispatcher.dispatchMouseMoved(meMoveTarget);
            	org.eclipse.swt.events.MouseEvent meUp = wrapMouseEvent(toXPosition, toYPosition, 1 , SWT.BUTTON1, 1);
            	eventDispatcher.dispatchMouseReleased(meUp);
            }
        });
    }
    
    public void mouseMoveLeftClick(final int xPosition, final int yPosition) {
        UIThreadRunnable.asyncExec(new VoidResult() {
            public void run() {
            	org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(xPosition, yPosition, 0, 0, 0);
            	eventDispatcher.dispatchMouseMoved(meMove);
            	org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1);
            	eventDispatcher.dispatchMousePressed(meDown);
            	org.eclipse.swt.events.MouseEvent meUp = wrapMouseEvent(xPosition, yPosition, 1 , SWT.BUTTON1, 1);
            	eventDispatcher.dispatchMouseReleased(meUp);
            }
        });
    }
    
    public void mouseEnterLeftClickAndExit(final int xPosition, final int yPosition) {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				eventDispatcher.dispatchMouseEntered(wrapMouseEvent(xPosition, yPosition, 0, 0, 0));
				eventDispatcher.dispatchMouseMoved(wrapMouseEvent(xPosition, yPosition, 0, 0, 0));
				eventDispatcher.dispatchMousePressed(wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1));
				eventDispatcher.dispatchMouseReleased(wrapMouseEvent(xPosition, yPosition, 1, SWT.BUTTON1, 1));
				eventDispatcher.dispatchMouseExited(wrapMouseEvent(xPosition, yPosition, 0, 0, 0));
			}
		});
    }
    
    public void typeText(final Text textControl, final String text) {
    	oldTypeText(textControl, text);
    	//TODO need to improve type text before that
    	//final SWTBotText textBot = new SWTBotText(textControl);
        //textBot.typeText(text + "\r");          
    }
    
    private void oldTypeText(final Text textControl, final String text) {
        
        UIThreadRunnable.syncExec(new VoidResult() {
            public void run() {
                textControl.setText("");
            }
        });
        for (int x = 0; x < text.length(); ++x) {
            final char c = text.charAt(x);
            UIThreadRunnable.syncExec(new VoidResult() {
                public void run() {
                    textControl.setFocus();
                    textControl.notifyListeners(SWT.KeyDown, keyEvent(SWT.NONE, c, 0));
                    textControl.notifyListeners(SWT.KeyUp, keyEvent(SWT.NONE, c, 0));
                    textControl.setText(textControl.getText() + c);
                }
            });
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
            }
        }

        // apply the value with a default selection event
        UIThreadRunnable.syncExec(new VoidResult() {
            public void run() {
                textControl.setFocus();
                textControl.notifyListeners(SWT.DefaultSelection, createEvent());
            }
        });
    }
    
    private Event keyEvent(int modificationKey, char c, int keyCode) {
        Event keyEvent = createEvent();
        keyEvent.stateMask = modificationKey;
        keyEvent.character = c;
        keyEvent.keyCode = keyCode;
        return keyEvent;
    }

    
    
}
