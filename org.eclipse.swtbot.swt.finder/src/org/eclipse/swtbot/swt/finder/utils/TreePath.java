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

import java.util.Arrays;

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
	public TreePath(Object... segments) {
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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hash;
		result = prime * result + Arrays.hashCode(segments);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreePath other = (TreePath) obj;
		if (hash != other.hash)
			return false;
		if (!Arrays.equals(segments, other.segments))
			return false;
		return true;
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
	 * Removes the last path component from the tree and returns it.
	 * 
	 * @return the last path component after removing it from the tree.
	 */
	public Object pop() {
		int segmentCount = getSegmentCount();
		if (segmentCount == 0) {
			return null;
		}
		Object lastSegment = getLastSegment();

		Object[] parentSegments = new Object[segmentCount - 1];
		System.arraycopy(segments, 0, parentSegments, 0, segmentCount - 1);
		segments = parentSegments;
		return lastSegment;
	}
}
