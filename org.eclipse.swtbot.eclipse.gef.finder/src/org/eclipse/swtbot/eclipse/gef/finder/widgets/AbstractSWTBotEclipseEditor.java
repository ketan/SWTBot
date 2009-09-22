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

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultParentResolver;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.core.IsAnything;

/**
 * Represent an Eclipse editor with no assumptions about the nature of the
 * controls that it contains. This differs from {@link SWTBotEclipseEditor},
 * which assumes that the editor contains a styled text widget.
 * 
 * @author David Green
 */
public abstract class AbstractSWTBotEclipseEditor extends SWTBotWorkbenchPart<IEditorReference> {

    protected IEditorReference reference;

    protected Widget widget;
    
    protected Finder finder;

    //TODO comment
    /**
     * 
     * @param name
     *            the filename as it appears in the editor tab
     * @throws WidgetNotFoundException
     */
    public AbstractSWTBotEclipseEditor(final IEditorReference reference, final SWTWorkbenchBot bot) throws WidgetNotFoundException {
        super(reference, bot);
        this.reference = partReference;
        this.finder = new Finder(new ControlFinder(new DefaultChildrenResolver(), new DefaultParentResolver()), new MenuFinder());
        this.widget = findWidget(reference, finder);
    }

    /**
     * Finds the widget using the given view and finder.
     * 
     * @param editorReference
     *            the part reference.
     * @param finder
     *            the finder to be used to find controls in the view.
     * @return the widget in the part reference.
     * @throws WidgetNotFoundException
     *             if the widget is not found.
     */
    protected static Widget findWidget(IWorkbenchPartReference editorReference, Finder finder) throws WidgetNotFoundException {
        if (editorReference instanceof WorkbenchPartReference) {
            Control control = ((WorkbenchPartReference) editorReference).getPane().getControl();
            boolean shouldFindInvisibleControls = finder.shouldFindInvisibleControls();
            finder.setShouldFindInvisibleControls(true);
            try {
                return finder.findControls(control, new IsAnything<Widget>(), true).get(1);
            } catch (Exception e) {
                throw new WidgetNotFoundException("Could not find any control inside the editor " + editorReference.getPartName(), e);
            } finally {
                finder.setShouldFindInvisibleControls(shouldFindInvisibleControls);
            }
        }
        throw new WidgetNotFoundException("Could not find any control inside the editor " + editorReference.getPartName());
    }
    
    /*
     * {@inheritDoc}
     * 
     * @see
     * org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart#close()
     */
    @Override
    public void close() {
        UIThreadRunnable.syncExec(new VoidResult() {
            public void run() {
                reference.getPage().closeEditor(reference.getEditor(false), false);
            }
        });
    }

    /*
     * {@inheritDoc}
     * 
     * @see org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart#show()
     */
    @Override
    public void show() {
        UIThreadRunnable.syncExec(new VoidResult() {
            public void run() {
                IEditorPart editor = reference.getEditor(true);
                reference.getPage().activate(editor);
            }
        });
    }
    
    /*
     * 
     */
    public AbstractSWTBotEclipseEditor save() {
        UIThreadRunnable.syncExec(new VoidResult() {
            public void run() {
                reference.getEditor(false).doSave(null);
            }
        });
        return this;
    }
}
