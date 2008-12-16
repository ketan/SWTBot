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
package org.eclipse.swtbot.generator;

import java.util.List;

import org.eclipse.swtbot.swt.finder.ReferenceBy;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class CombinationGeneratorTest {
	public static void main(String[] args) {
		ReferenceBy[] values = ReferenceBy.values();
		List<List<ReferenceBy>> combinations = ReferenceBy.getCombinations(values);
		for (List<ReferenceBy> list : combinations) {
			System.out.println(list);
		}
	}

}
