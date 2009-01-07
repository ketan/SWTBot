/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Hans Schwaebli - http://swtbot.org/bugzilla/show_bug.cgi?id=112
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;

/**
 * Translates from strings to one of the constants in SWT#IMAGE_*
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 1.3
 */
public class ImageFormatConverter {

	private List<ImageType>	imageTypes	= new ArrayList<ImageType>();

	/**
	 * The default constructor.
	 */
	public ImageFormatConverter() {
		this.imageTypes.add(new ImageType("BMP", SWT.IMAGE_BMP)); //$NON-NLS-1$
		this.imageTypes.add(new ImageType("GIF", SWT.IMAGE_GIF)); //$NON-NLS-1$
		this.imageTypes.add(new ImageType("ICO", SWT.IMAGE_ICO)); //$NON-NLS-1$
		this.imageTypes.add(new ImageType("JPEG", SWT.IMAGE_JPEG)); //$NON-NLS-1$
		this.imageTypes.add(new ImageType("JPG", SWT.IMAGE_JPEG)); //$NON-NLS-1$
		this.imageTypes.add(new ImageType("PNG", SWT.IMAGE_PNG)); //$NON-NLS-1$
		this.imageTypes.add(new ImageType("TIFF", SWT.IMAGE_TIFF)); //$NON-NLS-1$
	}

	/**
	 * @param extension the image format
	 * @return one of the constants defined in SWT#IMAGE*
	 * @throws IllegalArgumentException if the type could not be resolved
	 */
	public int imageTypeOf(String extension) {
		return typeFor(extension).type;
	}

	private ImageType typeFor(String format) {
		for (Iterator<ImageType> iterator = imageTypes.iterator(); iterator.hasNext();) {
			ImageType type = iterator.next();
			if (type.name.equalsIgnoreCase(format))
				return type;
		}
		throw new IllegalArgumentException("Did not understand format: " + format); //$NON-NLS-1$
	}

	/**
	 * Mapping from strings to constants in SWT#IMAGE_*
	 *
	 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
	 * @version $Id$
	 */
	protected class ImageType {
		/** The name of the image type */
		public final String	name;

		/** The type of the image, one of the constants in SWT.IMAGE_ */
		public final int	type;

		/**
		 * @param name the name of the image.
		 * @param type the type of the image.
		 */
		public ImageType(String name, int type) {
			this.name = name;
			this.type = type;
		}
	}

}
