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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: MatcherGeneratorMain.java 1194 2008-12-02 07:36:32Z kpadegaonkar $
 */
public class MatcherGeneratorMain {

	public static void main(String[] args) {
		try {
			org.hamcrest.generator.config.XmlConfigurator.main(new String[] { "swtbot-matchers.xml", "../org.eclipse.swtbot.swt.finder/src",
					"org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory_Original", "../org.eclipse.swtbot.swt.finder/src" });

			org.hamcrest.generator.config.XmlConfigurator.main(new String[] { "swtbot-eclipse-matchers.xml",
					"../org.eclipse.swtbot.eclipse.finder/src", "org.eclipse.swtbot.eclipse.finder.matcher.WidgetMatcherFactory_Original",
					"../org.eclipse.swtbot.eclipse.finder/src" });

			makeEclipseWidgetMatcherFactoryExtendSWTBotWidgetMatcherFactory();
			makeWidgetMatcherFactoryAbstract();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void makeEclipseWidgetMatcherFactoryExtendSWTBotWidgetMatcherFactory() throws FileNotFoundException, IOException {
		String sourceFile = "../org.eclipse.swtbot.eclipse.finder/src/org/eclipse/swtbot/eclipse/finder/matcher/WidgetMatcherFactory_Original.java";
		BufferedReader reader = new BufferedReader(new FileReader(sourceFile));

		BufferedWriter writer = new BufferedWriter(new FileWriter(
						"../org.eclipse.swtbot.eclipse.finder/src/org/eclipse/swtbot/eclipse/finder/matcher/WidgetMatcherFactory.java"));
		while (reader.ready()) {
			String line = reader.readLine();
			line = line.replaceAll("public class WidgetMatcherFactory_Original \\{",
					"public abstract class WidgetMatcherFactory extends org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory {");
			writer.write(line);
			writer.write("\n");
		}
		writer.close();
		reader.close();
		new File(sourceFile).delete();
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void makeWidgetMatcherFactoryAbstract() throws FileNotFoundException, IOException {
		String sourceFile = "../org.eclipse.swtbot.swt.finder/src/org/eclipse/swtbot/swt/finder/matcher/WidgetMatcherFactory_Original.java";
		BufferedReader reader = new BufferedReader(new FileReader(sourceFile));

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"../org.eclipse.swtbot.swt.finder/src/org/eclipse/swtbot/swt/finder/matcher/WidgetMatcherFactory.java"));
		while (reader.ready()) {
			String line = reader.readLine();
			line = line.replaceAll("public class WidgetMatcherFactory_Original \\{", "public abstract class WidgetMatcherFactory {");
			writer.write(line);
			writer.write("\n");
		}
		writer.close();
		reader.close();
		new File(sourceFile).delete();
	}
}
