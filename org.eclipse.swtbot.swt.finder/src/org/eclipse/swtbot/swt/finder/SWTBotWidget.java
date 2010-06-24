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
 * Marks a SWTBot widget so tools recognise them. This anotation is primarily used to describe the convinience API that
 * should be generated for a class annotated with the specified widget.
 * <p>
 * Usage:
 * 
 * <pre>
 * &#064;SWTBotWidget(clasz = Button.class, style = @Style(name = &quot;SWT.PUSH&quot;, value = SWT.PUSH), preferredName = &quot;button&quot;, referenceBy = { ReferenceBy.LABEL, ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })
 * public class SWTBotButton ...{
 *   // the implement 
 * }
 * </pre>
 * 
 * The above represents:
 * <ul>
 * <li>widget of type 'Button.class'</li>
 * <li>with style bits 'SWT.PUSH'</li>
 * <li>the preferred name for the generated API would be 'button'</li>
 * <li>the widget can be referenced by: <strong>a combination</strong> of LABEL, MNEMONIC, TOOLTIP, in addition to the
 * defaults described in {@link #defaultReferenceBy()}</li>
 * </ul>
 * This annotation will generate the following convinience API to find buttons:
 * <ul>
 * <li>public SWTBotButton buttonWithLabel(String label)</li>
 * <li>public SWTBotButton buttonWithLabel(String label, int index)</li>
 * <li>public SWTBotButton button(String mnemonicText)</li>
 * <li>public SWTBotButton button(String mnemonicText, int index)</li>
 * <li>public SWTBotButton buttonWithTooltip(String tooltip)</li>
 * <li>public SWTBotButton buttonWithTooltip(String tooltip, int index)</li>
 * <li>public SWTBotButton buttonWithId(String key, String value)</li>
 * <li>public SWTBotButton buttonWithId(String key, String value, int index)</li>
 * <li>public SWTBotButton buttonWithId(String value)</li>
 * <li>public SWTBotButton buttonWithId(String value, int index)</li>
 * <li>public SWTBotButton buttonInGroup(String inGroup)</li>
 * <li>public SWTBotButton buttonInGroup(String inGroup, int index)</li>
 * <li>public SWTBotButton button()</li>
 * <li>public SWTBotButton button(int index)</li>
 * <li>public SWTBotButton buttonWithLabelInGroup(String label, String inGroup)</li>
 * <li>public SWTBotButton buttonWithLabelInGroup(String label, String inGroup, int index)</li>
 * <li>public SWTBotButton buttonInGroup(String mnemonicText, String inGroup)</li>
 * <li>public SWTBotButton buttonInGroup(String mnemonicText, String inGroup, int index)</li>
 * <li>public SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup)</li>
 * <li>public SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup, int index)</li>
 * </ul>
 * </p>
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
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

	Class<?> returnType() default Object.class;
}
