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

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;

public class SWTGefForUnitTestsBot extends SWTGefBot {

	@Override
	protected SWTBotGefEditor createEditor(IEditorReference reference, SWTWorkbenchBot bot) {
		return new SWTBotGefForUnitTestsEditor(reference, bot);
	}
	
	public SWTBotGefForUnitTestsEditor gefEditor(String fileName) throws WidgetNotFoundException {
		return (SWTBotGefForUnitTestsEditor) gefEditor(fileName);
	}
	
}
