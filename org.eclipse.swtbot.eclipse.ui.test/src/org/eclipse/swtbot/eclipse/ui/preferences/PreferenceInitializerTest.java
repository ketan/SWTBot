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
package org.eclipse.swtbot.eclipse.ui.preferences;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.PreferenceStore;
import org.junit.Before;
import org.junit.Test;

/**
 * Initializes the default preferences if none exist.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PreferenceInitializerTest {
	/**  */
	private PreferenceInitializer	initializer;
	private PreferenceStore			swtbotPreferenceStore;
	private PreferenceStore			jdtPreferenceStore;
	private static final String		JDT_IMPORT_PROPERTY	= "content_assist_favorite_static_members";
	private static final String		DEFAULT_IMPORTS		= "org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.*;"
																+ "org.hamcrest.Matchers.*;"
																+ "org.hamcrest.MatcherAssert.*;"
																+ "org.junit.Assert.*;"
																+ "org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.*;"
																+ "org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.*;"
																+ "org.eclipse.swtbot.swt.finder.SWTBotAssert.*;"
																+ "org.eclipse.swtbot.eclipse.finder.waits.Conditions.*;"
																+ "org.eclipse.swtbot.swt.finder.waits.Conditions.*";

	@Test
	public void canPullJDTImports() throws Exception {
		jdtPreferenceStore.setValue(JDT_IMPORT_PROPERTY, "foo.*;bar.*");
		List<String> imports = initializer.getJDTImports();
		assertEquals(Arrays.asList("foo.*", "bar.*"), imports);
	}

	@Test
	public void addsFavoritesIfSWTBotPreferenceIsTrue() throws Exception {
		assertEquals("", jdtPreferenceStore.getString(JDT_IMPORT_PROPERTY));
		swtbotPreferenceStore.setValue(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES, true);
		assertEquals(DEFAULT_IMPORTS, jdtPreferenceStore.getString(JDT_IMPORT_PROPERTY));
	}

	@Test
	public void removesFavoritesIfSWTBotPreferenceIsFalse() throws Exception {
		assertEquals("", jdtPreferenceStore.getString(JDT_IMPORT_PROPERTY));
		swtbotPreferenceStore.setValue(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES, true);
		assertEquals(DEFAULT_IMPORTS, jdtPreferenceStore.getString(JDT_IMPORT_PROPERTY));

		swtbotPreferenceStore.setValue(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES, false);
		assertEquals("", jdtPreferenceStore.getString(JDT_IMPORT_PROPERTY));
	}

	@Before
	public void setUp() throws Exception {
		swtbotPreferenceStore = new PreferenceStore();
		jdtPreferenceStore = new PreferenceStore();
		initializer = new PreferenceInitializer(swtbotPreferenceStore, jdtPreferenceStore);
	}

}
