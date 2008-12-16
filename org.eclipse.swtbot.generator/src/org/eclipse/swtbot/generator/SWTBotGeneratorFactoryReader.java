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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swtbot.swt.finder.SWTBotWidget;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotGeneratorFactoryReader {

	private SWTBotWidget	annotation;
	private Constructor		constructor;
	private Class<?>		clasz;

	public SWTBotGeneratorFactoryReader(String className) throws ClassNotFoundException {
		clasz = Class.forName(className);
		annotation = getSWTBotAnnotation();
		constructor = getConstructor();
	}

	public ArrayList<String> getMethods() {
		if (annotation == null)
			return new ArrayList<String>();

		return MethodFactory.methods(annotation, constructor.getDeclaringClass(), annotation.clasz(), annotation.preferredName());
	}

	public Collection<? extends String> getImports() {
		if (annotation == null)
			return new ArrayList<String>();
		return MethodFactory.imports(annotation, constructor.getDeclaringClass(), annotation.clasz(), annotation.preferredName());
	}

	private Constructor getConstructor() {
		return clasz.getConstructors()[0];
	}

	private SWTBotWidget getSWTBotAnnotation() {
		return clasz.getAnnotation(SWTBotWidget.class);
	}


}
