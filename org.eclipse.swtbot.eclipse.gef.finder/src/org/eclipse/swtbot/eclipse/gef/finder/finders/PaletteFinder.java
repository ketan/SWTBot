/*******************************************************************************
 * Copyright (c) 2004, 2009 MAKE Technologies Inc. and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     MAKE Technologies Inc - initial API and implementation
 *     Mariot Chauvin <mariot.chauvin@obeo.fr> - refactoring
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.gef.finder.finders;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.hamcrest.Matcher;

/**
 * A finder for finding {@link PaletteEntry palette entries}.
 * 
 * @author David Green
 */
public class PaletteFinder {

	private final EditDomain editDomain;

	public PaletteFinder(EditDomain editDomain) {
		this.editDomain = editDomain;
	}
	
	public List<PaletteEntry> findEntries(final Matcher<?> matcher) {
		return UIThreadRunnable.syncExec(new Result<List<PaletteEntry>>() {
			public List<PaletteEntry> run() {
				List<PaletteEntry> list = new ArrayList<PaletteEntry>();
				PaletteRoot paletteRoot = editDomain.getPaletteViewer().getPaletteRoot();
				find(list,paletteRoot,matcher);
				return list;
			}
		});
	}
	
	protected void find(List<PaletteEntry> list, PaletteContainer container, Matcher<?> matcher) {
		List<PaletteEntry> children = container.getChildren();
		if (children != null) {
			for (PaletteEntry entry: children) {
				if (matcher.matches(entry)) {
					list.add(entry);
				} else if (entry instanceof PaletteContainer) {
					find(list,(PaletteContainer)entry,matcher);
				}
			}
		}
	}
}
