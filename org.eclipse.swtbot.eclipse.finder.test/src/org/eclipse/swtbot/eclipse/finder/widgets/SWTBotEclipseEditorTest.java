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

import java.util.Iterator;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
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

	NewJavaClass				javaClass			= new NewJavaClass();
	NewJavaProject				javaProject			= new NewJavaProject();
	PackageExplorerView			packageExplorerView	= new PackageExplorerView();
	private SWTBotEclipseEditor	editor;
	private SWTEclipseBot		bot;

	@Test
	public void getsAutoCompleteProposals() throws Exception {
		editor.navigateTo(3, 0);
		List autoCompleteProposals = editor.getAutoCompleteProposals("JFr");
		assertEquals(2, autoCompleteProposals.size());
		assertEquals("JFrame - javax.swing", autoCompleteProposals.get(0));
		String string = (String) autoCompleteProposals.get(1);
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
		bot = new SWTEclipseBot();
		closeWelcomePage();
		javaProject.createProject(PROJECT_NAME);

		javaClass.createClass(PACKAGE_NAME, CLASS_NAME);
		editor = bot.editor(CLASS_FILE_NAME);
	}

	private void closeWelcomePage() {
		try {
			bot.view("Welcome").close();
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
		List editors = bot.editors();
		for (Iterator iterator = editors.iterator(); iterator.hasNext();) {
			SWTBotEclipseEditor editor = (SWTBotEclipseEditor) iterator.next();
			editor.saveAndClose();
		}
	}

	@Test
	public void isActiveIsTrueForActiveEditor() throws Exception {
		javaClass.createClass("com.foo.example", "FooClass");
		javaClass.createClass("com.foo.example", "BarClass");
		javaClass.createClass("com.foo.example", "BazClass");

		assertTrue(bot.activeEditor().isActive());
		assertFalse(bot.editor("FooClass.java").isActive());
		assertFalse(bot.editor("BarClass.java").isActive());
		assertTrue(bot.editor("BazClass.java").isActive());
	}
}
