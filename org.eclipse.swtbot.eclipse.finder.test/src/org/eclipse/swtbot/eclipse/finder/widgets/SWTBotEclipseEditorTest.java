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
package org.eclipse.swtbot.eclipse.finder.widgets;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertContains;
import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertDoesNotContain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.helpers.NewJavaClass;
import org.eclipse.swtbot.eclipse.finder.widgets.helpers.NewJavaProject;
import org.eclipse.swtbot.eclipse.finder.widgets.helpers.PackageExplorerView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class SWTBotEclipseEditorTest {

	private static final String	PROJECT_NAME		= "FooBarProject";
	private static final String	PACKAGE_NAME		= "org.eclipse.swtbot.eclipse.test";
	private static final String	CLASS_NAME			= "HelloWorld";
	private static final String	CLASS_FILE_NAME		= CLASS_NAME + ".java";

	private NewJavaClass		javaClass			= new NewJavaClass();
	private NewJavaProject		javaProject			= new NewJavaProject();
	private PackageExplorerView	packageExplorerView	= new PackageExplorerView();
	private SWTWorkbenchBot		bot					= new SWTWorkbenchBot();
	private SWTBotEclipseEditor	editor;

	@Test
	public void getsAutoCompleteProposals() throws Exception {
		editor.navigateTo(3, 0);
		List<String> autoCompleteProposals = editor.getAutoCompleteProposals("JFr");
		assertEquals(autoCompleteProposals.toString(), 2, autoCompleteProposals.size());
		assertEquals("JFrame - javax.swing", autoCompleteProposals.get(0));
		String string = autoCompleteProposals.get(1);
		assertTrue(string.equals("JFr()  void - Method stub") || string.equals("JFr() : void - Method stub"));
	}

	@Test
	public void canAutoCompleteProposals() throws Exception {
		editor.navigateTo(3, 0);
		assertDoesNotContain("public static void main", editor.getText());
		editor.autoCompleteProposal("main", "main - main method");
		assertContains("public static void main", editor.getText());
	}

	@Before
	public void setUp() throws Exception {
		closeWelcomePage();
		javaProject.createProject(PROJECT_NAME);

		javaClass.createClass(PACKAGE_NAME, CLASS_NAME);
		editor = bot.editorByTitle(CLASS_FILE_NAME).toTextEditor();
		editor.save();
	}

	private void closeWelcomePage() {
		try {
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
			// do nothing
		}
	}

	@After
	public void tearDown() throws Exception {
		saveAndCloseAllEditors();
		packageExplorerView.deleteProject(PROJECT_NAME);
	}

	/**
	 * @throws WidgetNotFoundException
	 */
	private void saveAndCloseAllEditors() throws WidgetNotFoundException {
		List<? extends SWTBotEditor> editors = bot.editors();
		for (SWTBotEditor editor : editors) {
			editor.saveAndClose();
		}
	}

	@Test
	public void isActiveIsTrueForActiveEditor() throws Exception {
		javaClass.createClass("com.foo.example", "FooClass");
		javaClass.createClass("com.foo.example", "BarClass");
		javaClass.createClass("com.foo.example", "BazClass");

		assertTrue(bot.activeEditor().isActive());
		assertFalse(bot.editorByTitle("FooClass.java").isActive());
		assertFalse(bot.editorByTitle("BarClass.java").isActive());
		assertTrue(bot.editorByTitle("BazClass.java").isActive());
	}
}
