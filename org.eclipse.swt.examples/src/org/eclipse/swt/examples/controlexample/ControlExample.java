/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.examples.controlexample;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ControlExample {
	private static ResourceBundle	resourceBundle	= ResourceBundle.getBundle("examples_control");			//$NON-NLS-1$
	static final int				ciClosedFolder	= 0, ciOpenFolder = 1, ciTarget = 2, ciBackground = 3,
			ciParentBackground = 4;
	static final String[]			imageLocations	= { "closedFolder.gif", //$NON-NLS-1$
			"openFolder.gif", //$NON-NLS-1$
			"target.gif", //$NON-NLS-1$
			"backgroundImage.png", //$NON-NLS-1$
			"parentBackgroundImage.png"			};															//$NON-NLS-1$
	static final int[]				imageTypes		= { SWT.ICON, SWT.ICON, SWT.ICON, SWT.BITMAP, SWT.BITMAP };

	/**
	 * Invokes as a standalone program.
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		ControlExample instance = new ControlExample(shell);
		shell.setText(ControlExample.getResourceString("window.title"));
		ControlExample.setShellSize(instance, shell);
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		instance.dispose();
		display.dispose();
	}

	/**
	 * Gets a string from the resource bundle. We don't want to crash because of a missing String. Returns the key if
	 * not found.
	 */
	public static String getResourceString(String key) {
		try {
			return ControlExample.resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Gets a string from the resource bundle and binds it with the given arguments. If the key is not found, return the
	 * key.
	 */
	static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(ControlExample.getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Sets the size of the shell to it's "packed" size, unless that makes it larger than the monitor it is being
	 * displayed on, in which case just set the shell size to be slightly smaller than the monitor.
	 */
	public static void setShellSize(ControlExample instance, Shell shell) {
		Point size = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Rectangle monitorArea = shell.getMonitor().getClientArea();
		/*
		 * Workaround: if the tab folder is wider than the screen, carbon clips instead of somehow scrolling the tab
		 * items. We try to recover some width by using shorter tab names.
		 */
		//		if (size.x > monitorArea.width && SWT.getPlatform().equals("carbon")) {
		//			TabItem[] tabItems = instance.tabFolder.getItems();
		//			for (int i = 0; i < tabItems.length; i++)
		//				tabItems[i].setText(instance.tabs[i].getShortTabText());
		//			size = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		//		}
		shell.setSize(Math.min(size.x, monitorArea.width), Math.min(size.y, monitorArea.height));
	}

	private ShellTab	shellTab;

	private TabFolder	tabFolder;

	private final Tab[]	tabs;

	Image				images[];

	boolean				startup	= true;

	/**
	 * Creates an instance of a ControlExample embedded inside the supplied parent Composite.
	 * 
	 * @param parent
	 *            the container of the example
	 */
	public ControlExample(Composite parent) {
		initResources();
		tabFolder = new TabFolder(parent, SWT.NONE);
		tabs = createTabs();
		for (int i = 0; i < tabs.length; i++) {
			TabItem item = new TabItem(tabFolder, SWT.NONE);
			item.setText(tabs[i].getTabText());
			item.setControl(tabs[i].createTabFolderPage(tabFolder));
			item.setData(tabs[i]);
		}
		startup = false;
	}

	/**
	 * Disposes of all resources associated with a particular instance of the ControlExample.
	 */
	public void dispose() {
		/*
		 * Destroy any shells that may have been created by the Shells tab. When a shell is disposed, all child shells
		 * are also disposed. Therefore it is necessary to check for disposed shells in the shells list to avoid
		 * disposing a shell twice.
		 */
		if (shellTab != null)
			shellTab.closeAllShells();
		shellTab = null;
		tabFolder = null;
		freeResources();
	}

	/**
	 * Grabs input focus.
	 */
	public void setFocus() {
		tabFolder.setFocus();
	}

	/**
	 * Answers the set of example Tabs
	 */
	Tab[] createTabs() {
		return new Tab[] { new ButtonTab(this), new CanvasTab(this), new ComboTab(this), new CoolBarTab(this),
				new DateTimeTab(this), new DialogTab(this), new ExpandBarTab(this), new GroupTab(this),
				new LabelTab(this), new LinkTab(this), new ListTab(this), new MenuTab(this), new ProgressBarTab(this),
				new SashTab(this), new ScaleTab(this), shellTab = new ShellTab(this), new SliderTab(this),
				new SpinnerTab(this), new TabFolderTab(this), new TableTab(this), new TextTab(this),
				new ToolBarTab(this), new ToolTipTab(this), new TreeTab(this), };
	}

	/**
	 * Frees the resources
	 */
	void freeResources() {
		if (images != null) {
			for (int i = 0; i < images.length; ++i) {
				final Image image = images[i];
				if (image != null)
					image.dispose();
			}
			images = null;
		}
	}

	/**
	 * Loads the resources
	 */
	void initResources() {
		final Class clazz = ControlExample.class;
		if (ControlExample.resourceBundle != null)
			try {
				if (images == null) {
					images = new Image[ControlExample.imageLocations.length];

					for (int i = 0; i < ControlExample.imageLocations.length; ++i) {
						InputStream sourceStream = clazz.getResourceAsStream(ControlExample.imageLocations[i]);
						ImageData source = new ImageData(sourceStream);
						if (ControlExample.imageTypes[i] == SWT.ICON) {
							ImageData mask = source.getTransparencyMask();
							images[i] = new Image(null, source, mask);
						} else
							images[i] = new Image(null, source);
						try {
							sourceStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return;
			} catch (Throwable t) {
			}
			String error = ControlExample.resourceBundle != null ? ControlExample
					.getResourceString("error.CouldNotLoadResources") : "Unable to load resources"; //$NON-NLS-1$
					freeResources();
					throw new RuntimeException(error);
	}

	/**
	 * @return the tabFolder
	 */
	public TabFolder getTabFolder() {
		return tabFolder;
	}
}
