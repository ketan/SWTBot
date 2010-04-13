package org.eclipse.swtbot.eclipse.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Version;

public class NewPluginProjectWizardPage extends WizardPage {
	private final PlatformInfoProvider	provider;

	private Button						productIdButton;
	private Button						applicationIdButton;
	private Combo						productId;
	private Combo						applicationId;

	private Text						pluginId;
	private Text						pluginName;
	private Text						pluginVersion;
	private Text						pluginProvider;

	protected NewPluginProjectWizardPage(PlatformInfoProvider provider) {
		super("New SWTBot Test Plugin");
		this.provider = provider;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(2, false));

		Label label;
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("Plugin &Name:");

		// name, id, version, providers

		pluginName = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		pluginName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pluginName.setMessage("SWTBot Test Plugin");

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("&Plugin id:");

		pluginId = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		pluginId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pluginId.setMessage("com.example.rcp.uitest");

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("&Version:");

		pluginVersion = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		pluginVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pluginVersion.setText("1.0.0.qualifier");
		pluginVersion.setMessage("1.0.0.qualifier");

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		label.setText("&Provider:");

		pluginProvider = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		pluginProvider.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		pluginProvider.setMessage("Your Company");

		productAndApplication(composite);
		hookListeners();
		setControl(composite);
	}

	private void hookListeners() {
		applicationIdButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				productId.setEnabled(false);
				applicationId.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		productIdButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				applicationId.setEnabled(false);
				productId.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
				setErrorMessage(null);

				String pluginName = NewPluginProjectWizardPage.this.pluginName.getText();
				if (pluginName.trim().length() == 0) {
					setErrorMessage("Plugin name cannot be empty!");
					setPageComplete(false);
					return;
				}

				if (provider.getProjects().contains(pluginName)) {
					setErrorMessage("A project by that name already exists!");
					setPageComplete(false);
					return;
				}

				if (pluginId.getText().trim().length() == 0) {
					setErrorMessage("You did not set the plugin id!");
					setPageComplete(false);
					return;
				}

				if (!isValidCompositeID3_0(pluginId.getText())) {
					setErrorMessage("Invalid plugin id! Legal characters are A-Z a-z 0-9 . _ -");
					setPageComplete(false);
					return;
				}

				try {
					new Version(pluginVersion.getText());
				} catch (IllegalArgumentException ex) {
					setErrorMessage("The specified version does not have the correct format (major.minor.micro.qualifier) or contains invalid characters!");
					setPageComplete(false);
					return;
				}
			}
		};

		pluginId.addModifyListener(listener);
		pluginName.addModifyListener(listener);
		pluginVersion.addModifyListener(listener);
		pluginProvider.addModifyListener(listener);
	}

	// copied from IdUtil from PDE.
	private boolean isValidCompositeID3_0(String name) {
		if (name.length() <= 0) {
			return false;
		}
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if ((c < 'A' || 'Z' < c) && (c < 'a' || 'z' < c) && (c < '0' || '9' < c) && c != '_' && c != '-') {
				if (i == 0 || i == name.length() - 1 || c != '.') {
					return false;
				}
			}
		}
		return true;
	}

	private void productAndApplication(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		layoutData.horizontalSpan = 2;
		group.setLayoutData(layoutData);
		group.setLayout(new GridLayout(2, false));
		group.setText("Program to run");

		productIdButton = new Button(group, SWT.RADIO);
		productIdButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		productIdButton.setText("&Product id:");
		productIdButton.setSelection(true);

		productId = new Combo(group, SWT.DROP_DOWN);
		productId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		productId.setItems(provider.getProducts());
		productId.setText(provider.getDefaultProduct());

		applicationIdButton = new Button(group, SWT.RADIO);
		applicationIdButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		applicationIdButton.setText("&Application id:");

		applicationId = new Combo(group, SWT.READ_ONLY | SWT.SINGLE);
		applicationId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		applicationId.setItems(provider.getApplications());
		applicationId.setText(provider.getDefaultApplication());
		applicationId.setEnabled(false);
	}

	public String pluginName() {
		return pluginName.getText();
	}

	public String pluginId() {
		return pluginId.getText();
	}

	public String pluginVersion() {
		return pluginVersion.getText();
	}

	public String pluginProvider() {
		return pluginProvider.getText();
	}

}
