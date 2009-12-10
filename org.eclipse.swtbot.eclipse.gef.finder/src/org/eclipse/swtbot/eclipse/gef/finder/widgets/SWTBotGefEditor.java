/*******************************************************************************
 * Copyright (c) 2004, 2009 MAKE Technologies Inc and others
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
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
 * represent a graphical editor that uses the GEF framework. The underlying
 * editor must adapt to a {@link GraphicalViewer} which must extend
 * {@link GraphicalViewerImpl} (this is the default for all GEF-based editors
 * that extend {@link org.eclipse.gef.ui.parts.GraphicalEditor}).
 * 
 * Unlike most of SWTBot, this editor uses {@link org.eclipse.gef.EditPart edit
 * parts} to target UI events instead of SWT widgets. This is due to the fact
 * that GEF editors paint on a canvas and rarely use widgets at all. 
 * 
 * @author David Green
 */
public class SWTBotGefEditor extends SWTBotEditor {

    protected GraphicalViewer graphicalViewer;

    protected EditDomain editDomain;

    protected SWTBotGefFigureCanvas canvas;
    
    private Map<EditPart, SWTBotGefEditPart> editPartMapping = new WeakHashMap<EditPart, SWTBotGefEditPart>();

    //TODO comment.
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
    * lazily creates a {@link SWTBotGefEditPart} if this edit part does not exist yet. If an instance encapsulating the specified edit part has been created before, that instance is returned. 
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
     *  lazily creates a {@link SWTBotGefConnectionEditPart} if this edit part does not exist yet. If an instance encapsulating the specified edit part has been created before, that instance is returned. 
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
     * Activate the tool with the specified label. If there is many tools with the same label the first one will be used. See {@link SWTBotGefEditor#activateTool(String, int)}
     * @param label the label of the tool to activate
     * @return the editor bot
     * @throws WidgetNotFoundException if the tool with label specified could not be found
     */
    public SWTBotGefEditor activateTool(final String label) throws WidgetNotFoundException {
        activateTool(Pattern.compile(Pattern.quote(label)), 0);
        return this;
    }

    /**
     * Activate the tool with the specified label and the specified index. This method should be used only if there is many tools with the same label. See {@link SWTBotGefEditor#activateTool(String)}
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
                        editDomain.setActiveTool(((ToolEntry) paletteEntry).createTool());
                    } else {
                        exception[0] = new WidgetNotFoundException(String.format("%s is not a tool entry, it's a %s", labelMatcher.toString(), paletteEntry.getClass().getName()));
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

    //TODO should be in a separate class
    /**
     * type the given text into the graphical editor, presuming that it is
     * already in 'direct edit' mode.
     * 
     * @param text
     *            the text to type.
     * @throws WidgetNotFoundException
     */
    public void directEditType(String text) throws WidgetNotFoundException {
        List<Text> controls = bot.getFinder().findControls(getWidget(), new IsInstanceOf<Text>(Text.class), true);
        if (controls.size() == 1) {
            final Text textControl = controls.get(0);
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
        } else {
            throw new WidgetNotFoundException(String.format("Expected to find one text control, but found %s.  Is the editor in direct-edit mode?", controls.size()));
        }
    }

    /**
     * @param c
     *            the character.
     * @param modificationKey
     *            the modification key.
     * @param keyCode
     *            the keycode.
     * @return a key event with the specified keys.
     * @see Event#keyCode
     * @see Event#character
     * @see Event#stateMask
     * @since 1.2
     */
    @Deprecated
    protected Event keyEvent(int modificationKey, char c, int keyCode) {
        Event keyEvent = createEvent();
        keyEvent.stateMask = modificationKey;
        keyEvent.character = c;
        keyEvent.keyCode = keyCode;

        return keyEvent;
    }

    @Deprecated
    protected Event createEvent() {
        Event event = new Event();
        event.time = (int) System.currentTimeMillis();
        event.widget = getWidget();
        event.display = bot.getDisplay();
        return event;
    }

    /**
     * 
     * @param matcher
     *            the matcher that matches on {@link org.eclipse.gef.EditPart}
     * @return a collection of {@link SWTBotGefEditPart}
     * @throws WidgetNotFoundException
     */
	public List<SWTBotGefEditPart> editParts(Matcher<? extends EditPart> matcher) throws WidgetNotFoundException {
        return rootEditPart().descendants(matcher);
    }

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

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub
        
    }
    
    public SWTBotGefEditor clickContextMenu(String text) throws WidgetNotFoundException {
        new SWTBotGefContextMenu(getControl(), text).click();
        return this;
    }

    
    /**
     * this method emits mouse events that handle a mouse move and double click to the specified position within the editor.<br>
     * Note that a move is required before double clicking in order to update the mouse cursor with the target editpart.  
     *  
     * @param xPosition the relative x position within the graphical viewer
     * @param yPosition the relative y position within the graphical viewer
     */
    public void mouseMoveDoubleClick(final int xPosition, final int yPosition) {
    	UIThreadRunnable.asyncExec(new VoidResult() {
    		public void run() {
    			canvas.mouseMoveDoubleClick(xPosition, yPosition);
    		}
    	});
    }
    
    /**
     * this method emits mouse events that handle a mouse move and left click to the specified position within the editor.<br>
     * Note that a move is required before left clicking in order to update the mouse cursor with the target editpart.  
     *  
     * @param xPosition the relative x position within the graphical viewer
     * @param yPosition the relative y position within the graphical viewer
     */
    public void mouseMoveLeftClick(final int xPosition, final int yPosition) {
        UIThreadRunnable.asyncExec(new VoidResult() {
            public void run() {
        		canvas.mouseMoveLeftClick(xPosition, yPosition);
            }
        });
    }

