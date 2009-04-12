/*******************************************************************************
 * Copyright (c) 2008-2009 Ketan Padegaonkar and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.waits;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.finders.WorkbenchContentsFinder;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;
import org.eclipse.ui.IEditorReference;
import org.hamcrest.Matcher;

/**
 * Waits until an editor that matches the specified matcher appears.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @version $Id$
 */
public class WaitForEditor extends WaitForObjectCondition<IEditorReference> {

	/**
	 * Creates a condition that waits until the matcher is true.
	 * 
	 * @param matcher the matcher
	 */
	WaitForEditor(Matcher<?> matcher) {
		super(matcher);
	}

	public String getFailureMessage() {
		return "Could not find editor matching: " + matcher;
	}

	@Override
	protected List<IEditorReference> findMatches() {
		return UIThreadRunnable.syncExec(SWTUtils.display(), new ListResult<IEditorReference>() {
			public List<IEditorReference> run() {
				return new WorkbenchContentsFinder().findEditors(matcher);
			}
		});
	}

}
