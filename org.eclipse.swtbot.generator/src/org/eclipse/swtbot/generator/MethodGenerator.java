/*******************************************************************************
 * Copyright (c) 2008, 2010 Ketan Padegaonkar and others.
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

import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.utils.ClassUtils;

/**
 * Generate a method given the way a widget can be referenced.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class MethodGenerator {

	private final ReferenceBy[]	list;
	private final Class<?>		returnType;
	private final String		methodNamePrefix;
	private final Class<?>		widgetType;
	private final String		style;
	private final Class<?>	creationType;

	public MethodGenerator(Class<?> returnType, Class<?> creationType, Class<?> widgetType, String methodNamePrefix, String style, List<ReferenceBy> list) {
		this.returnType = returnType;
		this.creationType = creationType;
		this.widgetType = widgetType;
		this.methodNamePrefix = methodNamePrefix;
		this.style = style;
		this.list = list.toArray(new ReferenceBy[] {});
	}

	public MethodGenerator(Class<?> returnType, Class<?> creationType, Class<?> widgetType, String methodNamePrefix, String style, ReferenceBy... list) {
		this.returnType = returnType;
		this.creationType = creationType;
		this.widgetType = widgetType;
		this.methodNamePrefix = methodNamePrefix;
		this.style = style;
		this.list = list;
	}

	public String methodArguments() {
		StringBuffer result = new StringBuffer();
		String delimiter = ", "; //$NON-NLS-1$

		for (ReferenceBy ref : list) {
			result.append(ref.methodArgument()).append(delimiter);
		}
		result.lastIndexOf(delimiter);
		result.replace(result.length() - delimiter.length(), result.length(), ""); //$NON-NLS-1$

		return result.toString();
	}

	public String methodContentsWithIndex() {
		String result = "	@SuppressWarnings({\"unchecked\", \"rawtypes\"})\n"; //$NON-NLS-1$
		result += "	public " + ClassUtils.simpleClassName(returnType) + " " + methodName() + methodArgsWithIndex() + " {\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		result += "		Matcher matcher = allOf(" + instanceOf() + (otherMatchers().length() > 0 ? ", " : "") + otherMatchers() + ");\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		result += "		return new " + ClassUtils.simpleClassName(creationType) + "((" + ClassUtils.simpleClassName(widgetType) //$NON-NLS-1$ //$NON-NLS-2$
				+ ") widget(matcher, index), matcher);\n"; //$NON-NLS-1$
		result += "	}\n"; //$NON-NLS-1$
		return result;
	}

	public String methodContents() {
		String result = ""; //$NON-NLS-1$
		result += "	public " + ClassUtils.simpleClassName(returnType) + " " + methodName() + methodArgsWithoutIndex() + " {\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		result += "		return " + methodName() + "(" + (methodInvocationArgs().equals(", ") ? "" : methodInvocationArgs()) + "0);\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		result += "	}\n"; //$NON-NLS-1$
		return result;
	}

	public String commentContents() {
		String string = ""; //$NON-NLS-1$
		string += params();
		string += returnStatement();
		string += ".\n"; //$NON-NLS-1$
		return comment(string);
	}

	public String commentContentsWithIndex() {
		String string = ""; //$NON-NLS-1$
		string += params();
		string += "@param index the index of the widget.\n"; //$NON-NLS-1$
		string += returnStatement();
		string += ".\n"; //$NON-NLS-1$
		return comment(string);
	}

	private String returnStatement() {
		String string = "@return a {@link " + ClassUtils.simpleClassName(returnType) + "}"; //$NON-NLS-1$ //$NON-NLS-2$
		for (ReferenceBy ref : list) {
			string += " " + ref.describeJavaDoc(); //$NON-NLS-1$
		}
		return string;
	}

	private String params() {
		String string = ""; //$NON-NLS-1$
		for (ReferenceBy ref : list) {
			string += ref.paramJavaDoc();
		}
		return string;
	}

	private String comment(String string) {
		String[] lines = string.split("\n"); //$NON-NLS-1$
		StringBuffer buf = new StringBuffer();
		buf.append("	/**\n"); //$NON-NLS-1$
		for (String line : lines) {
			buf.append("	 * ").append(line).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buf.append("	 */"); //$NON-NLS-1$
		return buf.toString();
	}

	private String methodInvocationArgs() {
		String invocation = ""; //$NON-NLS-1$
		String[] methodArgs = methodArguments().split(","); //$NON-NLS-1$
		for (String methodArg : methodArgs) {
			String[] arg = methodArg.split("\\s"); //$NON-NLS-1$
			invocation += arg[arg.length - 1] + ", "; //$NON-NLS-1$
		}
		return invocation;
	}

	private String otherMatchers() {
		StringBuffer result = new StringBuffer();
		String delimiter = ", "; //$NON-NLS-1$

		for (ReferenceBy ref : list) {
			String matcherMethod = ref.matcherMethod();
			if (matcherMethod.trim().length() > 0)
				result.append(matcherMethod).append(delimiter);
		}

		if (hasStyle())
			result.append("withStyle(" + style + ", \"" + style + "\")").append(delimiter); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (result.lastIndexOf(delimiter) >= 0)
			result.replace(result.length() - delimiter.length(), result.length(), ""); //$NON-NLS-1$

		return result.toString();
	}

	private boolean hasStyle() {
		return !"SWT.NONE".equals(style); //$NON-NLS-1$
	}

	private String instanceOf() {
		return "widgetOfType(" + ClassUtils.simpleClassName(widgetType) + ".class)"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private String methodArgsWithIndex() {
		return "(" + methodArguments() + (methodArguments().length() > 0 ? ", " : "") + "int index)"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	private String methodArgsWithoutIndex() {
		return "(" + methodArguments() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private String methodName() {
		StringBuffer methodName = new StringBuffer(methodNamePrefix);
		for (ReferenceBy ref : list) {
			methodName.append(ref.methodNameSuffix());
		}
		return methodName.toString();
	}

	public List<String> imports() {
		ArrayList<String> imports = new ArrayList<String>();
		imports.add("import " + returnType.getName()); //$NON-NLS-1$
		imports.add("import " + widgetType.getName()); //$NON-NLS-1$
		return imports;
	}

}
