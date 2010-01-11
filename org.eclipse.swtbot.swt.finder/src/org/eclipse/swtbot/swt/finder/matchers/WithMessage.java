/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brock Janiczak - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matches {@link org.eclipse.swt.widgets.Widget}s with the specified message.
 * 
 * @author Brock Janiczak &lt;brockj [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
class WithMessage<T extends Widget> extends AbstractMatcher<T> {

	/** The text */
	protected String	message;

	/**
	 * Constructs the message text matcher with the given text.
	 * 
	 * @param text the message to match on the {@link org.eclipse.swt.widgets.Widget}
	 */
	WithMessage(String text) {
		this.message = text;
	}

	protected boolean doMatch(Object obj) {
		try {
			return getMessage(obj).equals(message);
		} catch (Exception e) {
			// do nothing
		}
		return false;
	}

	/**
	 * Gets the message of the object using the getText method. If the object doesn't contain a get message method an
	 * exception is thrown.
	 * 
	 * @param obj any object to get the message from.
	 * @return the return value of obj#getMessage()
	 * @throws NoSuchMethodException if the method "getMessage" does not exist on the object.
	 * @throws IllegalAccessException if the java access control does not allow invocation.
	 * @throws InvocationTargetException if the method "getMessage" throws an exception.
	 * @see Method#invoke(Object, Object[])
	 */
	static String getMessage(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return ((String) SWTUtils.invokeMethod(obj, "getMessage")); //$NON-NLS-1$
	}

	public void describeTo(Description description) {
		description.appendText("with message '").appendText(message).appendText("'"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Matches a widget that has the specified exact message.
	 * 
	 * @param message the message.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends Widget> Matcher<T> withMessage(String message) {
		return new WithMessage<T>(message);
	}
}
