package org.eclipse.swtbot.swt.finder.waits;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;

class WidgetIsEnabledCondition extends DefaultCondition {

	private final AbstractSWTBot<? extends Widget>	widget;

	WidgetIsEnabledCondition(AbstractSWTBot<? extends Widget> widget) {
		this.widget = widget;
	}

	public boolean test() throws Exception {
		return widget.isEnabled();
	}

	public String getFailureMessage() {
		return "The widget " + widget + " was not enabled.";
	}

}
