package org.eclipse.swtbot.eclipse.finder;

import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPartName;
import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPerspectiveId;
import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPerspectiveLabel;
import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForEditor;
import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForView;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.exceptions.ResultNotUniqueException;
import org.eclipse.swtbot.eclipse.finder.finders.WorkbenchContentsFinder;
import org.eclipse.swtbot.eclipse.finder.waits.WaitForEditor;
import org.eclipse.swtbot.eclipse.finder.waits.WaitForView;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewReference;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * SWTWorkbenchBot is a {@link SWTBot} with capabilities for testing Eclipse
 * workbench items like views, editors and perspectives.
 * 
 * @author Ralf Ebert www.ralfebert.de
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTWorkbenchBot extends SWTBot {

	private WorkbenchContentsFinder workbenchContentsFinder;

	/**
	 * Constructs a workbench bot.
	 */
	public SWTWorkbenchBot() {
		super();
		this.workbenchContentsFinder = new WorkbenchContentsFinder();
	}

	/**
	 * Returns the perspective matching the given matcher.
	 * 
	 * @throws WidgetNotFoundException if the perspective is not found.
	 * @throws ResultNotUniqueException if more then one perspective matches.
	 */
	public SWTBotPerspective perspective(Matcher<?> matcher) throws WidgetNotFoundException {
		List<IPerspectiveDescriptor> perspectives = workbenchContentsFinder.findPerspectives(matcher);
		
		if (perspectives.size() > 1) {
			throw new ResultNotUniqueException(matcher, perspectives);
		}
		
		return new SWTBotPerspective(perspectives.get(0), this);
	}

	/**
	 * Shortcut for perspective(withPerspectiveLabel(label))
	 */
	public SWTBotPerspective perspectiveByLabel(String label) throws WidgetNotFoundException {
		return perspective(withPerspectiveLabel(label));
	}

	/**
	 * Shortcut for perspective(perspectiveById(label))
	 */
	public SWTBotPerspective perspectiveById(String id) throws WidgetNotFoundException {
		return perspective(withPerspectiveId(id));
	}
	
	/**
	 * @return all available, matching perspectives.
	 * @param matcher Matcher for IPerspectiveDescriptor
	 */
	public List<SWTBotPerspective> perspectives(Matcher<?> matcher) {
		List<IPerspectiveDescriptor> perspectives = workbenchContentsFinder.findPerspectives(matcher);
		
		List<SWTBotPerspective> perspectiveBots = new ArrayList<SWTBotPerspective>();
		for (IPerspectiveDescriptor perspectiveDescriptor : perspectives) {
			perspectiveBots.add(new SWTBotPerspective(perspectiveDescriptor, this));
		}
		
		return perspectiveBots;
	}

	/**
	 * @return all available perspectives.
	 */
	public List<SWTBotPerspective> perspectives() {
		return perspectives(Matchers.anything());
	}
	
	/**
	 * Waits for a view matching the given matcher to appear in the active
	 * workbench page and returns it.
	 * 
	 * @throws WidgetNotFoundException if the view is not found.
	 * @throws ResultNotUniqueException if more then one view matches.
	 */
	public SWTBotView view(Matcher<?> matcher) {
		WaitForView waitForView = waitForView(matcher);
		waitUntilWidgetAppears(waitForView);
		
		if (waitForView.getAllMatches().size() > 1) {
			throw new ResultNotUniqueException(matcher, waitForView.getAllMatches());
		}
		
		return new SWTBotView(waitForView.get(0), this);
	}

	/**
	 * Shortcut for view(withPartName(title))
	 */
	public SWTBotView viewByTitle(String title) {
		return view(withPartName(title));
	}

	/**
	 * Shortcut for view(withId(id))
	 */
	public SWTBotView viewById(String id) {
		return view(withId(id));
	}
	
	/**
	 * Returns all views which are opened currently (no waiting!) which
	 * match the given matcher.
	 */
	public List<SWTBotView> views(Matcher<?> matcher) {
		List<IViewReference> views = workbenchContentsFinder.findViews(matcher);
		
		List<SWTBotView> viewBots = new ArrayList<SWTBotView>();
		for (IViewReference viewReference : views) {
			viewBots.add(new SWTBotView(viewReference, this));
		}
		
		return viewBots;
	}

	/**
	 * @return all views which are opened currently.
	 */
	public List<SWTBotView> views() {
		return views(Matchers.anything());
	}
	
	/**
	 * Returns the active workbench view part.
	 * 
	 * @return the active view, if any
	 * @throws WidgetNotFoundException if there is no active view.
	 */
	public SWTBotView activeView() throws WidgetNotFoundException {
		IViewReference view = workbenchContentsFinder.findActiveView();
		if (view == null)
			throw new WidgetNotFoundException("There is no active view"); //$NON-NLS-1$
		return new SWTBotView(view, this);
	}

	/**
	 * Waits for a editor matching the given matcher to appear in the active
	 * workbench page and returns it.
	 * 
	 * @throws WidgetNotFoundException if the editor is not found.
	 * @throws ResultNotUniqueException if more then one editor matches.
	 */
	public SWTBotEditor editor(Matcher<?> matcher) {
		WaitForEditor waitForEditor = waitForEditor(matcher);
		waitUntilWidgetAppears(waitForEditor);
		
		if (waitForEditor.getAllMatches().size() > 1) {
			throw new ResultNotUniqueException(matcher, waitForEditor.getAllMatches());
		}
		
		return new SWTBotEditor(waitForEditor.get(0), this);
	}
	
	/**
	 * Returns all editors which are opened currently (no waiting!) which
	 * match the given matcher.
	 */
	public List<SWTBotEditor> editors(Matcher<?> matcher) {
		List<IEditorReference> editors = workbenchContentsFinder.findEditors(matcher);
		
		List<SWTBotEditor> editorBots = new ArrayList<SWTBotEditor>();
		for (IEditorReference editorReference : editors) {
			editorBots.add(new SWTBotEditor(editorReference, this));
		}
		
		return editorBots;
	}

	/**
	 * @return all editors which are opened currently.
	 */
	public List<? extends SWTBotEditor> editors() {
		return editors(Matchers.anything());
	}

	/**
	 * Shortcut for editor(withPartName(title))
	 */
	public SWTBotEditor editorByTitle(String title) {
		return editor(withPartName(title));
	}

	/**
	 * Shortcut for editor(withId(id))
	 */
	public SWTBotEditor editorById(String id) {
		return editor(withId(id));
	}
	
	/**
	 * Returns the active workbench editor part.
	 * 
	 * @return the active editor, if any
	 * @throws WidgetNotFoundException if there is no active view.
	 */
	public SWTBotEditor activeEditor() throws WidgetNotFoundException {
		IEditorReference editor = workbenchContentsFinder.findActiveEditor();
		if (editor == null)
			throw new WidgetNotFoundException("There is no active editor"); //$NON-NLS-1$
		return new SWTBotEditor(editor, this);
	}

	/**
	 * @return the active perspective in the active workbench page.
	 */
	public SWTBotPerspective activePerspective() {
		IPerspectiveDescriptor perspective = workbenchContentsFinder.findActivePerspective();
		if (perspective == null)
			throw new WidgetNotFoundException("There is no active perspective"); //$NON-NLS-1$
		return new SWTBotPerspective(perspective, this);
	}
}
