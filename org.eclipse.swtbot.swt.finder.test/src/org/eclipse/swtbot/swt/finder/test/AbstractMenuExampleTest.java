/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.test;

import org.eclipse.swt.examples.addressbook.AddressBook;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtbot.swt.finder.finders.MenuFinder;
import org.junit.Before;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public abstract class AbstractMenuExampleTest extends AbstractSWTShellTest {

	protected MenuFinder menuFinder;
	protected AddressBook addressBook;

	@Before
	public void setUp() throws Exception {
		menuFinder = new MenuFinder();
	}

	@Override
	protected void createUI(Composite parent) {
		shell.setText("Address Book - Untitled");
		addressBook = new AddressBook();
		addressBook.open(shell);
	}

}