/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.keyboard;

import java.awt.im.InputContext;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.eclipse.swtbot.swt.finder.utils.FileUtils;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeyboardLayoutFilter {
	public static void main(String[] args) throws IOException {
		String contents = FileUtils.read("keyboard.layout");
		String[] split = contents.split("\n");

		Map<Character, String> map = new TreeMap<Character, String>();
		for (String string : split) {
			if (string.length() <= 0)
				continue;
			char ch = string.charAt(0);
			if (shouldFilter(ch)) {
				continue;
			}
			if (map.containsKey(ch)) {
				String keyStrokes = map.get(ch);
				if (string.length() < keyStrokes.length()) {
					map.put(ch, string.substring(2));
				}

			} else {
				map.put(ch, string.substring(2));
			}
		}

		FileWriter writer = new FileWriter("keyboard.layout.filtered");

		Map<String, Character> reverseMap = new TreeMap<String, Character>();

		for (Entry<Character, String> entry : map.entrySet()) {
			reverseMap.put(entry.getValue(), entry.getKey());
		}
		writer.write("Country:" + InputContext.getInstance().getLocale().getCountry() + "\n");
		writer.write("Variant:" + InputContext.getInstance().getLocale().getVariant() + "\n");
		writer.write("Language:" + InputContext.getInstance().getLocale().getLanguage() + "\n");
		for (Entry<String, Character> entry : reverseMap.entrySet()) {
			writer.write(entry.getValue());
			writer.write(" ");
			writer.write(entry.getKey());
			writer.write("\n");
		}
		writer.close();
		System.exit(0);
	}

	private static boolean shouldFilter(char ch) {
		return Keys.getInputs().contains(Character.toUpperCase(ch)) || Keys.getInputs().contains(Character.toLowerCase(ch));
	}
}
