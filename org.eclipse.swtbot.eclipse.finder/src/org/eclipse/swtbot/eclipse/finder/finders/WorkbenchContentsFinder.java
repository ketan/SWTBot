package org.eclipse.swtbot.eclipse.finder.finders;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
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
 * WorkbenchContentsFinder allows to access the contents of a workbench window.
 * Public methods can be executed in any thread.
 * 
 * @author Ralf Ebert www.ralfebert.de
 */
public class WorkbenchContentsFinder {

	private IWorkbenchWindow workbenchWindow;

	public WorkbenchContentsFinder() {
		workbenchWindow = UIThreadRunnable.syncExec(new Result<IWorkbenchWindow>() {

			public IWorkbenchWindow run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			}
		});

	}

	public WorkbenchContentsFinder(IWorkbenchWindow workbenchWindow) {
		this.workbenchWindow = workbenchWindow;

	}

	public List<IEditorReference> findEditors(final Matcher<?> matcher) {
		return UIThreadRunnable.syncExec(SWTUtils.display(), new ListResult<IEditorReference>() {
			public List<IEditorReference> run() {
				return findEditorsUi(matcher);
			}

		});
	}

	private IWorkbenchPage[] getWorkbenchPages() {
		return workbenchWindow.getPages();
	}

	public List<IViewReference> findViews(final Matcher<?> matcher) {
		return UIThreadRunnable.syncExec(SWTUtils.display(), new ListResult<IViewReference>() {
			public List<IViewReference> run() {
				return findViewsUi(matcher);
			}
		});
	}

	public List<IPerspectiveDescriptor> findPerspectives(final Matcher<?> matcher) {
		return UIThreadRunnable.syncExec(SWTUtils.display(), new ListResult<IPerspectiveDescriptor>() {
			public List<IPerspectiveDescriptor> run() {
				return findPerspectivesUi(matcher);
			}

		});

	}

	private List<IViewReference> findViewsUi(final Matcher<?> matcher) {
		List<IViewReference> result = new ArrayList<IViewReference>();
		IWorkbenchPage[] pages = getWorkbenchPages();
		for (int i = 0; i < pages.length; i++) {
			IWorkbenchPage page = pages[i];
			IViewReference[] viewReferences = page.getViewReferences();
			for (int j = 0; j < viewReferences.length; j++) {
				IViewReference viewReference = viewReferences[j];
				if (matcher.matches(viewReference)) {
					result.add(viewReference);
				}
			}
		}
		return result;
	}

	private List<IPerspectiveDescriptor> findPerspectivesUi(final Matcher<?> matcher) {
		IPerspectiveDescriptor[] perspectives = workbenchWindow.getWorkbench().getPerspectiveRegistry()
				.getPerspectives();
		List<IPerspectiveDescriptor> matchingPerspectives = new ArrayList<IPerspectiveDescriptor>();
		for (IPerspectiveDescriptor perspectiveDescriptor : perspectives) {
			if (matcher.matches(perspectiveDescriptor))
				matchingPerspectives.add(perspectiveDescriptor);
		}
		return matchingPerspectives;
	}

	private List<IEditorReference> findEditorsUi(final Matcher<?> matcher) {
		List<IEditorReference> result = new ArrayList<IEditorReference>();
		IWorkbenchPage[] pages = getWorkbenchPages();
		for (int i = 0; i < pages.length; i++) {
			IWorkbenchPage page = pages[i];
			IEditorReference[] editorReferences = page.getEditorReferences();
			for (int j = 0; j < editorReferences.length; j++) {
				IEditorReference editorReference = editorReferences[j];
				if (matcher.matches(editorReference)) {
					result.add(editorReference);
				}
			}
		}
		return result;
	}

	public IViewReference findActiveView() {
		return UIThreadRunnable.syncExec(new Result<IViewReference>() {
			public IViewReference run() {
				return findActiveViewUi();
			}
		});
	}

	private IViewReference findActiveViewUi() {
		try {
			IWorkbenchPartReference partReference = workbenchWindow.getActivePage().getActivePartReference();
			if (partReference instanceof IViewReference) {
				return (IViewReference) partReference;
			}
			return null;
		} catch (RuntimeException e) {
			return null;
		}
	}

	public IPerspectiveDescriptor findActivePerspective() {
		return UIThreadRunnable.syncExec(new Result<IPerspectiveDescriptor>() {
			public IPerspectiveDescriptor run() {
				return findActivePerspectiveUi();
			}
		});
	}

	private IPerspectiveDescriptor findActivePerspectiveUi() {
		try {
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			return activePage.getPerspective();
		} catch (RuntimeException e) {
			return null;
		}
	}

	public IEditorReference findActiveEditor() {
		return UIThreadRunnable.syncExec(new Result<IEditorReference>() {
			public IEditorReference run() {
				return findActiveEditorUi();
			}
		});
	}

	private IEditorReference findActiveEditorUi() {
		try {
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart activeEditor = activePage.getActiveEditor();
			return (IEditorReference) activePage.getReference(activeEditor);
		} catch (RuntimeException e) {
			return null;
		}
	}
}