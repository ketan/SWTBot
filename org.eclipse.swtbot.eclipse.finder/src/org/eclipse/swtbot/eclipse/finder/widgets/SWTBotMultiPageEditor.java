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
package org.eclipse.swtbot.eclipse.finder.widgets;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.matchers.WithItem;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.ui.IEditorReference;
import org.hamcrest.Matcher;

/**
 * Base class for all multi-page editors in Eclipse.
 * <p>
 * <b>Note:</b> It is recommend that clients decorate this class with their own custom wrappers.
 * </p>
 * 
 * <pre>
 * public class PDEManifestEditor extends SWTBotMultiPageEditor {
 * 	public PDEManifestEditor(SWTBotMultiPageEditor editor, SWTWorkbenchBot bot) {
 * 		super(editor.getReference(), bot);
 * 		this.editor = editor;
 * 	}
 * 
 * 	// any custom behavior you need to test
 * 	public activateBuildPropertiesTab() {
 * 		activatePage(&quot;build.properties&quot;);
 * 	}
 * 
 * 	public SWTBotStyledText buildProperties() {
 * 		activateBuildPropertiesTab();
 * 		return SWTBotStyledText((StyledText) findWidget(widgetOfType(StyledText.class)));
 * 	}
 * }
 * </pre>
 * 
 * @author Ketan Patel
 * @author Ketan Padegaonkar
 * @since 2.0
 */
public class SWTBotMultiPageEditor extends SWTBotEditor {
	/**
	 * The tabFolder widget.
	 */
	protected final CTabFolder	tabFolder;

	/**
	 * Constructs an instance of the given object.
	 * 
	 * @param editorReference the editor reference.
	 * @param bot the instance of {@link SWTWorkbenchBot} which will be used to drive operations on behalf of this
	 *            object.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotMultiPageEditor(IEditorReference editorReference, SWTWorkbenchBot bot) {
		super(editorReference, bot);
		tabFolder = (CTabFolder) findWidget(widgetOfType(CTabFolder.class));
	}

	/**
	 * Find the CTabItem whose text matches the given matcher.
	 * 
	 * @param titleMatcher the text matcher
	 * @return a {@link SWTBotCTabItem} with the specified tab name.
	 */
	private SWTBotCTabItem findPage(Matcher<? extends Widget> titleMatcher) {
		WithItem<CTabItem> itemMatcher = WithItem.withItem(allOf(widgetOfType(CTabItem.class), titleMatcher));
		if (itemMatcher.matches(tabFolder))
			return new SWTBotCTabItem(itemMatcher.get(0));
		throw new WidgetNotFoundException("Could not find page with title " + titleMatcher);
	}

	/**
	 * Returns the number of pages in this multi-page editor.
	 * 
	 * @return the number of pages
	 */
	public int getPageCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return tabFolder.getItemCount();
			}
		});
	}

	/**
	 * Sets the currently active page.
	 * 
	 * @param pageText the text label for the page to be activated
	 * @return the {@link CTabItem} that was activated.
	 */
	public SWTBotCTabItem activatePage(String pageText) {
		return activatePage(withMnemonic(pageText));
	}

	/**
	 * Sets the currently active page.
	 * 
	 * @param titleMatcher the title matcher for the page to be activated.
	 * @return the {@link CTabItem} that was activated.
	 */
	public SWTBotCTabItem activatePage(Matcher<? extends Widget> titleMatcher) {
		return findPage(titleMatcher).show().activate();
	}

	/**
	 * Returns the title of the currently active page or <code>null</code> if there is no active page
	 * 
	 * @return the title of the currently active page or <code>null</code> if there is no active page
	 */
	public String getActivePageTitle() {
		CTabItem tab = tabFolder.getSelection();
		if (tab != null) {
			return new SWTBotCTabItem(tab).getText();
		}
		return null;
	}

	/**
	 * Returns a list of title of all the pages in this multi-page editor.
	 * 
	 * @return List of title of all pages; empty list if no pages.
	 */
	public List<String> getPagesTitles() {
		List<String> pages = null;
		if (getPageCount() > 0) {
			pages = UIThreadRunnable.syncExec(new Result<List<String>>() {
				public List<String> run() {
					ArrayList<String> titles = new ArrayList<String>();
					for (CTabItem item : tabFolder.getItems()) {
						titles.add(item.getText());
					}
					return titles;
				}
			});
		}
		return pages == null ? new ArrayList<String>() : pages;
	}

	/**
	 * @param pageText the page title to test
	 * @return <code>true</code> if the currently active page has given title, <code>false</code> otherwise.
	 */
	public boolean isActivePage(String pageText) {
		return isActivePage(equalTo(pageText));
	}

	/**
	 * @param titleMatcher the title matcher for the active page
	 * @return <code>true</code> if the currently active page title matches, <code>false</code> otherwise.
	 */
	public boolean isActivePage(Matcher<String> titleMatcher) {
		return titleMatcher.matches(getActivePageTitle());
	}
}
