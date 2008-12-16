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
package org.eclipse.swtbot.swt.finder.matchers;

public class ObjectWithGetText {
	private final String	text;

	/**
	 * @param text
	 */
	public ObjectWithGetText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}