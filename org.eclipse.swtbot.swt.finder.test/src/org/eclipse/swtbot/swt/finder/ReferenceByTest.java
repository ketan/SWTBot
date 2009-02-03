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
package org.eclipse.swtbot.swt.finder;

import static org.eclipse.swtbot.swt.finder.ReferenceBy.ID_KEY_VALUE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.ID_VALUE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.IN_GROUP;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.LABEL;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.MNEMONIC;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.NONE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.TEXT;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.TOOLTIP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ReferenceByTest {

	@Test
	public void methodArguments() throws Exception {
		assertThat(IN_GROUP.methodArgument(), equalTo("String inGroup"));
		assertThat(LABEL.methodArgument(), equalTo("String label"));
		assertThat(NONE.methodArgument(), equalTo(""));
		assertThat(TEXT.methodArgument(), equalTo("String text"));
		assertThat(TOOLTIP.methodArgument(), equalTo("String tooltip"));
		assertThat(ID_KEY_VALUE.methodArgument(), equalTo("String key, String value"));
	}

	@Test
	public void methodNameSuffix() throws Exception {
		assertThat(IN_GROUP.methodNameSuffix(), equalTo("InGroup"));
		assertThat(LABEL.methodNameSuffix(), equalTo("WithLabel"));
		assertThat(NONE.methodNameSuffix(), equalTo(""));
		assertThat(TEXT.methodNameSuffix(), equalTo(""));
		assertThat(MNEMONIC.methodNameSuffix(), equalTo(""));
		assertThat(TOOLTIP.methodNameSuffix(), equalTo("WithTooltip"));
		assertThat(ID_KEY_VALUE.methodNameSuffix(), equalTo("WithId"));
	}

	@Test
	public void matcherMethod() throws Exception {
		assertThat(IN_GROUP.matcherMethod(), equalTo("inGroup(inGroup)"));
		assertThat(LABEL.matcherMethod(), equalTo("withLabel(label)"));
		assertThat(NONE.matcherMethod(), equalTo(""));
		assertThat(TEXT.matcherMethod(), equalTo("withText(text)"));
		assertThat(TOOLTIP.matcherMethod(), equalTo("withTooltip(tooltip)"));
		assertThat(MNEMONIC.matcherMethod(), equalTo("withMnemonic(mnemonicText)"));
		assertThat(ID_KEY_VALUE.matcherMethod(), equalTo("withId(key, value)"));
	}

	@Test
	public void compatibilityWithOtherReferences() throws Exception {
		assertFalse(TEXT.isCompatibleWith(LABEL));
		assertFalse(TEXT.isCompatibleWith(TOOLTIP));
		assertFalse(TEXT.isCompatibleWith(MNEMONIC));

		assertTrue(TEXT.isCompatibleWith(ID_KEY_VALUE));
		assertTrue(TEXT.isCompatibleWith(IN_GROUP));
		assertTrue(TEXT.isCompatibleWith(NONE));
		assertTrue(TEXT.isCompatibleWith(TEXT));

		assertFalse(LABEL.isCompatibleWith(TEXT));
		assertFalse(LABEL.isCompatibleWith(TOOLTIP));
		assertFalse(LABEL.isCompatibleWith(MNEMONIC));

		assertTrue(LABEL.isCompatibleWith(ID_KEY_VALUE));
		assertTrue(LABEL.isCompatibleWith(IN_GROUP));
		assertTrue(LABEL.isCompatibleWith(NONE));
		assertTrue(LABEL.isCompatibleWith(LABEL));

		assertFalse(MNEMONIC.isCompatibleWith(TEXT));
		assertFalse(MNEMONIC.isCompatibleWith(TOOLTIP));
		assertFalse(MNEMONIC.isCompatibleWith(LABEL));

		assertTrue(MNEMONIC.isCompatibleWith(ID_KEY_VALUE));
		assertTrue(MNEMONIC.isCompatibleWith(IN_GROUP));
		assertTrue(MNEMONIC.isCompatibleWith(NONE));
		assertTrue(MNEMONIC.isCompatibleWith(MNEMONIC));

		assertTrue(IN_GROUP.isCompatibleWith(null));
		assertFalse(ID_KEY_VALUE.isCompatibleWith(null));
		assertFalse(NONE.isCompatibleWith(null));
	}

	@Test
	public void generatesCombinations() throws Exception {
		List<List<ReferenceBy>> combinations = ReferenceBy.getCombinations(ReferenceBy.values());
		assertEquals(12, combinations.size());
		assertTrue(combinations.contains(Arrays.asList(TEXT)));
		assertTrue(combinations.contains(Arrays.asList(LABEL)));
		assertTrue(combinations.contains(Arrays.asList(TOOLTIP)));
		assertTrue(combinations.contains(Arrays.asList(IN_GROUP)));
		assertTrue(combinations.contains(Arrays.asList(MNEMONIC)));
		assertTrue(combinations.contains(Arrays.asList(NONE)));
		assertTrue(combinations.contains(Arrays.asList(ID_KEY_VALUE)));
		assertTrue(combinations.contains(Arrays.asList(ID_VALUE)));

		assertTrue(combinations.contains(Arrays.asList(TEXT, IN_GROUP)));
		assertTrue(combinations.contains(Arrays.asList(MNEMONIC, IN_GROUP)));
		assertTrue(combinations.contains(Arrays.asList(TOOLTIP, IN_GROUP)));
		assertTrue(combinations.contains(Arrays.asList(LABEL, IN_GROUP)));
	}

}
