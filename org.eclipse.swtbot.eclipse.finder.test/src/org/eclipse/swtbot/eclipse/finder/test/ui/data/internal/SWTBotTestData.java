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
package org.eclipse.swtbot.eclipse.finder.test.ui.data.internal;

import org.eclipse.core.runtime.Assert;

/**
 * A simple data item used in testing.
 * 
 * @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id: SWTBotTestData.java 1190 2008-12-02 07:06:28Z kpadegaonkar $
 */
public class SWTBotTestData {
	/**
	 * The name value.
	 */
	String	name	= "";

	/**
	 * Constructs the data item with the given name.
	 * 
	 * @param name the name to set. May not be <code>null</code>.
	 */
	public SWTBotTestData(String name) {
		setName(name);
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name or an empty string if it has not been set.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for the string. If the value is null and error will be thrown.
	 * 
	 * @param name the name to set. May not be <code>null</code>.
	 */
	public void setName(String name) {
		Assert.isNotNull(name);

		this.name = name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof SWTBotTestData) {
			return this.getName().equals(((SWTBotTestData) obj).getName());
		}
		
		return super.equals(obj);
	}

	public String toString() {
		return getName();
	}
}
