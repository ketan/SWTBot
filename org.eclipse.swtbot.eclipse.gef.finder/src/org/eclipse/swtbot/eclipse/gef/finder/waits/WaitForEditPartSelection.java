/*******************************************************************************
 * Copyright (c) 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - initial API and implementation
 *******************************************************************************/

package org.eclipse.swtbot.eclipse.gef.finder.waits;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.swtbot.eclipse.gef.finder.matchers.IsSelected;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;

/**
 * Condition that waits that given edit part is selected.
 *
 * @see Conditions
 * @author mchauvin
 */
public class WaitForEditPartSelection extends WaitForObjectCondition<SWTBotGefEditPart> {

	private SWTBotGefEditPart part;
	
	public WaitForEditPartSelection(final SWTBotGefEditPart part, final EditPartViewer viewer) {
		super(new IsSelected(viewer));
		this.part = part;
	}
	
	@Override
	protected List<SWTBotGefEditPart> findMatches() {
		List<SWTBotGefEditPart> list = new ArrayList<SWTBotGefEditPart>(2);
		if (matcher.matches(part))
			list.add(part);
		return list;
	}

	public String getFailureMessage() {
		return "Could not find edit part in selected edit part list"; //$NON-NLS-1$
	}

}
