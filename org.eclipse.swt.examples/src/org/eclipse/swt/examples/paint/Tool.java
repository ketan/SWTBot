/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.examples.paint;

import org.eclipse.swt.graphics.Image;

public class Tool {
	public Runnable	action;
	public Object	data;
	public String	group;
	public int		id;
	public Image	image	= null;
	public String	name;
	public int		type;

	public Tool(int id, String name, String group, int type) {
		super();
		this.id = id;
		this.name = name;
		this.group = group;
		this.type = type;
	}

	public Tool(int id, String name, String group, int type, Object data) {
		this(id, name, group, type);
		this.data = data;
	}
}
