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
package org.eclipse.swtbot.generator;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class HamcrestFactoryWriter implements HamcrestWriter {

	private final String		packageName;
	private final String		shortClassName;
	private final PrintWriter	output;

	private final String		newLine	= "\n"; //$NON-NLS-1$

	public HamcrestFactoryWriter(String packageName, String shortClassName, FileWriter fileWriter) {
		this.packageName = packageName;
		this.shortClassName = shortClassName;
		output = new PrintWriter(fileWriter);

	}

	public void writeHeader(Set<String> imports) {
		output.append("// Generated source.").append(newLine); //$NON-NLS-1$
		output.append("package ").append(packageName).append(';').append(newLine); //$NON-NLS-1$
		output.append(newLine);
		output.append(newLine);
		writeImports(imports);
	}

	public void beginClassDefinition() {
		output.append(newLine);
		output.append(newLine);
		output.append("/**\n" + //$NON-NLS-1$
				" * This class contains convenience API to find widgets in SWTBot.\n" + //$NON-NLS-1$
				" * Most users would start off as follows: \n" + //$NON-NLS-1$
				" * \n" + //$NON-NLS-1$
				" * <pre>\n" + //$NON-NLS-1$
				" *    SWTBot bot = new SWTBot();\n" + //$NON-NLS-1$
				" *    \n" + //$NON-NLS-1$
				" *    bot.button(&quot;hello world&quot;).click();\n" + //$NON-NLS-1$
				" *    \n" + //$NON-NLS-1$
				" *    // in case you have two edit buttons in two different groups\n" + //$NON-NLS-1$
				" *    // say an edit button in the &quot;Address&quot; section,\n" + //$NON-NLS-1$
				" *    // and another in &quot;Bank Account&quot; section, you can do the following\n" + //$NON-NLS-1$
				" *    // to click on the &quot;Edit&quot; button on the &quot;Bank Account&quot; section.\n" + //$NON-NLS-1$
				" *    // This is the recommended way to use SWTBot, instead of finding widgets based on its index.\n" + //$NON-NLS-1$
				" *    bot.buttonInGroup(&quot;Edit&quot;, &quot;Bank Account&quot;).click();\n" + //$NON-NLS-1$
				" * </pre>\n" + //$NON-NLS-1$
				" * \n" + //$NON-NLS-1$
				" * For finding widgets using custom matchers:\n" + //$NON-NLS-1$
				" * \n" + //$NON-NLS-1$
				" * <pre>\n" + //$NON-NLS-1$
				" *    SWTBot bot = new SWTBot();\n" + //$NON-NLS-1$
				" *    //\n" + //$NON-NLS-1$
				" *    // find a button within the currently active shell:\n" + //$NON-NLS-1$
				" *    //\n" + //$NON-NLS-1$
				" *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher)); // or\n" + //$NON-NLS-1$
				" *    SWTBotButton button = new SWTBotButton((Button)bot.widget(aMatcher, 3)); // for the 4th widget\n" + //$NON-NLS-1$
				" *    //\n" + //$NON-NLS-1$
				" *    // to find a button within a particular parent composite:\n" + //$NON-NLS-1$
				" *    //\n" + //$NON-NLS-1$
				" *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher, parentComposite)); //or\n" + //$NON-NLS-1$
				" *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher, parentComposite, 3)); //for the 4th widget\n" + //$NON-NLS-1$
				" * </pre>\n" + //$NON-NLS-1$
				" *\n" + //$NON-NLS-1$
				" * @version $Id$\n" + //$NON-NLS-1$
				" */\n"); //$NON-NLS-1$
		output.append("public class ").append(shortClassName).append(" extends SWTBotFactory {").append(newLine).append(newLine); //$NON-NLS-1$ //$NON-NLS-2$
	}



	public void writeFooter() {
		output.append("	private Matcher<? extends List> withLabel(String label) {\n"
				+ "		return WidgetMatcherFactory.withLabel(label, finder);\n"
				+ "	}\n\n");
		output.append('}').append(newLine);
	}

	public void close() {
		output.close();
	}

	public void flush() {
		output.flush();
	}

	public void writeMethod(String method) {
		output.append(method.toString()).append(newLine);
	}

	private void writeImports(Set<String> imports) {

		imports = new TreeSet<String>(imports);

		imports.add("import org.eclipse.swt.SWT"); //$NON-NLS-1$

		imports.add("import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withId"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMessage"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.inGroup"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withStyle"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withTooltip"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType"); //$NON-NLS-1$
		imports.add("import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf"); //$NON-NLS-1$
		imports.add("import org.eclipse.swtbot.swt.finder.finders.ControlFinder"); //$NON-NLS-1$
		imports.add("import org.eclipse.swtbot.swt.finder.finders.ChildrenControlFinder"); //$NON-NLS-1$
		imports.add("import org.eclipse.swtbot.swt.finder.finders.Finder"); //$NON-NLS-1$
		imports.add("import org.eclipse.swtbot.swt.finder.finders.MenuFinder"); //$NON-NLS-1$
		imports.add("import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton"); //$NON-NLS-1$
		imports.add("import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory"); //$NON-NLS-1$
		imports.add("import org.eclipse.swt.widgets.Widget"); //$NON-NLS-1$

		imports.add("import org.hamcrest.Matcher"); //$NON-NLS-1$

		for (String importz : imports) {
			output.append(importz).append(";").append(newLine); //$NON-NLS-1$
		}

	}

	public void beginConstructors() {
		output.append("	/**\n" +
				"	 * Constructs a bot.\n" +
				"	 */\n" +
				"	public SWTBot() {\n" +
				"		this(new ControlFinder(), new MenuFinder());\n" +
				"	}\n" +
				"\n" +
				"	/**\n" + 
				"	 * Constructs a bot that will match the contents of the given parentWidget.\n" + 
				"	 * \n" + 
				"	 * @param parent the parent\n" + 
				"	 */\n" + 
				"	public SWTBot(Widget parent) {\n" + 
				"		this(new ChildrenControlFinder(parent), new MenuFinder());\n" + 
				"	}\n" +
				"	/**\n" +
				"	 * Constructs an instance of the bot using the given control finder and menu finder.\n" +
				"	 * \n" +
				"	 * @param controlFinder the {@link ControlFinder} used to identify and find controls.\n" +
				"	 * @param menuFinder the {@link MenuFinder} used to find menu items.\n" +
				"	 */\n" +
				"	public SWTBot(ControlFinder controlFinder, MenuFinder menuFinder) {\n" +
				"		this(new Finder(controlFinder, menuFinder));\n" +
				"	}\n" +
				"\n" +
				"	/**\n" +
				"	 * Constructs a bot with the given finder.\n" +
				"	 * \n" +
				"	 * @param finder the finder.\n" +
				"	 */\n" +
				"	public SWTBot(Finder finder) {\n" +
				"		super(finder);\n" +
				"	}\n\n");
	}
}
