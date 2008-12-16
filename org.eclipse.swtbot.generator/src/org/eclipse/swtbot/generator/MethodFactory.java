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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: MethodFactory.java 1193 2008-12-02 07:22:51Z kpadegaonkar $
 */
public class MethodFactory {

	@SuppressWarnings("all")
	public static ArrayList<String> methods(SWTBotWidget annotation, Class returnType, Class<? extends Widget> widgetType, String methodPrefix) {
		ArrayList<String> result = new ArrayList<String>();

		List<List<ReferenceBy>> references = getReferenceCombinations(annotation);
		for (List<ReferenceBy> list : references) {
			result.add(new MethodGenerator(returnType, widgetType, methodPrefix, annotation.style().name(), list).commentContents());
			result.add(new MethodGenerator(returnType, widgetType, methodPrefix, annotation.style().name(), list).methodContents());
			result.add(new MethodGenerator(returnType, widgetType, methodPrefix, annotation.style().name(), list).commentContentsWithIndex());
			result.add(new MethodGenerator(returnType, widgetType, methodPrefix, annotation.style().name(), list).methodContentsWithIndex());
		}
		return result;
	}

	private static List<List<ReferenceBy>> getReferenceCombinations(SWTBotWidget annotation) {
		List<ReferenceBy> value = new ArrayList(Arrays.asList(annotation.referenceBy()));
		value.addAll(Arrays.asList(annotation.defaultReferenceBy()));
		ReferenceBy[] array = value.toArray(new ReferenceBy[] {});
		return ReferenceBy.getCombinations(array);
	}

	public static Collection<? extends String> imports(SWTBotWidget annotation, Class returnType, Class<? extends Widget> widgetType, String methodPrefix) {
		ArrayList<String> result = new ArrayList<String>();
		List<List<ReferenceBy>> references = getReferenceCombinations(annotation);
		for (List<ReferenceBy> list : references) {
			result.addAll(new MethodGenerator(returnType, widgetType, methodPrefix, annotation.style().name(), list).imports());
		}
		return result;
	}
}
