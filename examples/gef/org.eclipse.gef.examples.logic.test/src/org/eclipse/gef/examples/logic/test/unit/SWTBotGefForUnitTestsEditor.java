/*******************************************************************************
 * Copyright (c) 2010 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/

package org.eclipse.gef.examples.logic.test.unit;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;

/**
 * Subclass default editor to add useful methods for unit tests.
 * @author mchauvin
 */
public class SWTBotGefForUnitTestsEditor extends SWTBotGefEditor {

	public SWTBotGefForUnitTestsEditor(IEditorReference reference, SWTWorkbenchBot bot)
			throws WidgetNotFoundException {
		super(reference, bot);
	}
	
	public EditDomain forTestGetEditDomain() {
		return editDomain; 
	}
	
	public EditPartViewer forTestGetViewer() {
		return graphicalViewer;
	}
	
}