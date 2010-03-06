/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils.internal;

import org.eclipse.swtbot.swt.finder.exceptions.AssertionFailedException;

/**
 * Copy of {@link org.eclipse.core.runtime.Assert}, moved here to reduce dependency on org.eclipse.equinox.common.
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public final class Assert {
	/* This class is not intended to be instantiated. */
	private Assert() {
		// not allowed
	}

	/**
	 * Asserts that an argument is legal. If the given boolean is not <code>true</code>, an
	 * <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param expression the outcode of the check
	 * @return <code>true</code> if the check passes (does not return if the check fails)
	 * @exception IllegalArgumentException if the legality test failed
	 */
	public static boolean isLegal(boolean expression) {
		return isLegal(expression, ""); //$NON-NLS-1$
	}

	/**
	 * Asserts that an argument is legal. If the given boolean is not <code>true</code>, an
	 * <code>IllegalArgumentException</code> is thrown. The given message is included in that exception, to aid
	 * debugging.
	 * 
	 * @param expression the outcode of the check
	 * @param message the message to include in the exception
	 * @return <code>true</code> if the check passes (does not return if the check fails)
	 * @exception IllegalArgumentException if the legality test failed
	 */
	public static boolean isLegal(boolean expression, Object message) {
		if (!expression)
			throw new IllegalArgumentException(message.toString());
		return expression;
	}

	/**
	 * Asserts that the given object is not <code>null</code>. If this is not the case, some kind of unchecked exception
	 * is thrown.
	 * 
	 * @param object the value to test
	 */
	public static void isNotNull(Object object) {
		isNotNull(object, ""); //$NON-NLS-1$
	}

	/**
	 * Asserts that the given object is not <code>null</code>. If this is not the case, some kind of unchecked exception
	 * is thrown. The given message is included in that exception, to aid debugging.
	 * 
	 * @param object the value to test
	 * @param message the message to include in the exception
	 */
	public static void isNotNull(Object object, Object message) {
		if (object == null)
			throw new AssertionFailedException("null argument: " + message); //$NON-NLS-1$
	}

	/**
	 * Asserts that the given boolean is <code>true</code>. If this is not the case, some kind of unchecked exception is
	 * thrown.
	 * 
	 * @param expression the outcode of the check
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, ""); //$NON-NLS-1$
	}

	/**
	 * Asserts that the given boolean is <code>true</code>. If this is not the case, some kind of unchecked exception is
	 * thrown. The given message is included in that exception, to aid debugging.
	 * 
	 * @param expression the outcode of the check
	 * @param message the message to include in the exception
	 */
	public static void isTrue(boolean expression, Object message) {
		if (!expression)
			throw new AssertionFailedException("assertion failed: " + message); //$NON-NLS-1$
	}

	/**
	 * Asserts that the given list is not empty or null. If this is not the case, some kind of unchecked exception is
	 * thrown. The given message is included in that exception, to aid debugging.
	 * 
	 * @param expression the outcode of the check
	 * @param message the message to include in the exception
	 */
	public static void isNotEmpty(Object... toCheck) {
		Assert.isNotNull(toCheck);
		if (toCheck.length == 0) {
			throw new AssertionFailedException("List cannot be empty or null.");
		}
	}
}
