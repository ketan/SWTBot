/*******************************************************************************
 * Copyright (c) 2009 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/

package org.eclipse.gef.examples.logicdesigner;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class LogicMultiPageEditor extends MultiPageEditorPart {

	private LogicEditor editor1;
	
	private LogicEditor editor2;
	
	protected void createPages() {
		editor1 = new LogicEditor();
		editor2 = new LogicEditor();
		try {
			addPage(editor1, getEditorInput());
			addPage(editor2, getEditorInput());
		} catch (final PartInitException e) {
			e.printStackTrace();
		}
	}

	
	public void doSave(IProgressMonitor monitor) {
		editor1.doSave(monitor);
		editor2.doSave(monitor);
	}

	public void doSaveAs() {
		editor1.doSaveAs();
		editor2.doSaveAs();
	}

	public boolean isSaveAsAllowed() {
		return editor1.isSaveAsAllowed() && editor2.isSaveAsAllowed();
	}

}
