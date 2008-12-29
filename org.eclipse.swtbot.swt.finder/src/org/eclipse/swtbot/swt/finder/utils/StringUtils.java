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
package org.eclipse.swtbot.swt.finder.utils;

import java.util.Collection;

import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

/**
 * A set of utilities for string manipulation.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.0
 */
public class StringUtils {

	/**
	 * Joins an array of objects using the given delimiter as spacing.
	 * 
	 * @param toJoin the objects to join into a string.
	 * @param delimiter the delimiter used to join the objects.
	 * @return the result of joining the <code>toJoin</code> with <code>delimiter</code>.
	 */
	public static String join(Object[] toJoin, String delimiter) {
		if ((toJoin == null) || (toJoin.length == 0))
			return "";
		Assert.isTrue(!isEmptyOrNull(delimiter));
		StringBuffer result = new StringBuffer();

		for (Object object : toJoin) {
			result.append(object);
			result.append(delimiter);
		}

		result.lastIndexOf(delimiter);
		result.replace(result.length() - delimiter.length(), result.length(), "");
		return result.toString();
	}

	/**
	 * Joins a collection of objects using the given delimiter as spacing.
	 * 
	 * @param toJoin the objects to join into a string.
	 * @param delimiter the delimiter used to join the objects.
	 * @return the result of joining the <code>toJoin</code> with <code>delimiter</code>.
	 */
	public static String join(Collection<?> toJoin, String delimiter) {
		return join(toJoin.toArray(), delimiter);
	}

	/**
	 * Checks if the given string is <code>null</code> or empty.
	 * 
	 * @param string the string.
	 * @return <code>true</code> if string is null, blank or whitespaces. <code>false</code> otherwise.
	 */
	public static boolean isEmptyOrNull(String string) {
		return isNull(string) || isEmpty(string);
	}

	/**
	 * Joins the given integer array with the given delimiter.
	 * 
	 * @param toJoin the integers to join into a string.
	 * @param delimiter the delimiter.
	 * @return the result of joining the <code>toJoin</code> with <code>delimiter</code>.
	 */
	public static String join(int[] toJoin, String delimiter) {
		Integer[] ints = new Integer[toJoin.length];
		for (int i = 0; i < toJoin.length; i++)
			ints[i] = new Integer(toJoin[i]);
		return join(ints, delimiter);
	}

	/**
	 * @param text the text.
	 * @return <code>true</code> if the text is empty, <code>false</code> otherwise.
	 * @since 1.3
	 */
	public static boolean isEmpty(String text) {
		return text.trim().equals("");
	}

	/**
	 * @param text the text.
	 * @return <code>true</code> if the text is null, <code>false</code> otherwise.
	 * @since 1.3
	 */
	public static boolean isNull(String text) {
		return text == null;
	}

	/**
	 * Converts the string to camelcase. Strings are of the format: THIS_IS_A_STRING, and the result of camel casing
	 * would be thisIsAString.
	 * 
	 * @param string the string to be camelcased.
	 * @return the camel cased string.
	 * @since 2.0
	 */
	public static String toCamelCase(String string) {
		StringBuffer result = new StringBuffer(string);
		while (result.indexOf("_") != -1) {
			int indexOf = result.indexOf("_");
			result.replace(indexOf, indexOf + 2, "" + Character.toUpperCase(result.charAt(indexOf + 1)));
		}
		return result.toString();
	}

	/**
	 * Converts the string to capitalized. Strings are of the format: THIS_IS_A_STRING, and the result of capitalization
	 * would be ThisIsAString.
	 * 
	 * @param string the string to capitalize.
	 * @return the capitalized string.
	 * @since 2.0
	 */
	public static String capitalize(String string) {
		StringBuffer result = new StringBuffer(toCamelCase(string));
		result.replace(0, 1, "" + Character.toUpperCase(result.charAt(0)));
		return result.toString();
	}

}
