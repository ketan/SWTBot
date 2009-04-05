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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SugarGenerator {

	private final List<HamcrestWriter>	factoryWriters	= new ArrayList<HamcrestWriter>();
	private final List<String>			factoryMethods	= new ArrayList<String>();
	private final Set<String>			imports			= new TreeSet<String>();

	public void addWriter(HamcrestWriter quickReferenceWriter) {
		factoryWriters.add(quickReferenceWriter);
	}

	public void generate() {
		for (HamcrestWriter factoryWriter : factoryWriters) {
			factoryWriter.writeHeader(imports);
			factoryWriter.beginClassDefinition();
			factoryWriter.beginConstructors();
			writeMethods(factoryWriter);
			factoryWriter.writeFooter();
			factoryWriter.flush();
		}
	}

	private void writeMethods(HamcrestWriter factoryWriter) {
		for (String method : factoryMethods)
			factoryWriter.writeMethod(method);
	}

	public void close() {
		for (HamcrestWriter factoryWriter : factoryWriters)
			factoryWriter.close();
	}

	public void addFactoryMethods(SWTBotGeneratorFactoryReader doxFactoryReader) {
		factoryMethods.addAll(doxFactoryReader.getMethods());
	}

	public void addImports(SWTBotGeneratorFactoryReader doxFactoryReader) {
		imports.addAll(doxFactoryReader.getImports());
	}

}
