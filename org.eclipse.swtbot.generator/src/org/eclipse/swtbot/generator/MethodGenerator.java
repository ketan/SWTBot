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

	public MethodGenerator(Class<?> returnType, Class<?> widgetType, String methodNamePrefix, String style, List<ReferenceBy> list) {
		this.returnType = returnType;
		this.widgetType = widgetType;
		this.methodNamePrefix = methodNamePrefix;
		this.style = style;
		this.list = list.toArray(new ReferenceBy[] {});
	}

	public MethodGenerator(Class<?> returnType, Class<?> widgetType, String methodNamePrefix, String style, ReferenceBy... list) {
		this.returnType = returnType;
		this.widgetType = widgetType;
		this.methodNamePrefix = methodNamePrefix;
		this.style = style;
		this.list = list;
	}

	public String methodArguments() {
		StringBuffer result = new StringBuffer();
		String delimiter = ", ";

		for (ReferenceBy ref : list) {
			result.append(ref.methodArgument()).append(delimiter);
		}
		result.lastIndexOf(delimiter);
		result.replace(result.length() - delimiter.length(), result.length(), "");

		return result.toString();
	}

	public String methodContentsWithIndex() {
		String result = "	@SuppressWarnings(\"unchecked\")\n";
		result += "	public " + ClassUtils.simpleClassName(returnType) + " " + methodName() + methodArgsWithIndex() + " {\n";
		result += "		Matcher matcher = allOf(" + instanceOf() + (otherMatchers().length() > 0 ? ", " : "") + otherMatchers() + ");\n";
		result += "		return new " + ClassUtils.simpleClassName(returnType) + "((" + ClassUtils.simpleClassName(widgetType)
				+ ") widget(matcher, index), matcher);\n";
		result += "	}\n";
		return result;
	}

	public String methodContents() {
		String result = "";
		result += "	public " + ClassUtils.simpleClassName(returnType) + " " + methodName() + methodArgsWithoutIndex() + " {\n";
		result += "		return " + methodName() + "(" + (methodInvocationArgs().equals(", ") ? "" : methodInvocationArgs()) + "0);\n";
		result += "	}\n";
		return result;
	}

	public String commentContents() {
		String string = "";
		string += params();
		string += returnStatement();
		string += ".\n";
		return comment(string);
	}

	public String commentContentsWithIndex() {
		String string = "";
		string += params();
		string += "@param index the index of the widget.\n";
		string += returnStatement();
		string += ".\n";
		return comment(string);
	}

	private String returnStatement() {
		String string = "@return a {@link " + ClassUtils.simpleClassName(returnType) + "}";
		for (ReferenceBy ref : list) {
			string += " " + ref.describeJavaDoc();
		}
		return string;
	}

	private String params() {
		String string = "";
		for (ReferenceBy ref : list) {
			string += ref.paramJavaDoc();
		}
		return string;
	}

	private String comment(String string) {
		String[] lines = string.split("\n");
		StringBuffer buf = new StringBuffer();
		buf.append("	/**\n");
		for (String line : lines) {
			buf.append("	 * ").append(line).append("\n");
		}
		buf.append("	 */");
		return buf.toString();
	}

	private String methodInvocationArgs() {
		String invocation = "";
		String[] methodArgs = methodArguments().split(",");
		for (String methodArg : methodArgs) {
			String[] arg = methodArg.split("\\s");
			invocation += arg[arg.length - 1] + ", ";
		}
		return invocation;
	}

	private String otherMatchers() {
		StringBuffer result = new StringBuffer();
		String delimiter = ", ";

		for (ReferenceBy ref : list) {
			String matcherMethod = ref.matcherMethod();
			if (matcherMethod.trim().length() > 0)
				result.append(matcherMethod).append(delimiter);
		}

		if (hasStyle())
			result.append("withStyle(" + style + ", \"" + style + "\")").append(delimiter);

		if (result.lastIndexOf(delimiter) >= 0)
			result.replace(result.length() - delimiter.length(), result.length(), "");

		return result.toString();
	}

	private boolean hasStyle() {
		return !"SWT.NONE".equals(style);
	}

	private String instanceOf() {
		return "widgetOfType(" + ClassUtils.simpleClassName(widgetType) + ".class)";
	}

	private String methodArgsWithIndex() {
		return "(" + methodArguments() + (methodArguments().length() > 0 ? ", " : "") + "int index)";
	}

	private String methodArgsWithoutIndex() {
		return "(" + methodArguments() + ")";
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
		imports.add("import " + returnType.getName());
		imports.add("import " + widgetType.getName());
		return imports;
	}

}
