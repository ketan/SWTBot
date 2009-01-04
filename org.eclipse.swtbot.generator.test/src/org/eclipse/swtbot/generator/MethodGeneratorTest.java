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
import junit.framework.TestCase;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MethodGeneratorTest extends TestCase {

	private MethodGenerator	inGroup;
	private MethodGenerator	withTextInGroup;
	private MethodGenerator	withStyleWithTextInGroup;
	private MethodGenerator	withStyle;

	public void testGeneratesMethodArgumentsGivenASingleReference() throws Exception {
		assertThat(inGroup.methodArguments(), equalTo("String inGroup"));
	}

	public void testGeneratesMethodArgumentsGivenTwoReferences() throws Exception {
		assertThat(withTextInGroup.methodArguments(), equalTo("String text, String inGroup"));
	}

	public void testGeneratesMethodContentsWithIndexGivenASingleReference() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n" + 
				"	public SWTBotButton buttonInGroup(String inGroup, int index) {\n" + 
				"		Matcher matcher = allOf(widgetOfType(Button.class), inGroup(inGroup));\n" +
				"		return new SWTBotButton((Button) widget(matcher, index), matcher);\n" +
				"	}\n"
				, inGroup.methodContentsWithIndex());
	}

	public void testGeneratesMethodContentsWithIndexGivenTwoReferences() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n" +
				"	public SWTBotButton buttonInGroup(String text, String inGroup, int index) {\n" + 
				"		Matcher matcher = allOf(widgetOfType(Button.class), withText(text), inGroup(inGroup));\n" +
				"		return new SWTBotButton((Button) widget(matcher, index), matcher);\n" + 
				"	}\n"
				, withTextInGroup.methodContentsWithIndex());
	}

	public void testGeneratesMethodContentsWithIndexAndStyleGivenTwoReferences() throws Exception {
		assertEquals(
				"	@SuppressWarnings(\"unchecked\")\n" +
				"	public SWTBotTree treeInGroup(String text, String inGroup, int index) {\n" +
				"		Matcher matcher = allOf(widgetOfType(Tree.class), withText(text), inGroup(inGroup), withStyle(SWT.FOO_STYLE, \"SWT.FOO_STYLE\"));\n" +
				"		return new SWTBotTree((Tree) widget(matcher, index), matcher);\n" +
				"	}\n"
				, withStyleWithTextInGroup.methodContentsWithIndex());
	}

	public void testGeneratesMethodContentsWithStyleWithNoReferences() throws Exception {
		assertEquals("	@SuppressWarnings(\"unchecked\")\n" + 
				"	public SWTBotTree tree(int index) {\n" +
				"		Matcher matcher = allOf(widgetOfType(Tree.class), withStyle(SWT.FOO_STYLE, \"SWT.FOO_STYLE\"));\n" +
				"		return new SWTBotTree((Tree) widget(matcher, index), matcher);\n" +
				"	}\n"
				, withStyle.methodContentsWithIndex());
	}

	public void testGeneratesMethodContentsWithoutIndexGivenASingleReference() throws Exception {
		assertEquals(
				"	public SWTBotButton buttonInGroup(String inGroup) {\n" + 
				"		return buttonInGroup(inGroup, 0);\n" 
				+ "	}\n", inGroup
				.methodContents());
	}

	public void testGeneratesMethodContentsWithoutIndexGivenTwoReferences() throws Exception {
		assertEquals(
				"	public SWTBotButton buttonInGroup(String text, String inGroup) {\n"
				+ "		return buttonInGroup(text, inGroup, 0);\n"
				+ "	}\n", withTextInGroup.methodContents());
	}

	public void testGeneratesImports() throws Exception {
		assertThat(inGroup.imports(), hasItem("import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton"));
		assertThat(inGroup.imports(), hasItem("import org.eclipse.swt.widgets.Button"));
	}

	protected void setUp() throws Exception {
		inGroup = new MethodGenerator(SWTBotButton.class, Button.class, "button", "SWT.NONE", ReferenceBy.IN_GROUP);
		withTextInGroup = new MethodGenerator(SWTBotButton.class, Button.class, "button", "SWT.NONE", ReferenceBy.TEXT,
				ReferenceBy.IN_GROUP);
		withStyle = new MethodGenerator(SWTBotTree.class, Tree.class, "tree", "SWT.FOO_STYLE", ReferenceBy.NONE);
		withStyleWithTextInGroup = new MethodGenerator(SWTBotTree.class, Tree.class, "tree", "SWT.FOO_STYLE",
				ReferenceBy.TEXT, ReferenceBy.IN_GROUP);
	}

}
