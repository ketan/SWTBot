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
 * @version $Id$
 */
public class MatcherGeneratorMain {

	public static void main(String[] args) {
		try {
			org.hamcrest.generator.config.XmlConfigurator.main(new String[] { "swtbot-matchers.xml", "../org.eclipse.swtbot.swt.finder/src", //$NON-NLS-1$ //$NON-NLS-2$
					"org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory_Original", "../org.eclipse.swtbot.swt.finder/src" }); //$NON-NLS-1$ //$NON-NLS-2$

			org.hamcrest.generator.config.XmlConfigurator.main(new String[] { "swtbot-eclipse-matchers.xml", //$NON-NLS-1$
					"../org.eclipse.swtbot.eclipse.finder/src", "org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory_Original", //$NON-NLS-1$ //$NON-NLS-2$
					"../org.eclipse.swtbot.eclipse.finder/src" }); //$NON-NLS-1$

			makeEclipseWidgetMatcherFactoryExtendSWTBotWidgetMatcherFactory();
			makeWidgetMatcherFactoryAbstract();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void makeEclipseWidgetMatcherFactoryExtendSWTBotWidgetMatcherFactory() throws FileNotFoundException, IOException {
		String sourceFile = "../org.eclipse.swtbot.eclipse.finder/src/org/eclipse/swtbot/eclipse/finder/matchers/WidgetMatcherFactory_Original.java"; //$NON-NLS-1$
		BufferedReader reader = new BufferedReader(new FileReader(sourceFile));

		BufferedWriter writer = new BufferedWriter(new FileWriter(
						"../org.eclipse.swtbot.eclipse.finder/src/org/eclipse/swtbot/eclipse/finder/matchers/WidgetMatcherFactory.java")); //$NON-NLS-1$
		while (reader.ready()) {
			String line = reader.readLine();
			line = line.replaceAll("public class WidgetMatcherFactory_Original \\{", //$NON-NLS-1$
					"public abstract class WidgetMatcherFactory extends org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory {"); //$NON-NLS-1$
			writer.write(line);
			writer.write("\n"); //$NON-NLS-1$
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
		String sourceFile = "../org.eclipse.swtbot.swt.finder/src/org/eclipse/swtbot/swt/finder/matchers/WidgetMatcherFactory_Original.java"; //$NON-NLS-1$
		BufferedReader reader = new BufferedReader(new FileReader(sourceFile));

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"../org.eclipse.swtbot.swt.finder/src/org/eclipse/swtbot/swt/finder/matchers/WidgetMatcherFactory.java")); //$NON-NLS-1$
		while (reader.ready()) {
			String line = reader.readLine();
			line = line.replaceAll("public class WidgetMatcherFactory_Original \\{", "public abstract class WidgetMatcherFactory {"); //$NON-NLS-1$ //$NON-NLS-2$
			writer.write(line);
			writer.write("\n"); //$NON-NLS-1$
		}
		writer.close();
		reader.close();
		new File(sourceFile).delete();
	}
}
