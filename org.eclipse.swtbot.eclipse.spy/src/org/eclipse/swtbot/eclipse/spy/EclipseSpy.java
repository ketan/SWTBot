/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.spy;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IParentResolver;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: EclipseSpy.java 1193 2008-12-02 07:22:51Z kpadegaonkar $
 */
public class EclipseSpy {

	Action					actionMonitor;

	long					lastWidget;

	StyledText				output;

	Runnable				trackWidgets;

	private final Composite	parent;

	/**
	 * @param parent
	 * @param childrenResolver
	 * @param parentResolver
	 */
	public EclipseSpy(Composite parent, IChildrenResolver childrenResolver, IParentResolver parentResolver) {
		this.parent = parent;
		initialize(parent.getDisplay());
		trackWidgets = new EclipseWidgetTracker(this, childrenResolver, parentResolver);
	}

	private void createActionMonitor() {
		actionMonitor = new Action("Monitor", IAction.AS_CHECK_BOX) {
			public void run() {
				if (actionMonitor.isChecked()) {
					Display display = output.getDisplay();
					display.timerExec(100, trackWidgets);
				}
			}
		};

		actionMonitor.setToolTipText("Enable/Disable monitoring of widgets");
		actionMonitor.setChecked(false);
	}

	private void createOutputText() {
		output = new StyledText(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		output.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, true));
		output.setFont(new Font(Display.getCurrent(), "Courier New", 10, SWT.NONE));
	}

	private void hookAccelerator() {
		parent.getDisplay().addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if ((e.stateMask == SWT.CTRL) && (e.keyCode == SWT.SHIFT))
					if (actionMonitor.isChecked())
						actionMonitor.setChecked(false);
					else {
						actionMonitor.setChecked(true);
						actionMonitor.run();
					}
			}
		});

		// parent.getDisplay().addFilter(SWT.KeyDown, new SWTBotExecutionListener());
	}

	private void initialize(Display display) {
		createOutputText();
		createActionMonitor();
		hookAccelerator();
	}
}