    //TODO should be commented
    /**
     * this method emits mouse events that handle drags within the editor
     * 
     * @param fromXPosition the relative x position within the graphical viewer to drag from
     * @param fromYPosition the relative y position within the graphical viewer to drag from
     * @param toXPosition the relative x position within the graphical viewer to drag to
     * @param toYPosition the relative y position within the graphical viewer to drag to
     */
    public void mouseDrag(final int fromXPosition, final int fromYPosition, final int toXPosition, final int toYPosition) {
        UIThreadRunnable.asyncExec(new VoidResult() {
            public void run() {
                canvas.mouseDrag(fromXPosition, fromYPosition, toXPosition, toYPosition);
            }
        });
    }
	

	//TODO should be commented
	 /** 
	    * this method emits mouse events that handle a mouse move and left click to the specified position within the editor.<br>
	    * Note that a move is required before left clicking in order to update the mouse cursor with the target editpart.  
	    *  
	    * @param label the label 
	    */
	   public void mouseMoveLeftClick(String label) {
	       SWTBotGefEditPart selectedEP = getEditPart(label);
	       if (selectedEP==null) {
	    	   throw new WidgetNotFoundException(String.format("Expected to find widget %s",label)); 
	       }
	       Rectangle bounds = ((GraphicalEditPart) selectedEP.part()).getFigure().getBounds();
           mouseMoveLeftClick(bounds.x, bounds.y); 
	   }
	   
	   /**
	    * this method emits mouse events that handle a mouse move and double click to the specified position within the editor.<br>
	    * Note that a move is required before double clicking in order to update the mouse cursor with the target editpart.  
	    * As we can not double click on the corner, we move the double click position
	    * @param label the label 
	    */
	   public void mouseMoveDoubleClick(final String label) {
	       SWTBotGefEditPart selectedEP = getEditPart(label);
	       if (selectedEP==null) {
	    	   throw new WidgetNotFoundException(String.format("Expected to find widget %s",label)); 
	       }
	       Rectangle bounds = ((GraphicalEditPart) selectedEP.part()).getFigure().getBounds();
           mouseMoveDoubleClick(bounds.x, bounds.y+MOVE);
	   }
	   
	   private static final int MOVE = 3;
	   
	   /**
	    * this method emits mouse events that handle drags within the editor
	    * 
	    * @param label the label 
	    * @param toXPosition the relative x position within the graphical viewer to drag to
	    * @param toYPosition the relative y position within the graphical viewer to drag to
	    */
	   public void mouseDrag(final String label, final int toXPosition, final int toYPosition) {
	       SWTBotGefEditPart selectedEP = getEditPart(label);
	       if (selectedEP==null) {
	    	   throw new WidgetNotFoundException(String.format("Expected to find widget %s",label));  
	       }
           Rectangle bounds = ((GraphicalEditPart) selectedEP.part()).getFigure().getBounds();
           mouseDrag(bounds.x, bounds.y, toXPosition, toYPosition);
	   }
	    
	    /**
	     * select the edit part with the label as a single selection
	     */
	   public SWTBotGefEditor select(String label)
	   {
	       SWTBotGefEditPart selectedEP = getEditPart(label);
	       if (selectedEP == null) {
	    	   throw new WidgetNotFoundException(String.format("Expected to find widget %s",label));  
	       }
	       List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
	       editParts.add(selectedEP);
	       return select(selectedEP);
	   }

	   /**
	    * get this edit part with the label as a single selection
	    */
	   public SWTBotGefEditPart getEditPart(String label)
	   {  
	       List<SWTBotGefEditPart> allEditParts = mainEditPart().children();
	       allEditParts.addAll(mainEditPart().sourceConnections());
	       return getEditpart(label, allEditParts);
	   }

	   //FIXME should moved in a finder
	   @Deprecated
	   /**
        * get this edit part with the label as a single selection
        */
	   public SWTBotGefEditPart getEditpart(String label, List<SWTBotGefEditPart> allEditParts)
	   {
	       for (SWTBotGefEditPart child : allEditParts) {
	           IFigure figure = ((GraphicalEditPart) child.part()).getFigure();
	           
	           if (isLabel(figure, label)) {
	                return child;
	            }
	           
	           // find label in children
	           if (findLabelFigure(figure, label))  {
	               SWTBotGefEditPart childEditPart = getEditPart(child, label);
	               if (childEditPart!=null) {
	                   return childEditPart;
	               }
	               return child;
	           }
	           
	           // find label in connections
	           SWTBotGefEditPart childEditPart = getEditPart(child, label);
	           if (childEditPart != null) {
	               return childEditPart;
	           }
	       }
	       return null;
	   }
        
        /**
         * get this edit part with the label as a single selection
         */
        private SWTBotGefEditPart getEditPart(SWTBotGefEditPart editPart, String label)
        {
            if (editPart.children().isEmpty() && findLabelFigure(((GraphicalEditPart) editPart.part()).getFigure(), label)) {
                return editPart;
            }
    
            List<SWTBotGefEditPart> allEditParts = editPart.children();
            allEditParts.addAll(editPart.sourceConnections());
            return getEditpart(label, allEditParts);
        }

        //FIXME should moved in a finder
        /**
         * @return if the figure is a label
         */
        private boolean isLabel(IFigure figure, String label)
        {
            // case 1 : gef label
            if ((figure instanceof Label && ((Label)figure).getText().equals(label)))
            {
                return true;
            }

            // case 2 : no gef label
            if ((figure instanceof TextFlow && ((TextFlow)figure).getText().equals(label)))
            {
                return true;
            }
            return false;
        }
        
        //FIXME should moved in a finder
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
}
