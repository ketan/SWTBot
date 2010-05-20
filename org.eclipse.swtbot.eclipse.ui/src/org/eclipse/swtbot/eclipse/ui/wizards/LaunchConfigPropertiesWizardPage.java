package org.eclipse.swtbot.eclipse.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LaunchConfigPropertiesWizardPage extends WizardPage {

	protected LaunchConfigPropertiesWizardPage() {
		super("Default launcher settings");
		setTitle("Settings when you launch your application");
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(2, false));

		Label label;
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("&Test Runner:");

		Combo junitRunner = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		junitRunner.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		junitRunner.setItems(new String[] { "JUnit 4(recommend)", "JUnit 3" });
		junitRunner.select(0);

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("&Program Arguments:");

		Text programArgs = new Text(composite, SWT.MULTI | SWT.LEAD | SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.horizontalSpan = 2;
		programArgs.setLayoutData(layoutData);

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("&VM Arguments:");
		setControl(composite);

		Text vmArgs = new Text(composite, SWT.MULTI | SWT.LEAD | SWT.BORDER);
		layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.horizontalSpan = 2;
		vmArgs.setLayoutData(layoutData);
	}

}
