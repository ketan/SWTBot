package org.eclipse.gef.examples.logicdesigner;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class LogicMultiPageEditor extends MultiPageEditorPart {

	private LogicEditor editor1;
	
	private LogicEditor editor2;
	
	@Override
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

	@Override
	public void doSave(IProgressMonitor monitor) {
		editor1.doSave(monitor);
		editor2.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		editor1.doSaveAs();
		editor2.doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return editor1.isSaveAsAllowed() && editor2.isSaveAsAllowed();
	}

}
