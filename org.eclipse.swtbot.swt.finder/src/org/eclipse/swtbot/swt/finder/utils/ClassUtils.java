/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=88
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

/**
 * A utility for class based work.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class ClassUtils {

	/**
	 * Gets the simple class name of an object or an empty string if not valid.
	 *
	 * @param object the object
	 * @return the classname of the object or an empty string.
	 */
	public static String simpleClassName(Object object) {
		return object == null ? "" : ClassUtils.simpleClassName(object.getClass()); //$NON-NLS-1$
	}

	/**
	 * Gets the simple class name for the given class.
	 *
	 * @param clasz the class
	 * @return the classname of the clasz
	 */
	public static String simpleClassName(Class<?> clasz) {
		if (clasz == null)
			return ""; //$NON-NLS-1$
		if (clasz.isMemberClass())
			return clasz.getDeclaringClass().getSimpleName() + "$" + clasz.getSimpleName(); //$NON-NLS-1$
		return clasz.getSimpleName();
	}

	/**
	 * Gets the simple class name for the given class.
	 *
	 * @param claszName the class
	 * @return the classname of the clasz
	 * @since 2.0
	 */
	public static String simpleClassName(String claszName) {
		if (StringUtils.isEmptyOrNull(claszName))
			return ""; //$NON-NLS-1$
		return claszName.substring(claszName.lastIndexOf(".") + 1); //$NON-NLS-1$
	}
}
