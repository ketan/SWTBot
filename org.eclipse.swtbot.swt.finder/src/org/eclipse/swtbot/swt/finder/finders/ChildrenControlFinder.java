/*******************************************************************************
 * Copyright (c) 2008 Cedric Chabanois and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Chabanois - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.finders;

import java.util.List;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultParentResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IParentResolver;
import org.hamcrest.Matcher;

/**
 * Finds controls matching a particular matcher in the given parent widget.
 *
 * @author Cedric Chabanois &lt;cchabanois [at] no-log [dot] org&gt;
 * @version $Id$
 * @since 1.0
 */
public class ChildrenControlFinder extends ControlFinder {
	/**
	 * The parent widget to begin searching for children.
	 *
	 * @since 1.1
	 */
	protected final Widget	parentWidget;

	/**
	 * Constructs a child control finder widget using the given parent widget as its starting point.
	 *
	 * @param parentWidget the parent widget in which controls should be found.
	 */
	public ChildrenControlFinder(Widget parentWidget) {
		this(parentWidget, new DefaultChildrenResolver(), new DefaultParentResolver());
	}

	/**
	 * Constructs the child control finder with the given parent widget as it's starting point and the set resolvers.
	 *
	 * @param parentWidget the parent widget in which controls should be found.
	 * @param childrenResolver the resolver used to resolve children of a control.
	 * @param parentResolver the resolver used to resolve parent of a control.
	 */
	public ChildrenControlFinder(Widget parentWidget, IChildrenResolver childrenResolver, IParentResolver parentResolver) {
		super(childrenResolver, parentResolver);
		this.parentWidget = parentWidget;
	}

	/**
	 * Attempts to find the controls using the given matcher starting with the given parent widget. This will search
	 * recursively.
	 *
	 * @param matcher the matcher used to find controls in the {@link #parentWidget}.
	 * @return all controls in the parent widget that the matcher matches.
	 */
	@Override
	public List<? extends Widget> findControls(Matcher<?> matcher) {
		return findControls(parentWidget, matcher, true);
	}

}
