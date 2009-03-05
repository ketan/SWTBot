/*******************************************************************************
 * Copyright (c) 2008-2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.text.MessageFormat;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class main {
	public static void main(String[] args) {
		String format = MessageFormat.format("The index ({0}) was more than the number of nodes ({1}) in the tree.", 2, 3);
		System.out.println(format);
	}
}
