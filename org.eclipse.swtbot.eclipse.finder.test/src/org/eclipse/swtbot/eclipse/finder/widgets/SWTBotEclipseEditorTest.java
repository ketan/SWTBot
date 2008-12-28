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

import java.util.Iterator;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTBotEclipseTestCase;
import org.eclipse.swtbot.eclipse.finder.widgets.helpers.NewJavaClass;
import org.eclipse.swtbot.eclipse.finder.widgets.helpers.NewJavaProject;
import org.eclipse.swtbot.eclipse.finder.widgets.helpers.PackageExplorerView;
import org.eclipse.swtbot.swt.finder.widgets.WidgetNotFoundException;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotEclipseEditorTest extends SWTBotEclipseTestCase {

	private static final String	PROJECT_NAME		= "FooBarProject";
	private static final String	PACKAGE_NAME		= "org.eclipse.swtbot.eclipse.test";
	private static final String	CLASS_NAME			= "HelloWorld";
	private static final String	CLASS_FILE_NAME		= CLASS_NAME + ".java";

	NewJavaClass				javaClass			= new NewJavaClass();
	NewJavaProject				javaProject			= new NewJavaProject();
	PackageExplorerView			packageExplorerView	= new PackageExplorerView();
	private SWTBotEclipseEditor	editor;

	public void testGetsAutoCompleteProposals() throws Exception {
		editor.navigateTo(3, 0);
		List autoCompleteProposals = editor.getAutoCompleteProposals("JFr");
		assertEquals(2, autoCompleteProposals.size());
		assertEquals("JFrame - javax.swing", autoCompleteProposals.get(0));
		String string = (String) autoCompleteProposals.get(1);
		assertTrue(string.equals("JFr()  void - Method stub") || string.equals("JFr() : void - Method stub"));
	}

	public void testCanAutoCompleteProposals() throws Exception {
		editor.navigateTo(3, 0);
		assertDoesNotContain("public static void main", editor.getText());
		editor.autoCompleteProposal("main", "main - main method");
		assertContains("public static void main", editor.getText());
	}

	public static void assertDoesNotContain() {

	}

	protected void setUp() throws Exception {
		super.setUp();
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

	protected void tearDown() throws Exception {
		super.tearDown();
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

	public void testIsActiveIsTrueForActiveEditor() throws Exception {
		javaClass.createClass("com.foo.example", "FooClass");
		javaClass.createClass("com.foo.example", "BarClass");
		javaClass.createClass("com.foo.example", "BazClass");

		assertTrue(bot.activeEditor().isActive());
		assertFalse(bot.editor("FooClass.java").isActive());
		assertFalse(bot.editor("BarClass.java").isActive());
		assertTrue(bot.editor("BazClass.java").isActive());
	}
}
