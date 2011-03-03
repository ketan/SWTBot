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

import org.eclipse.swtbot.swt.finder.utils.FileUtils;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class HamcrestFactoryWriter implements HamcrestWriter {

	private final String		packageName;
	private final String		shortClassName;
	private final PrintWriter	output;

	private final String		newLine	= "\n"; //$NON-NLS-1$
	private final String	superClass;

	public HamcrestFactoryWriter(String packageName, String shortClassName, String superClass, FileWriter fileWriter) {
		this.packageName = packageName;
		this.shortClassName = shortClassName;
		this.superClass = superClass;
		output = new PrintWriter(fileWriter);

	}

	public void writeHeader(Set<String> imports) {
		output.append("// Generated source. DO NOT MODIFY.").append(newLine); //$NON-NLS-1$
		output.append("// To add new widgets, please see README file in the generator plugin.").append(newLine); //$NON-NLS-1$
		output.append("package ").append(packageName).append(';').append(newLine); //$NON-NLS-1$
		output.append(newLine);
		output.append(newLine);
		writeImports(imports);
	}

	public void beginClassDefinition() {
		output.append(newLine);
		output.append(newLine);

		output.append(FileUtils.read("templates/" + shortClassName + "/javadoc").trim()); //$NON-NLS-1$
		output.append(newLine);
		output.append("public class ").append(shortClassName).append(" extends "+ superClass + " {").append(newLine).append(newLine); //$NON-NLS-1$ //$NON-NLS-2$
	}



	public void writeFooter() {
		output.append(newLine);
		output.append(FileUtils.read("templates/" + shortClassName + "/additionalMethods")); //$NON-NLS-1$

		output.append(newLine);
		
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
		imports.addAll(FileUtils.readlines("templates/" + shortClassName + "/imports"));


		for (String importz : imports) {
			output.append(importz).append(";").append(newLine); //$NON-NLS-1$
		}

	}

	public void beginConstructors() {
		output.append("	/**\n" +
				"	 * Constructs a bot.\n" +
				"	 */\n" +
				"	public " + shortClassName + "() {\n" +
				"		this(new ControlFinder(), new MenuFinder());\n" +
				"	}\n" +
				"\n" +
				"	/**\n" + 
				"	 * Constructs a bot that will match the contents of the given parentWidget.\n" + 
				"	 * \n" + 
				"	 * @param parent the parent\n" + 
				"	 */\n" + 
				"	public " + shortClassName + "(Widget parent) {\n" + 
				"		this(new ChildrenControlFinder(parent), new MenuFinder());\n" + 
				"	}\n" +
				"	/**\n" +
				"	 * Constructs an instance of the bot using the given control finder and menu finder.\n" +
				"	 * \n" +
				"	 * @param controlFinder the {@link ControlFinder} used to identify and find controls.\n" +
				"	 * @param menuFinder the {@link MenuFinder} used to find menu items.\n" +
				"	 */\n" +
				"	public " + shortClassName + "(ControlFinder controlFinder, MenuFinder menuFinder) {\n" +
				"		this(new Finder(controlFinder, menuFinder));\n" +
				"	}\n" +
				"\n" +
				"	/**\n" +
				"	 * Constructs a bot with the given finder.\n" +
				"	 * \n" +
				"	 * @param finder the finder.\n" +
				"	 */\n" +
				"	public " + shortClassName + "(Finder finder) {\n" +
				"		super(finder);\n" +
				"	}\n\n");
	}
}
