/*******************************************************************************
 *  Copyright 2007 new SWTBot, http://swtbot.org/
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/

import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.utils.SWTBotEvents;

public class DumpEvents {

	public DumpEvents() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		new ControlExample(shell);

		Listener listener = new EventListener();
		for (int event : SWTBotEvents.events()) {
			display.addFilter(event, listener);
		}

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	class EventListener implements Listener {
		public void handleEvent(Event event) {
			if (event.type == SWT.Dispose || event.type == SWT.MouseMove || event.type == SWT.MouseHover || event.type == SWT.MouseEnter
					|| event.type == SWT.MouseExit || event.type == SWT.MouseExit || event.type == SWT.Paint || event.type == SWT.Move
					|| event.type == SWT.Resize)
				return;
			System.out.println(SWTBotEvents.toString(event));
		}
	}

	public static void main(String[] args) {
		try {
			new DumpEvents();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static {
		System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");
	}
}
