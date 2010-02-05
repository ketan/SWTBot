package org.eclipse.gef.examples.logic.test.unit;

import org.eclipse.gef.EditDomain;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;

public class SWTBotGefForUnitTestsEditor extends SWTBotGefEditor {

	public SWTBotGefForUnitTestsEditor(IEditorReference reference, SWTWorkbenchBot bot)
			throws WidgetNotFoundException {
		super(reference, bot);
	}
	
	public EditDomain forTestGetEditDomain() {
		return editDomain; 
	}
}
