/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.test;

import org.eclipse.swt.examples.browserexample.BrowserExample;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class BaseBrowserExampleTest extends BaseSWTShellTest {

	protected ControlExample	controlExample;

	@Override
	protected final void createUI(Composite parent) {
		shell.setText("Browser Shell");
		new BrowserExample(parent, false);
	}

}
