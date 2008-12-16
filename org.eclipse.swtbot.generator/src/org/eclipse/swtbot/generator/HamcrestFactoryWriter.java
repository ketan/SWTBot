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

	private final String		newLine	= "\n";

	public HamcrestFactoryWriter(String packageName, String shortClassName, FileWriter fileWriter) {
		this.packageName = packageName;
		this.shortClassName = shortClassName;
		output = new PrintWriter(fileWriter);

	}

	public void writeHeader(Set<String> imports) {
		output.append("// Generated source.").append(newLine);
		output.append("package ").append(packageName).append(';').append(newLine);
		output.append(newLine);
		output.append(newLine);
		writeImports(imports);
	}

	public void beginClassDefinition() {
		output.append(newLine);
		output.append(newLine);
		output.append("/**\n" +
				" * This class contains convenience API to find widgets in SWTBot.\n" +
				" * Most users would start off as follows: \n" +
				" * \n" +
				" * <pre>\n" +
				" *    SWTBot bot = new SWTBot();\n" +
				" *    \n" +
				" *    bot.button(&quot;hello world&quot;).click();\n" +
				" *    \n" +
				" *    // in case you have two edit buttons in two different groups\n" +
				" *    // say an edit button in the &quot;Address&quot; section,\n" +
				" *    // and another in &quot;Bank Account&quot; section, you can do the following\n" +
				" *    // to click on the &quot;Edit&quot; button on the &quot;Bank Account&quot; section.\n" +
				" *    // This is the recommended way to use SWTBot, instead of finding widgets based on its index.\n" +
				" *    bot.buttonInGroup(&quot;Edit&quot;, &quot;Bank Account&quot;).click();\n" +
				" * </pre>\n" +
				" * \n" +
				" * For finding widgets using custom matchers:\n" +
				" * \n" +
				" * <pre>\n" +
				" *    SWTBot bot = new SWTBot();\n" +
				" *    //\n" +
				" *    // find a button within the currently active shell:\n" +
				" *    //\n" +
				" *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher)); // or\n" +
				" *    SWTBotButton button = new SWTBotButton((Button)bot.widget(aMatcher, 3)); // for the 4th widget\n" +
				" *    //\n" +
				" *    // to find a button within a particular parent composite:\n" +
				" *    //\n" +
				" *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher, parentComposite)); //or\n" +
				" *    SWTBotButton button = new SWTBotButton((Button) bot.widget(aMatcher, parentComposite, 3)); //for the 4th widget\n" +
				" * </pre>\n" +
				" *\n" +
				" * @version $Id$\n" +
				" */\n");
		output.append("public class ").append(shortClassName).append(" extends SWTBotFactory {").append(newLine).append(newLine);
	}



	public void writeFooter() {
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

		imports.add("import org.eclipse.swt.SWT");

		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withMnemonic");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withText");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withId");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withLabel");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withText");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.inGroup");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withStyle");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.withTooltip");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.widgetOfType");
		imports.add("import static org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.allOf");

		imports.add("import org.hamcrest.Matcher");

		for (String importz : imports) {
			output.append(importz).append(";").append(newLine);
		}

	}
}
