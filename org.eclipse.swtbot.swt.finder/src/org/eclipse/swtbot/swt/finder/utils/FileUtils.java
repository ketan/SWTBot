/*******************************************************************************
 * Copyright (c) 2008-2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Provides utilities to read and write to files.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class FileUtils {

	/**
	 * @param filePath the path to the file.
	 * @return the contents of the file in filePath.
	 */
	public static String read(String filePath) {
		return read(new File(filePath));
	}

	/**
	 * @param file the file to read from.
	 * @return the contents of the file.
	 */
	public static String read(File file) {
		try {
			return read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param in the input stream to read from.
	 * @return the contents of the inputstream.
	 */
	public static String read(InputStream in) {
		return read(new InputStreamReader(in, Charset.forName("UTF-8")));
	}

	/**
	 * @param url the URL to read from.
	 * @return the contents of the url.
	 */
	public static String read(URL url) {
		try {
			return read(url.openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param in the reader to read from.
	 * @return the contents of the reader.
	 */
	public static String read(Reader in) {
		StringBuffer buffer = new StringBuffer();
		try {
			while (in.ready()) {
				buffer.append((char) in.read());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(in);
		}
		return buffer.toString();
	}

	/**
	 * @param text the contents to write to the file.
	 * @param file the file to write to.
	 */
	public static void write(String text, File file) {
		BufferedWriter w = null;
		try {
			try {
				w = new BufferedWriter(new FileWriter(file));
				w.append(text);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			close(w);
		}
	}

	/**
	 * Creates directory specified by destination, and all its parents if they don't exist.
	 * 
	 * @param destination the directory to create.
	 */
	public static void mkdirs(String destination) {
		mkdirs(new File(destination));
	}

	/**
	 * Creates directory specified by destination, and all its parents if they don't exist.
	 * 
	 * @param destination the directory to create.
	 */
	public static void mkdirs(File destination) {
		if (destination.exists()) {
			return;
		}
		if (!destination.mkdirs()) {
			throw new RuntimeException("Unable to create directory [" + destination + "].");
		}
	}

	private static void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
