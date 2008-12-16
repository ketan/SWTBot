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
package org.eclipse.swt.examples.texteditor;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class Images {

	public Image	Blue;
	// Bitmap Images
	public Image	Bold;
	public Image	Erase;
	public Image	Green;
	public Image	Italic;
	public Image	Red;
	public Image	Strikeout;
	public Image	Underline;

	Image[]			AllBitmaps;

	Images() {
	}

	public void freeAll() {
		for (int i = 0; i < AllBitmaps.length; i++)
			AllBitmaps[i].dispose();
		AllBitmaps = null;
	}

	public void loadAll(Display display) {
		// Bitmap Images
		Bold = createBitmapImage(display, "bold");
		Italic = createBitmapImage(display, "italic");
		Underline = createBitmapImage(display, "underline");
		Strikeout = createBitmapImage(display, "strikeout");
		Red = createBitmapImage(display, "red");
		Green = createBitmapImage(display, "green");
		Blue = createBitmapImage(display, "blue");
		Erase = createBitmapImage(display, "erase");

		AllBitmaps = new Image[] { Bold, Italic, Underline, Strikeout, Red, Green, Blue, Erase, };
	}

	Image createBitmapImage(Display display, String fileName) {
		InputStream sourceStream = Images.class.getResourceAsStream(fileName + ".bmp");
		InputStream maskStream = Images.class.getResourceAsStream(fileName + "_mask.bmp");
		ImageData source = new ImageData(sourceStream);
		ImageData mask = new ImageData(maskStream);
		Image result = new Image(display, source, mask);
		try {
			sourceStream.close();
			maskStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
