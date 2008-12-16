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

import static java.lang.annotation.ElementType.TYPE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.ID_KEY_VALUE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.ID_VALUE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.IN_GROUP;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.LABEL;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.MNEMONIC;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.NONE;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.TEXT;
import static org.eclipse.swtbot.swt.finder.ReferenceBy.TOOLTIP;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Widget;

/**
 * Marks a SWTBot widget so tools recognise them.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: SWTBotWidget.java 1188 2008-12-02 06:55:23Z kpadegaonkar $
 * @since 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { TYPE })
public @interface SWTBotWidget {
	/** Widget can be found using the following methods */
	ReferenceBy[] referenceBy() default { ID_KEY_VALUE, ID_VALUE, IN_GROUP, LABEL, MNEMONIC, NONE, TEXT, TOOLTIP };

	/** Widget is of the specified class */
	Class<? extends Widget> clasz();

	/** If a the specified widget can have different styles, specify one of the styles in {@link SWT} */
	Style style() default @Style();

	/** The preferred name for the widget */
	String preferredName();

	/** Default reference by */
	ReferenceBy[] defaultReferenceBy() default { ReferenceBy.ID_KEY_VALUE, ReferenceBy.ID_VALUE, ReferenceBy.IN_GROUP, ReferenceBy.NONE };
}
