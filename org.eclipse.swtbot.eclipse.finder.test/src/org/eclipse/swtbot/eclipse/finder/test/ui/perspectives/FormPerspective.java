package org.eclipse.swtbot.eclipse.finder.test.ui.perspectives;

import org.eclipse.swtbot.eclipse.finder.FinderTestIds;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class FormPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView(FinderTestIds.VIEW_ID_FORM_1, IPageLayout.LEFT, 0.2f, layout.getEditorArea());
		layout.addView(FinderTestIds.VIEW_ID_FORM_2, IPageLayout.RIGHT, 0.2f, FinderTestIds.VIEW_ID_FORM_1);
	}

}
