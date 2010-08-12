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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.FileUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class SWTBotMultiPageEditorTest extends AbstractSWTBotEclipseTest {

	private File					testFile;
	private SampleMultiPageEditor	editor;

	@Before
	public void before() throws Exception {
		testFile = File.createTempFile("multipage", ".mpe");
		testFile.deleteOnExit();
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				try {
					IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IDE.openEditor(activePage, testFile.toURI(),
							"org.eclipse.swtbot.eclipse.finder.test.ui.editors.MultiPageEditor", true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
		this.editor = new SampleMultiPageEditor(bot.multipageEditorByTitle("Sample Multi-page Editor"), bot);
	}

	@After
	public void after() {
		if (testFile != null) {
			testFile.delete();
		}
	}

	@Test
	public void shouldGetPageCount() throws Exception {
		assertEquals(3, editor.getPageCount());
	}

	@Test
	public void shouldGetActivePage() throws Exception {
		assertTrue(editor.isActive());
		assertEquals(testFile.getName(), editor.getActivePageTitle());
	}

	@Test
	public void shouldGetListOfPageTitles() throws Exception {
		List<String> pages = editor.getPagesTitles();
		assertThat("Pages", pages, hasItems(testFile.getName(), "Properties", "Preview"));
		assertEquals(3, pages.size());
	}

	@Test
	public void shouldAnswerIsActivePageCorrectly() throws Exception {
		assertTrue(editor.isActivePage(testFile.getName()));
		assertTrue(editor.isActivePage(startsWith("multipage")));
		assertFalse(editor.isActivePage("Properties"));
	}

	@Test
	public void shouldSetActivePage() throws Exception {
		assertFalse(editor.isActivePage("Properties"));
		editor.activatePage("Properties");
		assertTrue(editor.isActivePage("Properties"));
		editor.activatePage("Preview");
		assertTrue(editor.isActivePage("Preview"));
	}

	@Test
	public void editorShouldBeMarkedDirtyOnChanges() {
		assertFalse(editor.isDirty());
		editor.getText().setText("Make editor dirty");
		assertTrue(editor.isDirty());
		editor.save();
		assertFalse(editor.isDirty());
	}

	@Test
	public void shouldSaveEditorContents() throws Exception {
		String expectedContent = "Some content...";
		editor.getText().setText(expectedContent);
		editor.save();
		assertEquals(expectedContent, FileUtils.read(testFile));
	}

	private static class SampleMultiPageEditor extends SWTBotMultiPageEditor {
		public SampleMultiPageEditor(SWTBotMultiPageEditor editor, SWTWorkbenchBot bot) {
			super(editor.getReference(), bot);
		}

		public SWTBotStyledText getText() {
			return new SWTBotStyledText((StyledText) findWidget(widgetOfType(StyledText.class)));
		}
	}
}
