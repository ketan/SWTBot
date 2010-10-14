package org.eclipse.swtbot.eclipse.finder.test.ui.views;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SWTBotFormView extends ViewPart {

	private Text text;

	@Override
	public void createPartControl(Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
		Label label = new Label(parent, SWT.NONE);
		label.setText(getPartName());
		text = new Text(parent, SWT.BORDER);
		text.setText(getPartName());
	}

	@Override
	public void setFocus() {
		text.setFocus();
	}

}
