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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swtbot.eclipse.ui.Activator;
import org.eclipse.swtbot.swt.finder.collections.OrderedSet;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;
import org.eclipse.ui.IStartup;

/**
 * Initializes the default preferences if none exist.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer implements IStartup, IPropertyChangeListener {

	private final IPreferenceStore	swtbotPreferenceStore;
	private final IPreferenceStore	jdtPreferenceStore;

	/**
	 * Creates a default preference initializer.
	 */
	public PreferenceInitializer() {
		this(Activator.getDefault().getPreferenceStore(), PreferenceConstants.getPreferenceStore());
	}

	/**
	 * Create a preference initializer with the two preference stores.
	 * 
	 * @param swtbotPreferenceStore used by swtbot.
	 * @param jdtPreferenceStore used by JDT.
	 */
	public PreferenceInitializer(IPreferenceStore swtbotPreferenceStore, IPreferenceStore jdtPreferenceStore) {
		this.swtbotPreferenceStore = swtbotPreferenceStore;
		this.jdtPreferenceStore = jdtPreferenceStore;
		swtbotPreferenceStore.addPropertyChangeListener(this);
	}

	static final String	ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES	= "ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES";

	public void initializeDefaultPreferences() {
		swtbotPreferenceStore.setDefault(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES, true);
	}

	public void earlyStartup() {
		new PreferenceInitializer().initializeFavorites();
	}

	void initializeFavorites() {
		List<String> imports;
		if (swtbotPreferenceStore.getBoolean(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES)) {
			imports = new OrderedSet<String>(getJDTImports());
			imports.addAll(getDefaultFavorites());
		} else {
			imports = getJDTImports();
			if (imports.containsAll(getDefaultFavorites()))
				imports.removeAll(getDefaultFavorites());
		}
		String join = StringUtils.join(imports, ";");
		jdtPreferenceStore.setValue(PreferenceConstants.CODEASSIST_FAVORITE_STATIC_MEMBERS, join);
	}

	public List<String> getJDTImports() {
		String preference = jdtPreferenceStore.getString(PreferenceConstants.CODEASSIST_FAVORITE_STATIC_MEMBERS);
		if (preference.trim().equals("")) {
			return new ArrayList<String>();
		}
		String[] imports = preference.split(";");
		return new ArrayList<String>(Arrays.asList(imports));
	}

	private OrderedSet<String> getDefaultFavorites() {
		OrderedSet<String> orderedSet = new OrderedSet<String>();
		orderedSet.add("org.eclipse.swtbot.swt.finder.matcher.WidgetMatcherFactory.*");
		orderedSet.add("org.hamcrest.Matchers.*");
		orderedSet.add("org.hamcrest.MatcherAssert.*");
		orderedSet.add("junit.framework.Assert.*");
		orderedSet.add("org.eclipse.swtbot.eclipse.finder.matcher.WidgetMatcherFactory.*");
		orderedSet.add("org.eclipse.swtbot.swt.finder.finder.UIThreadRunnable.*");
		orderedSet.add("org.eclipse.swtbot.swt.finder.SWTBotTestCase.*");
		orderedSet.add("org.eclipse.swtbot.swt.finder.wait.Conditions.*");
		return orderedSet;
	}

	public void propertyChange(PropertyChangeEvent event) {
		initializeFavorites();
	}
}
