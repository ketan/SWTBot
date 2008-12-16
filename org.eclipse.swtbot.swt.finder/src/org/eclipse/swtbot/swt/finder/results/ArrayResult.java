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
package org.eclipse.swtbot.swt.finder.results;

/**
 * A runnable that returns an array of types T.
 *
 * @since 2.0
 */
public interface ArrayResult<T> {
	/**
	 * @return a runnable that returns an object of type T.
	 */
	public T[] run();
}