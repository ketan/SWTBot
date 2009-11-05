
package org.eclipse.gef.examples.logicdesigner;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

public class LogicMWizardPage1 extends LogicWizardPage1 {

	public LogicMWizardPage1(IWorkbench aWorkbench,
			IStructuredSelection selection) {
		super(aWorkbench, selection);
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		this.setFileName("emptyModel" + ".logicm"); //$NON-NLS-2$//$NON-NLS-1$
	}

}
