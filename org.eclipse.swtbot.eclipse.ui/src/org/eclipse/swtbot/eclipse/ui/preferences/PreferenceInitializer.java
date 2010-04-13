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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swtbot.eclipse.ui.Activator;
import org.eclipse.ui.IStartup;

/**
 * Initializes the default preferences if none exist.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer implements IStartup, IPropertyChangeListener {

	private static final String		EMPTY										= "";											//$NON-NLS-1$
	private static final String		SEMI_COLON									= ";";											//$NON-NLS-1$

	private final IPreferenceStore	swtbotPreferenceStore;
	private final IPreferenceStore	jdtPreferenceStore;
	static final String				ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES	= "ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES";	//$NON-NLS-1$

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

	public void initializeDefaultPreferences() {
		swtbotPreferenceStore.setDefault(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES, true);
	}

	public void earlyStartup() {
		new PreferenceInitializer().initializeFavorites();
	}

	void initializeFavorites() {
		Set<String> imports;
		if (swtbotPreferenceStore.getBoolean(PreferenceInitializer.ENABLE_ADDITIONAL_AUTOCOMPLETE_FAVOURTES)) {
			imports = new LinkedHashSet<String>(getJDTImports());
			imports.addAll(getDefaultFavorites());
		} else {
			imports = getJDTImports();
			if (imports.containsAll(getDefaultFavorites()))
				imports.removeAll(getDefaultFavorites());
		}
		String join = join(imports, SEMI_COLON);
		jdtPreferenceStore.setValue(PreferenceConstants.CODEASSIST_FAVORITE_STATIC_MEMBERS, join);
	}

	/**
	 * @return the JDT favourite imports.
	 */
	public Set<String> getJDTImports() {
		String preference = jdtPreferenceStore.getString(PreferenceConstants.CODEASSIST_FAVORITE_STATIC_MEMBERS);
		if (EMPTY.equals(preference.trim())) {
			return new HashSet<String>();
		}
		String[] imports = preference.split(SEMI_COLON);
		return new LinkedHashSet<String>(Arrays.asList(imports));
	}

	private LinkedHashSet<String> getDefaultFavorites() {
		LinkedHashSet<String> orderedSet = new LinkedHashSet<String>();
		orderedSet.add(importStatement("org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory"));
		orderedSet.add(importStatement("org.hamcrest.Matchers"));
		orderedSet.add(importStatement("org.hamcrest.MatcherAssert"));
		orderedSet.add(importStatement("org.junit.Assert"));
		orderedSet.add(importStatement("org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory"));
		orderedSet.add(importStatement("org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable"));
		orderedSet.add(importStatement("org.eclipse.swtbot.swt.finder.SWTBotAssert"));
		orderedSet.add(importStatement("org.eclipse.swtbot.swt.finder.SWTBotAssert"));
		orderedSet.add(importStatement("org.eclipse.swtbot.eclipse.finder.waits.Conditions"));
		orderedSet.add(importStatement("org.eclipse.swtbot.swt.finder.waits.Conditions"));
		return orderedSet;
	}

	public void propertyChange(PropertyChangeEvent event) {
		initializeFavorites();
	}

	private String importStatement(String clazz) {
		return clazz + ".*"; //$NON-NLS-1$;
	}

	private String join(Collection<?> toJoin, String delimiter) {
		if ((toJoin == null) || (toJoin.size() == 0))
			return ""; 
		StringBuffer result = new StringBuffer();

		for (Object object : toJoin) {
			result.append(object);
			result.append(delimiter);
		}

		result.lastIndexOf(delimiter);
		result.replace(result.length() - delimiter.length(), result.length(), ""); //$NON-NLS-1$
		return result.toString();
	}

}
