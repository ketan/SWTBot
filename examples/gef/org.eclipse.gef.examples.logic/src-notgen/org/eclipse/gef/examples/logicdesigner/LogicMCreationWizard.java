
package org.eclipse.gef.examples.logicdesigner;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class LogicMCreationWizard extends Wizard implements INewWizard {
	private LogicMWizardPage1 logicPage = null;
	private IStructuredSelection selection;
	private IWorkbench workbench;

public void addPages(){
	logicPage = new LogicMWizardPage1(workbench,selection);
	addPage(logicPage);
}

public void init(IWorkbench aWorkbench,IStructuredSelection currentSelection) {
	workbench = aWorkbench;
	selection = currentSelection;
}

public boolean performFinish(){
	return logicPage.finish();
}

}
