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
package org.eclipse.swtbot.eclipse.finder.waits;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;

/**
 * Waits until an editor that matches the specified matcher appears.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WaitForEditor extends DefaultCondition {
	private final Matcher<?>		matcher;
	private List<IEditorReference>	workbenchParts;

	/**
	 * Creates a condition that waits until the matcher is true.
	 *
	 * @param matcher the matcher
	 */
	public WaitForEditor(Matcher<?> matcher) {
		this.matcher = matcher;
	}

	public String getFailureMessage() {
		return "Could not find editor matching: " + matcher;
	}

	public boolean test() throws Exception {
		workbenchParts = new ArrayList<IEditorReference>();
		findMatchingParts();
		return !workbenchParts.isEmpty();
	}

	private void findMatchingParts() {
		for (IEditorReference workbenchPartReference : findEditors()) {
			if (matcher.matches(workbenchPartReference))
				workbenchParts.add(workbenchPartReference);
		}
	}

	public IEditorReference get(int index) {
		return workbenchParts.get(index);
	}

	private List<IEditorReference> findEditors() {
		return UIThreadRunnable.syncExec(SWTUtils.display(), new ListResult<IEditorReference>() {
			public List<IEditorReference> run() {
				ArrayList<IEditorReference> result = new ArrayList<IEditorReference>();
				IWorkbenchPage[] pages = getWorkbenchPages();
				for (int i = 0; i < pages.length; i++) {
					IWorkbenchPage page = pages[i];
					IEditorReference[] editorReferences = page.getEditorReferences();
					for (int j = 0; j < editorReferences.length; j++) {
						IEditorReference editorReference = editorReferences[j];
						result.add(editorReference);
					}
				}
				return result;
			}
		});
	}

	private IWorkbenchPage[] getWorkbenchPages() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage[] pages = activeWorkbenchWindow.getPages();
		return pages;
	}

	public List<IEditorReference> all() {
		return workbenchParts;
	}
}
