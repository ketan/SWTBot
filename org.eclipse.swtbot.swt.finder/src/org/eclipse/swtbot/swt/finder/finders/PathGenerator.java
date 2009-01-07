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


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.resolvers.DefaultChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.TreePath;

/**
 * This is a path generate used to resolve paths to a component. This is intended for use only internally by the
 * debugging and logging framework.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class PathGenerator {

	/** The childrenResolver */
	protected IChildrenResolver	childrenResolver;

	/**
	 * Gets the current registered child resolver.
	 *
	 * @return the childrenResolver. If it has not been set a default will be used.
	 */
	public IChildrenResolver getChildrenResolver() {
		if (childrenResolver == null)
			setChildrenResolver(new DefaultChildrenResolver());
		return childrenResolver;
	}

	/**
	 * Converts the string to an object representation.
	 *
	 * @param stringPath The string path to find.
	 * @param display The display to use.
	 * @return the path to a component represented by <code>stringPath</code>
	 */
	public TreePath getPathFromString(String stringPath, Display display) {
		String[] elements = stringPath.split("//");
		if (elements.length <= 1)
			return null;

		Widget[] treePath = new Widget[elements.length - 1];

		Object parent = display;

		for (int i = 1; i < elements.length; i++) {
			String token = elements[i];
			String[] pathElements = token.split("/");

			parent = getParent(treePath, parent, i, pathElements);
		}
		return new TreePath(treePath);
	}

	/**
	 * Converts the treePath to a string representation.
	 *
	 * @param path The tree path to use.
	 * @return the path in a string format
	 * @see #pathAsString(Widget, StringBuffer)
	 */
	public String getPathAsString(TreePath path) {
		StringBuffer builder = new StringBuffer();
		_getPathAsString(path, builder);
		return builder.toString();
	}

	/**
	 * Gets the widget contained within the parent at the given index.
	 *
	 * @param parent The parent widget to use.
	 * @param index The index of the child widget to get from the parent.
	 * @return the index of the widget in the given <code>parent</code>.
	 */
	public Widget getWidget(Object parent, int index) {
		if (parent instanceof Display)
			return getShell((Display) parent, index);
		else if (parent instanceof Widget)
			return getWidget((Widget) parent, index);
		return null;
	}

	/**
	 * Sets the resolver to use for child resolution.
	 *
	 * @param childrenResolver The child resolver to use.
	 */
	public void setChildrenResolver(IChildrenResolver childrenResolver) {
		this.childrenResolver = childrenResolver;
	}

	/**
	 * @param treePath
	 * @param parent
	 * @param i
	 * @param pathElements
	 * @return
	 */
	private Object getParent(Widget[] treePath, Object parent, int i, String[] pathElements) {
		if (pathElements.length > 0) {

			parent = getWidget(parent, Integer.valueOf(pathElements[1]).intValue());
			treePath[i - 1] = (Widget) parent;
		}
		return parent;
	}

	private void _getPathAsString(TreePath path, StringBuffer result) {
		Widget lastWidget = (Widget) path.getLastSegment();
		TreePath parentPath = path.getParentPath();
		if (parentPath != null)
			_getPathAsString(parentPath, result);
		if (lastWidget != null)
			pathAsString(lastWidget, result);
	}

	/**
	 * @param display
	 * @param index
	 * @return a shell on the display
	 */
	Widget getShell(final Display display, final int index) {
		return UIThreadRunnable.syncExec(display, new WidgetResult<Shell>() {
			public Shell run() {
				return display.getShells()[index];
			}
		});
	}

	/**
	 * @param parent
	 * @param index
	 * @return the widget at the specified position in the specified parent
	 */
	Widget getWidget(final Widget parent, final int index) {
		return UIThreadRunnable.syncExec(parent.getDisplay(), new WidgetResult<Widget>() {
			public Widget run() {
				return getChildrenResolver().getChildren(parent).get(index);
			}
		});
	}

	/**
	 * Converts the path to a widget into a String.
	 *
	 * @param w the widget.
	 * @param result the buffer into which the result should be returned.
	 */
	protected void pathAsString(Widget w, StringBuffer result) {
		String className = w.getClass().getName();
		int widgetIndex = SWTUtils.widgetIndex(w);
		result.append("//" + className.substring(className.lastIndexOf(".") + 1) + "/" + widgetIndex);
	}

	/**
	 * @param widget
	 * @return the path to the control, as a string
	 */
	public String getPathAsString(Widget widget) {
		return getPathAsString(getPath(widget));
	}

	/**
	 * @param widget
	 * @return the path to the widget from the shell that contains it
	 */
	public TreePath getPath(Widget widget) {
		return new ControlFinder().getPath(widget);
	}
}
