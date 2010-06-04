/*******************************************************************************
 * Copyright (c) 2010 Chris Aniszczyk and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Chris Aniszczyk <caniszczyk@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.forms.finder.finders;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotExpandableComposite;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotFormText;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotHyperlink;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotImageHyperlink;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotScrolledFormText;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotSection;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotTreeNode;
import org.eclipse.swtbot.forms.finder.widgets.SWTBotTwistie;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.ChildrenControlFinder;
import org.eclipse.swtbot.swt.finder.finders.ControlFinder;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledFormText;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TreeNode;
import org.eclipse.ui.forms.widgets.Twistie;
import org.hamcrest.Matcher;

/**
 * SWTFormsBot is a {@link SWTBot} with capabilities for testing Eclipse forms.
 * 
 * @author Chris Aniszczyk <caniszczyk@gmail.com>
 * @version $Id$
 */
public class SWTFormsBot extends SWTBot {
	
	public SWTFormsBot() {
		super(new ControlFinder(), new MenuFinder());
	}
	
	/**
	 * Constructs a bot that will match the contents of the given parentWidget.
	 * 
	 * @param parent the parent
	 */
	public SWTFormsBot(Widget parent) {
		super(new ChildrenControlFinder(parent), new MenuFinder());
	}
	
	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotHyperlink hyperlink(String mnemonicText) {
		return hyperlink(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotHyperlink hyperlink(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withMnemonic(mnemonicText));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotHyperlink} with the specified <code>key/value</code>.
	 */
	public SWTBotHyperlink hyperlinkWithId(String key, String value) {
		return hyperlinkWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotHyperlink hyperlinkWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withId(key, value));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotHyperlink} with the specified <code>value</code>.
	 */
	public SWTBotHyperlink hyperlinkWithId(String value) {
		return hyperlinkWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotHyperlink hyperlinkWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class), withId(value));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}


	/**
	 * @return a {@link SWTBotHyperlink} with the specified <code>none</code>.
	 */
	public SWTBotHyperlink hyperlink() {
		return hyperlink(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotHyperlink} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotHyperlink hyperlink(int index) {
		Matcher matcher = allOf(widgetOfType(Hyperlink.class));
		return new SWTBotHyperlink((Hyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotImageHyperlink imageHyperlink(String mnemonicText) {
		return imageHyperlink(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotImageHyperlink imageHyperlink(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withMnemonic(mnemonicText));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>key/value</code>.
	 */
	public SWTBotImageHyperlink imageHyperlinkWithId(String key, String value) {
		return imageHyperlinkWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotImageHyperlink imageHyperlinkWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withId(key, value));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>value</code>.
	 */
	public SWTBotImageHyperlink imageHyperlinkWithId(String value) {
		return imageHyperlinkWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotImageHyperlink imageHyperlinkWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class), withId(value));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}


	/**
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>none</code>.
	 */
	public SWTBotImageHyperlink imageHyperlink() {
		return imageHyperlink(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotImageHyperlink} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotImageHyperlink imageHyperlink(int index) {
		Matcher matcher = allOf(widgetOfType(ImageHyperlink.class));
		return new SWTBotImageHyperlink((ImageHyperlink) widget(matcher, index), matcher);
	}
	
	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotExpandableComposite} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotExpandableComposite expandableComposite(String mnemonicText) {
		return expandableComposite(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotExpandableComposite} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotExpandableComposite expandableComposite(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(ExpandableComposite.class), withMnemonic(mnemonicText));
		return new SWTBotExpandableComposite((ExpandableComposite) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotExpandableComposite} with the specified <code>key/value</code>.
	 */
	public SWTBotExpandableComposite expandableCompositeWithId(String key, String value) {
		return expandableCompositeWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotExpandableComposite} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotExpandableComposite expandableCompositeWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ExpandableComposite.class), withId(key, value));
		return new SWTBotExpandableComposite((ExpandableComposite) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotExpandableComposite} with the specified <code>value</code>.
	 */
	public SWTBotExpandableComposite expandableCompositeWithId(String value) {
		return expandableCompositeWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotExpandableComposite} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotExpandableComposite expandableCompositeWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ExpandableComposite.class), withId(value));
		return new SWTBotExpandableComposite((ExpandableComposite) widget(matcher, index), matcher);
	}
	
	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @return a {@link SWTBotSection} with the specified <code>mnemonicText</code>.
	 */
	public SWTBotSection section(String mnemonicText) {
		return section(mnemonicText, 0);
	}

	/**
	 * @param mnemonicText the mnemonicText on the widget.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSection} with the specified <code>mnemonicText</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSection section(String mnemonicText, int index) {
		Matcher matcher = allOf(widgetOfType(Section.class), withMnemonic(mnemonicText));
		return new SWTBotSection((Section) widget(matcher, index), matcher);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotSection} with the specified <code>key/value</code>.
	 */
	public SWTBotSection sectionWithId(String key, String value) {
		return sectionWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSection} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSection sectionWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(Section.class), withId(key, value));
		return new SWTBotSection((Section) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotSection} with the specified <code>value</code>.
	 */
	public SWTBotSection sectionWithId(String value) {
		return sectionWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotSection} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotSection sectionWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Section.class), withId(value));
		return new SWTBotSection((Section) widget(matcher, index), matcher);
	}
	
	/**
	 * @return a {@link SWTBotFormText}.
	 */
	public SWTBotFormText formText() {
		Matcher matcher = allOf(widgetOfType(FormText.class));
		return new SWTBotFormText((FormText) widget(matcher, 0), matcher);
	}
	
	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotFormText} with the specified <code>key/value</code>.
	 */
	public SWTBotFormText formTextWithId(String key, String value) {
		return formTextWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotFormText} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotFormText formTextWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(FormText.class), withId(key, value));
		return new SWTBotFormText((FormText) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotFormText} with the specified <code>value</code>.
	 */
	public SWTBotFormText formTextWithId(String value) {
		return formTextWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotFormText} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotFormText formTextWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(FormText.class), withId(value));
		return new SWTBotFormText((FormText) widget(matcher, index), matcher);
	}
	
	/**
	 * @return a {@link SWTBotScrolledFormText}.
	 */
	public SWTBotScrolledFormText scrolledFormText() {
		Matcher matcher = allOf(widgetOfType(ScrolledFormText.class));
		return new SWTBotScrolledFormText((ScrolledFormText) widget(matcher, 0), matcher);
	}
	
	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @return a {@link SWTBotScrolledFormText} with the specified <code>key/value</code>.
	 */
	public SWTBotScrolledFormText scrolledFormTextWithId(String key, String value) {
		return scrolledFormTextWithId(key, value, 0);
	}

	/**
	 * @param key the key set on the widget.
	 * @param value the value for the key.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotScrolledFormText} with the specified <code>key/value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotScrolledFormText scrolledFormTextWithId(String key, String value, int index) {
		Matcher matcher = allOf(widgetOfType(ScrolledFormText.class), withId(key, value));
		return new SWTBotScrolledFormText((ScrolledFormText) widget(matcher, index), matcher);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotScrolledFormText} with the specified <code>value</code>.
	 */
	public SWTBotScrolledFormText scrolledFormTextWithId(String value) {
		return scrolledFormTextWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotScrolledFormText} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotScrolledFormText scrolledFormTextWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(ScrolledFormText.class), withId(value));
		return new SWTBotScrolledFormText((ScrolledFormText) widget(matcher, index), matcher);
	}
	
	/**
	 * @return a {@link SWTBotTwistie} with the specified <code>none</code>.
	 */
	public SWTBotTwistie twistie() {
		return twistie(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTwistie} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTwistie twistie(int index) {
		Matcher matcher = allOf(widgetOfType(Twistie.class));
		return new SWTBotTwistie((Twistie) widget(matcher, index), matcher);
	}
	
	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotTwistie} with the specified <code>value</code>.
	 */
	public SWTBotTwistie twistieWithId(String value) {
		return twistieWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTwistie} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTwistie twistieWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(Twistie.class), withId(value));
		return new SWTBotTwistie((Twistie) widget(matcher, index), matcher);
	}
	
	// TODO
	
	/**
	 * @return a {@link SWTBotTreeNode} with the specified <code>none</code>.
	 */
	public SWTBotTreeNode treeNode() {
		return treeNode(0);
	}

	/**
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTreeNode} with the specified <code>none</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTreeNode treeNode(int index) {
		Matcher matcher = allOf(widgetOfType(TreeNode.class));
		return new SWTBotTreeNode((TreeNode) widget(matcher, index), matcher);
	}
	
	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @return a {@link SWTBotTreeNode} with the specified <code>value</code>.
	 */
	public SWTBotTreeNode treeNodeWithId(String value) {
		return treeNodeWithId(value, 0);
	}

	/**
	 * @param value the value for the key {@link org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences#DEFAULT_KEY}.
	 * @param index the index of the widget.
	 * @return a {@link SWTBotTreeNode} with the specified <code>value</code>.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotTreeNode treeNodeWithId(String value, int index) {
		Matcher matcher = allOf(widgetOfType(TreeNode.class), withId(value));
		return new SWTBotTreeNode((TreeNode) widget(matcher, index), matcher);
	}
	
}