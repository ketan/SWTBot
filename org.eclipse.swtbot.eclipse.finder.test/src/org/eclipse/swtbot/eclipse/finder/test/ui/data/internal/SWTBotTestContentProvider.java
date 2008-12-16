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
package org.eclipse.swtbot.eclipse.finder.test.ui.data.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * This is the content provider for getting the data for the SWTBotTest Tree Viewer.
 * 
 * @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTestContentProvider implements IStructuredContentProvider {

	/**
	 * The list of managed data.
	 */
	List		data	= new ArrayList();
	/**
	 * The table view instance assigned to this provider.
	 */
	TableViewer	viewer	= null;

	/**
	 * Constructs the content provider.
	 */
	public SWTBotTestContentProvider() {
		// Do nothing.
	}

	/**
	 * Adds an item to the content provider.
	 * 
	 * @param item The item to add.
	 */
	public void add(SWTBotTestData item) {
		if (!data.contains(item)) {
			data.add(item);
			viewer.add(item);
		}
	}

	/**
	 * Adds an item to the content provider.
	 * 
	 * @param item The item to add.
	 */
	public void remove(SWTBotTestData item) {
		data.remove(item);
		viewer.remove(item);
	}

	public Object[] getElements(Object inputElement) {
		return data.toArray();
	}

	public void dispose() {
		data.clear();
		data = null;
		viewer.getTable().dispose();
		viewer = null;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// This does not do any checking of the type before casting...This will
		// just pass the error up the chain.
		this.viewer = (TableViewer) viewer;
		data = (List) newInput;
	}
}
