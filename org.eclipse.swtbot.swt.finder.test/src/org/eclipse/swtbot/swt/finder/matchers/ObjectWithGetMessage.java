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

public class ObjectWithGetMessage {
	private final String	message;

	/**
	 * @param message
	 */
	public ObjectWithGetMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
