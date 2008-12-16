/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;


import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.PathGenerator;
import org.eclipse.swtbot.swt.finder.utils.ClassUtils;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.StringDescription;

/**
 * A matcher that logs the result of matches. The match is done by subclasses.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AbstractMatcher.java 1219 2008-12-03 16:57:32Z kpadegaonkar $
 */
public abstract class AbstractMatcher<T> extends BaseMatcher<T> {

	public static final Logger	log	= Logger.getLogger(AbstractMatcher.class);

	public boolean matches(Object item) {
		boolean result = false;
		try {
			result = doMatch(item);
			String text = "";
			if (log.isDebugEnabled()) {
				text = SWTUtils.getText(item);
				try {
					if (text.length() > 20)
						text = text.substring(0, 20);
				} catch (Exception e) {
					// do nothing
				}
				if (result) {
					log.debug(MessageFormat.format("matched {0} {1}, using matcher: {2}", ClassUtils.simpleClassName(item), text, StringDescription.toString(this)));
				} else
					log.trace(MessageFormat.format("did not match {0} {1}, using matcher: {2}", ClassUtils.simpleClassName(item), text, StringDescription.toString(this)));
			}

			if (log.isTraceEnabled() && (item instanceof Widget)) {
				PathGenerator pathGenerator = new PathGenerator();
				TreePath path = pathGenerator.getPath((Widget) item);
				int segmentCount = path.getSegmentCount();

				String prefix = "";
				for (int i = 0; i < segmentCount - 1; i++)
					prefix += "    ";
				prefix += "+---";
				log.trace(prefix + "Widget: " + ClassUtils.simpleClassName(item) + "{" + text + "}");
			}
		} catch (Exception e) {
			log.warn("Matcher threw an exception: ", e);
		}
		return result;
	}

	/**
	 * Subclasses must override, this is the actual method that does the matching
	 *
	 * @param item the item to match.
	 * @return <code>true</code> if the item matches, <code>false</code> otherwise.
	 */
	protected abstract boolean doMatch(Object item);

}
