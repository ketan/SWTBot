/*******************************************************************************
 * Copyright (c) 2011 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Toby Weston - initial API and implementation (Bug 259799)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static java.util.Collections.emptyList;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.waitForWidget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.IntResult;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

/**
 * Represents an {@link ExpandBar}.
 * 
 * @author Toby Weston
 */
@SWTBotWidget(clasz = ExpandBar.class, preferredName = "expandBar", referenceBy = { ReferenceBy.LABEL })
public class SWTBotExpandBar extends AbstractSWTBot<ExpandBar> {

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotExpandBar(ExpandBar w) {
		super(w);
	}

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param w the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 */
	public SWTBotExpandBar(ExpandBar w, SelfDescribing description) {
		super(w, description);
	}

	/**
	 * @return the number of {@link ExpandItem}s in the widget.
	 */
	public int itemCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				return widget.getItemCount();
			}
		});
	}

	/**
	 * @return the number of items that are expanded.
	 * @see #itemCount()
	 * @see #collapsedItemCount()
	 */
	public int expandedItemCount() {
		return syncExec(new IntResult() {
			public Integer run() {
				int count = 0;
				for (ExpandItem item : widget.getItems()) {
					if (item.getExpanded())
						count++;
				}
				return count;
			}
		});
	}

	/**
	 * @return the number of items that are collapsed.
	 * @see #itemCount()
	 * @see #expandedItemCount()
	 */
	public int collapsedItemCount() {
		return itemCount() - expandedItemCount();
	}

	/**
	 * Expands the {@link ExpandItem} and returns it.
	 * 
	 * @param itemText the text on the item.
	 * @return the {@link SWTBotExpandItem} with the specified text.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotExpandItem expandItem(final String itemText) {
		return expandItem(withText(itemText));
	}

	/**
	 * Expands the {@link ExpandItem} and returns it.
	 * 
	 * @param matcher the matcher.
	 * @return the {@link SWTBotExpandItem} that matches the matcher
	 */
	public SWTBotExpandItem expandItem(Matcher<Widget> matcher) {
		waitForEnabled();
		return getExpandItem(matcher).expand();
	}

	/**
	 * Collapses the {@link ExpandItem} and returns it.
	 * 
	 * @param itemText the text on the item.
	 * @return the {@link SWTBotExpandItem} with the specified text.
	 */
	@SuppressWarnings("unchecked")
	public SWTBotExpandItem collapseItem(final String itemText) {
		return collapseItem(withText(itemText));
	}

	/**
	 * Collapses the {@link ExpandItem} and returns it.
	 * 
	 * @param matcher the matcher.
	 * @return the {@link SWTBotExpandItem} that matches the matcher.
	 */
	public SWTBotExpandItem collapseItem(Matcher<Widget> matcher) {
		waitForEnabled();
		return getExpandItem(matcher).collapse();
	}

	/**
	 * Return the {@link ExpandItem} that matches the specified matcher.
	 * 
	 * @param matcher the matcher.
	 * @return the {@link SWTBotExpandItem} with the specified text.
	 */
	public SWTBotExpandItem getExpandItem(Matcher<Widget> matcher) {
		try {
			matcher = allOf(widgetOfType(ExpandItem.class), matcher);
			WaitForObjectCondition<? extends Widget> waitForWidget = waitForWidget(matcher, widget);
			new SWTBot().waitUntilWidgetAppears(waitForWidget);
			return new SWTBotExpandItem((ExpandItem) waitForWidget.get(0), matcher);
		} catch (TimeoutException e) {
			throw new WidgetNotFoundException("Timed out waiting for expandBar item " + matcher, e);
		}
	}

	/**
	 * @return a list of {@link SWTBotExpandItem} or the empty list if there was a problem with any of the items.
	 */
	public List<SWTBotExpandItem> getAllItems() {
		return syncExec(new ListResult<SWTBotExpandItem>() {
			public List<SWTBotExpandItem> run() {
				List<SWTBotExpandItem> result = new ArrayList<SWTBotExpandItem>();
				for (ExpandItem item : widget.getItems()) {
					try {
						result.add(new SWTBotExpandItem(item));
					} catch (WidgetNotFoundException e) {
						return emptyList();
					}
				}
				return result;
			}
		});
	}

	/**
	 * @return <code>true</code> if the expandBar has any items, <code>false</code> otherwise.
	 */
	public boolean hasItems() {
		return itemCount() > 0;
	}
}
