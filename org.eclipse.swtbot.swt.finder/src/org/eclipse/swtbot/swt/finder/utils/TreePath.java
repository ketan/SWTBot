/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

public final class TreePath {

	/**
	 * Constant for representing an empty tree path.
	 */
	public static final TreePath	EMPTY	= new TreePath(new Object[0]);

	private Object[]				segments;

	private int						hash;

	/**
	 * Constructs a path identifying a leaf node in a tree.
	 * 
	 * @param segments path of elements to a leaf node in a tree, starting with the root element
	 */
	public TreePath(Object[] segments) {
		Assert.isNotNull(segments);
		for (int i = 0; i < segments.length; i++) {
			Assert.isNotNull(segments[i]);
		}
		this.segments = segments;
	}

	/**
	 * Returns the element at the specified index in this path.
	 * 
	 * @param index index of element to return
	 * @return element at the specified index
	 */
	public Object getSegment(int index) {
		return segments[index];
	}

	/**
	 * Returns the number of elements in this path.
	 * 
	 * @return the number of elements in this path
	 */
	public int getSegmentCount() {
		return segments.length;
	}

	/**
	 * Returns the first element in this path, or <code>null</code> if this path has no segments.
	 * 
	 * @return the first element in this path
	 */
	public Object getFirstSegment() {
		if (segments.length == 0) {
			return null;
		}
		return segments[0];
	}

	/**
	 * Returns the last element in this path, or <code>null</code> if this path has no segments.
	 * 
	 * @return the last element in this path
	 */
	public Object getLastSegment() {
		if (segments.length == 0) {
			return null;
		}
		return segments[segments.length - 1];
	}

	/**
	 * Returns a copy of this tree path with one segment removed from the end, or <code>null</code> if this tree path
	 * has no segments.
	 * 
	 * @return a tree path
	 */
	public TreePath getParentPath() {
		int segmentCount = getSegmentCount();
		if (segmentCount < 1) {
			return null;
		} else if (segmentCount == 1) {
			return EMPTY;
		}
		Object[] parentSegments = new Object[segmentCount - 1];
		System.arraycopy(segments, 0, parentSegments, 0, segmentCount - 1);
		return new TreePath(parentSegments);
	}

	/**
	 * Returns a copy of this tree path with the given segment added at the end.
	 * 
	 * @param newSegment
	 * @return a tree path
	 */
	public TreePath createChildPath(Object newSegment) {
		int segmentCount = getSegmentCount();
		Object[] childSegments = new Object[segmentCount + 1];
		if (segmentCount > 0) {
			System.arraycopy(segments, 0, childSegments, 0, segmentCount);
		}
		childSegments[segmentCount] = newSegment;
		return new TreePath(childSegments);
	}
}
