/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.generator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MethodGeneratorTest {

	private MethodGenerator	inGroup;
	private MethodGenerator	withTextInGroup;
	private MethodGenerator	withStyleWithTextInGroup;
	private MethodGenerator	withStyle;
	private MethodGenerator	withSubclassedWidgets;

	@Test
	public void generatesMethodArgumentsGivenASingleReference() throws Exception {
		assertThat(inGroup.methodArguments(), equalTo("String inGroup"));
	}

	@Test
	public void generatesMethodArgumentsGivenTwoReferences() throws Exception {
		assertThat(withTextInGroup.methodArguments(), equalTo("String text, String inGroup"));
	}

	@Test
	public void generatesMethodContentsWithIndexGivenASingleReference() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n" + "	public SWTBotButton buttonInGroup(String inGroup, int index) {\n"
				+ "		Matcher matcher = allOf(widgetOfType(Button.class), inGroup(inGroup));\n"
				+ "		return new SWTBotButton((Button) widget(matcher, index), matcher);\n" + "	}\n", inGroup.methodContentsWithIndex());
	}

	@Test
	public void generatesMethodContentsWithIndexGivenTwoReferences() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n"
				+ "	public SWTBotButton buttonInGroup(String text, String inGroup, int index) {\n"
				+ "		Matcher matcher = allOf(widgetOfType(Button.class), withText(text), inGroup(inGroup));\n"
				+ "		return new SWTBotButton((Button) widget(matcher, index), matcher);\n" + "	}\n", withTextInGroup
				.methodContentsWithIndex());
	}

	@Test
	public void generatesMethodContentsWithIndexAndStyleGivenTwoReferences() throws Exception {
		assertEquals(
				"	@SuppressWarnings(\"unchecked\")\n"
						+ "	public SWTBotTree treeInGroup(String text, String inGroup, int index) {\n"
						+ "		Matcher matcher = allOf(widgetOfType(Tree.class), withText(text), inGroup(inGroup), withStyle(SWT.FOO_STYLE, \"SWT.FOO_STYLE\"));\n"
						+ "		return new SWTBotTree((Tree) widget(matcher, index), matcher);\n" + "	}\n", withStyleWithTextInGroup
						.methodContentsWithIndex());
	}

	@Test
	public void generatesMethodContentsWithStyleWithNoReferences() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n" + "	public SWTBotTree tree(int index) {\n"
				+ "		Matcher matcher = allOf(widgetOfType(Tree.class), withStyle(SWT.FOO_STYLE, \"SWT.FOO_STYLE\"));\n"
				+ "		return new SWTBotTree((Tree) widget(matcher, index), matcher);\n" + "	}\n", withStyle.methodContentsWithIndex());
	}

	@Test
	public void generatesMethodContentsWithoutIndexGivenASingleReference() throws Exception {
		assertEquals("	public SWTBotButton buttonInGroup(String inGroup) {\n" + "		return buttonInGroup(inGroup, 0);\n" + "	}\n", inGroup
				.methodContents());
	}

	@Test
	public void generatesMethodContentsWithoutIndexGivenTwoReferences() throws Exception {
		assertEquals("	public SWTBotButton buttonInGroup(String text, String inGroup) {\n" + "		return buttonInGroup(text, inGroup, 0);\n"
				+ "	}\n", withTextInGroup.methodContents());
	}
	
	@Test
	public void generatesMethodContentsWithSubclassedTypes() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n" + 
				"	public SWTBotToolbarButton toolbarToggleButtonInGroup(String text, String inGroup, int index) {\n" + 
				"		Matcher matcher = allOf(widgetOfType(ToolItem.class), withText(text), inGroup(inGroup), withStyle(SWT.FOO_STYLE, \"SWT.FOO_STYLE\"));\n" + 
				"		return new SWTBotToolbarPushButton((ToolItem) widget(matcher, index), matcher);\n" + 
				"	}\n" + 
				"", withSubclassedWidgets.methodContentsWithIndex());
	}

	@Test
	public void generatesImports() throws Exception {
		assertThat(inGroup.imports(), hasItem("import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton"));
		assertThat(inGroup.imports(), hasItem("import org.eclipse.swt.widgets.Button"));
	}

	@Before
	public void setUp() throws Exception {
		inGroup = new MethodGenerator(SWTBotButton.class, SWTBotButton.class, Button.class, "button", "SWT.NONE", ReferenceBy.IN_GROUP);
		withTextInGroup = new MethodGenerator(SWTBotButton.class, SWTBotButton.class, Button.class, "button", "SWT.NONE", ReferenceBy.TEXT,
				ReferenceBy.IN_GROUP);
		withStyle = new MethodGenerator(SWTBotTree.class, SWTBotTree.class, Tree.class, "tree", "SWT.FOO_STYLE", ReferenceBy.NONE);
		withStyleWithTextInGroup = new MethodGenerator(SWTBotTree.class, SWTBotTree.class, Tree.class, "tree", "SWT.FOO_STYLE", ReferenceBy.TEXT,
				ReferenceBy.IN_GROUP);
		
		withSubclassedWidgets = new MethodGenerator(SWTBotToolbarButton.class, SWTBotToolbarPushButton.class, ToolItem.class, "toolbarToggleButton", "SWT.FOO_STYLE", ReferenceBy.TEXT,
				ReferenceBy.IN_GROUP);
	}

}
