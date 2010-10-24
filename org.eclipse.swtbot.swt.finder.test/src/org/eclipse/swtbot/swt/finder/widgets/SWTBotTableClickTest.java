/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.snippets.viewers.Snippet009CellEditors;
import org.eclipse.swtbot.swt.finder.test.BaseSWTTest;
import org.junit.Test;

/**
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 * @since 1.2
 */
public class SWTBotTableClickTest extends BaseSWTTest {

	@Test
	public void clickOnCell() throws Exception {
		SWTBotTable table = bot.table();
		table.click(0, 0);
		bot.sleep(1000);
		bot.text("0", 0).setText("101");
		bot.sleep(1000);
		table.click(1, 0);
		bot.sleep(1000);
		assertEquals("Item 101", table.cell(0, 0));
	}
	
	@Override
	public void runUIThread() {
		Snippet009CellEditors.main(new String[0]);
	}

}
