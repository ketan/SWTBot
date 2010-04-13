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
package org.eclipse.swtbot.eclipse.ui.test;

import org.eclipse.swtbot.eclipse.ui.preferences.PreferenceInitializerTest;
import org.eclipse.swtbot.eclipse.ui.project.ProjectCreatorTest;
import org.eclipse.swtbot.eclipse.ui.project.TemplatizerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@RunWith(Suite.class)
@SuiteClasses({PreferenceInitializerTest.class, ProjectCreatorTest.class, TemplatizerTest.class})
public class AllTests {

}
