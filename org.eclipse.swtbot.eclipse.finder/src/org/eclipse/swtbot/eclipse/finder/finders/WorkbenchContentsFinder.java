/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.finders;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;

/**
 * WorkbenchContentsFinder allows to access the contents of a workbench window (views, editors, pages etc).
 * 
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class WorkbenchContentsFinder {

	/**
	 * @return the active workbench window.
	 */
	public IWorkbenchWindow activeWorkbenchWindow() {
		return syncExec(new Result<IWorkbenchWindow>() {
			public IWorkbenchWindow run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			}
		});
	}

	/**
	 * @param matcher the matcher used to match editors.
	 * @return the matching editors.
	 */
	public List<IEditorReference> findEditors(final Matcher<?> matcher) {
		return syncExec(SWTUtils.display(), new ListResult<IEditorReference>() {
			public List<IEditorReference> run() {
				return findEditorsInternal(matcher);
			}

		});
	}

	/**
	 * @param matcher the matcher used to match views
	 * @return the list of matching views
	 */
	public List<IViewReference> findViews(final Matcher<?> matcher) {
		return syncExec(SWTUtils.display(), new ListResult<IViewReference>() {
			public List<IViewReference> run() {
				return findViewsInternal(matcher);
			}
		});
	}

	/**
	 * @param matcher the matcher used to match perspectives
	 * @return the list of matching perspectives
	 */
	public List<IPerspectiveDescriptor> findPerspectives(final Matcher<?> matcher) {
		return syncExec(SWTUtils.display(), new ListResult<IPerspectiveDescriptor>() {
			public List<IPerspectiveDescriptor> run() {
				return findPerspectivesInternal(matcher);
			}
		});

	}

	private List<IViewReference> findViewsInternal(final Matcher<?> matcher) {
		List<IViewReference> result = new ArrayList<IViewReference>();
		IWorkbenchPage[] pages = workbenchPages();
		for (IWorkbenchPage page : pages) {
			IViewReference[] viewReferences = page.getViewReferences();
			for (IViewReference viewReference : viewReferences) {
				if (matcher.matches(viewReference))
					result.add(viewReference);
			}
		}
		return result;
	}

	private List<IPerspectiveDescriptor> findPerspectivesInternal(final Matcher<?> matcher) {
		IPerspectiveDescriptor[] perspectives = activeWorkbenchWindow().getWorkbench().getPerspectiveRegistry().getPerspectives();
		List<IPerspectiveDescriptor> matchingPerspectives = new ArrayList<IPerspectiveDescriptor>();
		for (IPerspectiveDescriptor perspectiveDescriptor : perspectives)
			if (matcher.matches(perspectiveDescriptor))
				matchingPerspectives.add(perspectiveDescriptor);
		return matchingPerspectives;
	}

	private List<IEditorReference> findEditorsInternal(final Matcher<?> matcher) {
		List<IEditorReference> result = new ArrayList<IEditorReference>();
		IWorkbenchPage[] pages = workbenchPages();
		for (IWorkbenchPage page : pages) {
			IEditorReference[] editorReferences = page.getEditorReferences();
			for (IEditorReference editorReference : editorReferences) {
				if (matcher.matches(editorReference))
					result.add(editorReference);
			}
		}
		return result;
	}

	/**
	 * @return the active view.
	 */
	public IViewReference findActiveView() {
		return syncExec(new Result<IViewReference>() {
			public IViewReference run() {
				return findActiveViewInternal();
			}
		});
	}

	private IViewReference findActiveViewInternal() {
		IWorkbenchPartReference partReference = activePageInternal().getActivePartReference();
		if (partReference instanceof IViewReference)
			return (IViewReference) partReference;
		return null;
	}

	/**
	 * @return the active perspective.
	 */
	public IPerspectiveDescriptor findActivePerspective() {
		return syncExec(new Result<IPerspectiveDescriptor>() {
			public IPerspectiveDescriptor run() {
				return findActivePerspectiveInternal();
			}
		});
	}

	/**
	 * @return the active editor.
	 */
	public IEditorReference findActiveEditor() {
		return syncExec(new Result<IEditorReference>() {
			public IEditorReference run() {
				return findActiveEditorInternal();
			}
		});
	}

	private IWorkbenchPage[] workbenchPages() {
		return activeWorkbenchWindow().getPages();
	}

	private IEditorReference findActiveEditorInternal() {
		IWorkbenchPage page = activePageInternal();
		IEditorPart activeEditor = page.getActiveEditor();
		return (IEditorReference) page.getReference(activeEditor);
	}

	private IPerspectiveDescriptor findActivePerspectiveInternal() {
		return activePageInternal().getPerspective();
	}

	private IWorkbenchPage activePageInternal() {
		return activeWorkbenchWindow().getActivePage();
	}
}
