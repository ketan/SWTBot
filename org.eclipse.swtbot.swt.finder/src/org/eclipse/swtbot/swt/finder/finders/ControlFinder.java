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
package org.eclipse.swtbot.swt.finder.finders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.collections.OrderedSet;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultParentResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IParentResolver;
import org.eclipse.swtbot.swt.finder.results.ArrayResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Matcher;

/**
 * Finds controls matching a particular matcher.
 * 
 * @see UIThreadRunnable
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class ControlFinder {

	/**
	 * The logging instance for this class.
	 */
	private static final Logger			log							= Logger.getLogger(ControlFinder.class);

	/** The childrenResolver */
	protected final IChildrenResolver	childrenResolver;

	/** The display */
	protected Display					display;

	/** The parentResolver */
	protected final IParentResolver		parentResolver;

	/**
	 * Set to true if the control finder should find invisible controls. Invisible controls are ones hidden from the
	 * display (isVisible() = false)
	 * 
	 * @since 1.0
	 */
	public boolean						shouldFindInVisibleControls	= false;

	/**
	 * Creates a Control finder using {@link DefaultChildrenResolver} and {@link DefaultParentResolver}.
	 */
	public ControlFinder() {
		this(new DefaultChildrenResolver(), new DefaultParentResolver());
	}

	/**
	 * Creates a control finder using the given resolvers.
	 * 
	 * @param childrenResolver the resolver used to resolve children of a control.
	 * @param parentResolver the resolver used to resolve parent of a control.
	 */
	public ControlFinder(IChildrenResolver childrenResolver, IParentResolver parentResolver) {
		display = SWTUtils.display();
		this.childrenResolver = childrenResolver;
		this.parentResolver = parentResolver;
	}

	/**
	 * Finds the controls in the active shell matching the given matcher.
	 * <p>
	 * Note: This method is thread safe.
	 * </p>
	 * 
	 * @param matcher the matcher used to find controls in the active shell.
	 * @return all controls in the active shell that the matcher matches.
	 * @see Display#getActiveShell()
	 */
	public List<? extends Widget> findControls(Matcher<?> matcher) {
		return findControls(activeShell(), matcher, true);
	}

	/**
	 * Finds the controls matching one of the widgets using the given matcher. This will also go recursively though the
	 * {@code widgets} provided.
	 * 
	 * @param widgets the list of widgets.
	 * @param matcher the matcher used to match the widgets.
	 * @param recursive if the match should be recursive.
	 * @return all visible widgets in the children that the matcher matches. If recursive is <code>true</code> then find
	 *         the widgets within each of the widget.
	 */
	public List<? extends Widget> findControls(final List<Widget> widgets, final Matcher<?> matcher, final boolean recursive) {
		return findControlsInternal(widgets, matcher, recursive);
	}

	/**
	 * Returns true if the widget is a control and it is visible.
	 * <p>
	 * This method is not thread safe and must be invoked from the UI thread.
	 * </p>
	 * <p>
	 * TODO visibility of tab items.
	 * </p>
	 * 
	 * @param w the widget
	 * @return <code>true</code> if the control is visible, <code>false</code> otherwise.
	 * @see Control#getVisible()
	 * @since 1.0
	 */
	protected boolean visible(Widget w) {
		if (shouldFindInVisibleControls)
			return true;
		return !((w instanceof Control) && !((Control) w).getVisible());
	}

	/**
	 * Finds the controls starting with the given parent widget and uses the given matcher. If recursive is set, it will
	 * attempt to find the controls recursively in each child widget if they exist.
	 * <p>
	 * This method is thread safe.
	 * </p>
	 * 
	 * @param parentWidget the parent widget in which controls should be found.
	 * @param matcher the matcher used to match the widgets.
	 * @param recursive if the match should be recursive.
	 * @return all visible widgets in the parentWidget that the matcher matches. If recursive is <code>true</code> then
	 *         find the widget within each of the parentWidget.
	 */
	public List<? extends Widget> findControls(final Widget parentWidget, final Matcher<?> matcher, final boolean recursive) {
		return UIThreadRunnable.syncExec(display, new ListResult<Widget>() {
			public List<Widget> run() {
				return findControlsInternal(parentWidget, matcher, recursive);
			}
		});
	}

	/**
	 * This finds controls using the list of widgets and the matcher. If recursive is set, it will attempt to find the
	 * controls recursively in each child widget if they exist.
	 * <p>
	 * This method is not thread safe and must be invoked from the UI thread.
	 * </p>
	 * 
	 * @see #findControls(List, Matcher, boolean)
	 */
	private List<? extends Widget> findControlsInternal(final List<Widget> widgets, final Matcher<?> matcher, final boolean recursive) {
		OrderedSet<Widget> list = new OrderedSet<Widget>();
		for (Widget w : widgets) {
			list.addAll(findControlsInternal(w, matcher, recursive));
		}
		return new ArrayList<Widget>(list);
	}

	/**
	 * Find controls starting from the parent widget using the given matcher. If recursive is set, it will attempt to
	 * find the controls recursively in each child widget if they exist.
	 * <p>
	 * This method is not thread safe and must be invoked from the UI thread.
	 * </p>
	 * 
	 * @see #findControlsInternal(Widget, Matcher, boolean)
	 */
	private List<Widget> findControlsInternal(final Widget parentWidget, final Matcher<?> matcher, final boolean recursive) {
		if ((parentWidget == null) || parentWidget.isDisposed())
			return new ArrayList<Widget>();
		if (!visible(parentWidget)) {
			if (!isComposite(parentWidget))
				log.trace(MessageFormat.format("{0} is not visible, skipping.", parentWidget));
			return new ArrayList<Widget>();
		}
		OrderedSet<Widget> controls = new OrderedSet<Widget>();
		if (matcher.matches(parentWidget) && !controls.contains(parentWidget))
			controls.add(parentWidget);
		if (recursive) {
			List<Widget> children = getChildrenResolver().getChildren(parentWidget);
			controls.addAll(findControlsInternal(children, matcher, recursive));
		}
		return new ArrayList<Widget>(controls);
	}

	private boolean isComposite(Widget parentWidget) {
		return parentWidget.getClass().equals(Composite.class);
	}

	/**
	 * Finds the shell matching the given text (shell.getText()).
	 * 
	 * @param text The text on the Shell
	 * @return A Shell containing the specified text
	 */
	public List<Shell> findShells(final String text) {
		return UIThreadRunnable.syncExec(new ListResult<Shell>() {
			public List<Shell> run() {
				ArrayList<Shell> list = new ArrayList<Shell>();
				Shell[] shells = getShells();
				for (Shell shell : shells) {
					if (shell.getText().equals(text))
						list.add(shell);
				}
				return list;
			}
		});
	}

	/**
	 * Gets the registered children resolver. If the resolver had never been set a default resolver will be created.
	 * 
	 * @return the childrenResolver
	 */
	public IChildrenResolver getChildrenResolver() {
		return childrenResolver;
	}

	/**
	 * Gets the registered parent resolver. If the resolver was not registered then a default instance will be returned.
	 * 
	 * @return the parentResolver
	 */
	public IParentResolver getParentResolver() {
		return parentResolver;
	}

	/**
	 * Gets the path to the widget. The path is the list of all parent containers of the widget.
	 * 
	 * @param w the widget.
	 * @return the path to the widget w.
	 */
	public TreePath getPath(Widget w) {
		return new TreePath(getParents(w).toArray());
	}

	/**
	 * Gets the shells registered with the display.
	 * 
	 * @return the shells
	 */
	public Shell[] getShells() {
		return UIThreadRunnable.syncExec(display, new ArrayResult<Shell>() {
			public Shell[] run() {
				return display.getShells();
			}
		});
	}

	/**
	 * Return the active shell.
	 * 
	 * @return the active shell.
	 * @see Display#getActiveShell()
	 */
	public Shell activeShell() {
		Shell activeShell = UIThreadRunnable.syncExec(display, new WidgetResult<Shell>() {
			public Shell run() {
				return display.getActiveShell();
			}
		});
		if (activeShell != null)
			return activeShell;
		return UIThreadRunnable.syncExec(display, new WidgetResult<Shell>() {
			public Shell run() {
				Shell[] shells = getShells();
				for (Shell shell : shells)
					if (shell.isFocusControl())
						return shell;
				return null;
			}
		});
	}

	private List<Widget> getParents(final Widget w) {
		return UIThreadRunnable.syncExec(display, new ListResult<Widget>() {
			public List<Widget> run() {
				Widget parent = w;
				List<Widget> parents = new LinkedList<Widget>();
				while (parent != null) {
					parents.add(parent);
					parent = getParentResolver().getParent(parent);
				}
				Collections.reverse(parents);
				return parents;
			}
		});
	}

}
