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

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class KeyboardLayoutGenerator {

	private static Keyboard							keyboard;
	private static SWTBotText						text;
	private static FileWriter						fileWriter;
	private static CombinationGenerator<Integer>	generator;

	public static void main(String[] args) {
		System.setProperty("org.eclipse.swtbot.keyboardLayout", "empty");

		Display display = new Display();
		keyboard = Keyboard.getSWTKeyboard();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		final Text text = new Text(shell, SWT.MULTI | SWT.LEAD | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createGenerator();
		new Thread() {
			public void run() {
				SWTUtils.sleep(1000);
				try {
					typeKeystrokes(text);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} finally {
					System.exit(0);
				}
			};
		}.start();

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

	private static void typeKeystrokes(final Text text) throws IOException {
		fileWriter = new FileWriter("keyboard.layout");
		KeyboardLayoutGenerator.text = new SWTBotText(text);
		for (char c : Keys.getInputs()) {
			combination(c);
		}

		fileWriter.close();
	}

	private static void createGenerator() {
		if (isMac()) {
			generator = new CombinationGenerator<Integer>(4, SWT.ALT, SWT.SHIFT, SWT.CTRL, SWT.COMMAND);
		} else {
			generator = new CombinationGenerator<Integer>(3, SWT.ALT, SWT.SHIFT, SWT.CTRL);
		}
	}

	private static void combination(char ch) {
		Iterator<List<Integer>> iterator = generator.iterator();
		while (iterator.hasNext()) {
			Integer[] combination = iterator.next().toArray(new Integer[0]);
			int modificationKeys = or(combination);

			KeyStroke[] keys;
			try {
				keys = Keystrokes.toKeys(modificationKeys, ch);
			} catch (Exception e) {
				// could not parse ch to a keystroke.
				keys = new KeyStroke[] { KeyStroke.getInstance(0, ch) };
			}
			if (filter(keys))
				continue;
			text.setText("\n");
			text.setFocus();
			keyboard.pressShortcut(keys);
			text.setFocus();
			text.setText(text.getText().trim() + " " + KeyStroke.getInstance(modificationKeys, ch));
			try {
				fileWriter.write(text.getText());
				fileWriter.write('\n');
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private static boolean filter(KeyStroke[] keys) {
		List<KeyStroke> list = Arrays.asList(keys);

		return // quit
		list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'Q')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'q')))
				// logout
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND | SWT.ALT, 'Q')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND | SWT.ALT, 'q')))
				// minimize
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'm')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'M')))
				// screenshot
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND | SWT.SHIFT, '4')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND | SWT.CTRL | SWT.SHIFT, '4')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND | SWT.SHIFT, '3')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND | SWT.CTRL | SWT.SHIFT, '3')))
				// hide
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'h')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'H')))
				// paste
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'v')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'V')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.CTRL, 'v')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.CTRL, 'V')))
				// cut
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'x')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.COMMAND, 'X')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.CTRL, 'x')))
				|| list.equals(Arrays.asList(Keystrokes.toKeys(SWT.CTRL, 'X')))
		//
		;
	}

	private static boolean isMac() {
		String swtPlatform = SWT.getPlatform();
		return ("carbon".equals(swtPlatform) || "cocoa".equals(swtPlatform));
	}

	private static int or(Integer[] combination) {
		int or = 0;
		for (Integer integer : combination) {
			or |= integer;
		}
		return or;
	}

	private static class CombinationGenerator<T> implements Iterable<List<T>> {

		private final int			r;
		private final T[]			values;
		private ArrayList<List<T>>	result;

		/**
		 * @param r the number of elements in the combination
		 * @param values the values that should be combined.
		 */
		CombinationGenerator(int r, T... values) {
			this.r = r;
			this.values = values;
			initialize();
		}

		private void initialize() {
			result = new ArrayList<List<T>>();
			for (int i = 1; i <= r; i++) {
				FixedSizeCombinationGenerator<T> combinationGenerator = new FixedSizeCombinationGenerator<T>(i, values);
				for (List<T> list : combinationGenerator) {
					result.add(list);
				}
			}
		}

		public Iterator<List<T>> iterator() {
			return result.iterator();
		}

		class FixedSizeCombinationGenerator<E> implements Iterator<List<E>>, Iterable<List<E>> {
			private int[]		a;
			private int			n;
			private int			r;
			private BigInteger	numLeft;
			private BigInteger	total;
			private final E[]	elements;

			FixedSizeCombinationGenerator(int r, E... elements) {
				this.elements = elements;
				int n = elements.length;
				if (r > n) {
					throw new IllegalArgumentException();
				}
				if (n < 1) {
					throw new IllegalArgumentException();
				}
				this.n = n;
				this.r = r;
				a = new int[r];
				BigInteger nFact = getFactorial(n);
				BigInteger rFact = getFactorial(r);
				BigInteger nminusrFact = getFactorial(n - r);
				total = nFact.divide(rFact.multiply(nminusrFact));
				reset();
			}

			public void reset() {
				for (int i = 0; i < a.length; i++) {
					a[i] = i;
				}
				numLeft = new BigInteger(total.toString());
			}

			private BigInteger getFactorial(int n) {
				BigInteger fact = BigInteger.ONE;
				for (int i = n; i > 1; i--) {
					fact = fact.multiply(new BigInteger(Integer.toString(i)));
				}
				return fact;
			}

			private int[] getIndices() {
				if (numLeft.equals(total)) {
					numLeft = numLeft.subtract(BigInteger.ONE);
					return a;
				}

				int i = r - 1;
				while (a[i] == n - r + i) {
					i--;
				}
				a[i] = a[i] + 1;
				for (int j = i + 1; j < r; j++) {
					a[j] = a[i] + j - i;
				}
				numLeft = numLeft.subtract(BigInteger.ONE);
				return a;
			}

			public boolean hasNext() {
				return numLeft.compareTo(BigInteger.ZERO) == 1;
			}

			public List<E> next() {
				ArrayList<E> arrayList = new ArrayList<E>();
				int[] indices = getIndices();
				for (int i = 0; i < indices.length; i++) {
					arrayList.add(elements[indices[i]]);
				}
				return arrayList;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

			public Iterator<List<E>> iterator() {
				return this;
			}
		}

	}

}
