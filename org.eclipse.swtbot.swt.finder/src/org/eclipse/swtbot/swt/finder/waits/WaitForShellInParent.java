/*******************************************************************************
 * Copyright (c) 2008 SWTBot contributors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Patel - initial API and implementation
 *     			(https://bugs.eclipse.org/bugs/show_bug.cgi?id=259837)
 *     Ketan Padegaonkar - modification to initial implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.waits;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.hamcrest.Matcher;

/**
 * A contdition that waits until a shell matching the matcher appears in the parent shell.
 *
 * @author Ketan Patel &lt;ktp420@live.com&gt;
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForShellInParent extends WaitForShell {
	private final Shell	parent;

	WaitForShellInParent(Shell parent, Matcher<?> matcher) {
		super(matcher);
		this.parent = parent;
	}

	protected Shell[] findShells() {
		return UIThreadRunnable.syncExec(new ArrayResult<Shell>() {
			public Shell[] run() {
				return parent.getShells();
			}
		});
	}

}
