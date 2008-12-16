/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.test.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swtbot.eclipse.finder.test.ui.data.internal.SWTBotTestContentProvider;
import org.eclipse.swtbot.eclipse.finder.test.ui.data.internal.SWTBotTestData;
import org.eclipse.swtbot.eclipse.finder.test.ui.data.internal.SWTBotTestLabelProvider;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * <p>
 * This is a simple view that displays some strings in a view and provides a few actions.
 * </p>
 * <p>
 * Its purpose is for providing a fixed view for testing the SWTBot Eclipse Finder Bundle.
 * </p>
 * 
 * @author Stephen Paulin &lt;paulin [at] spextreme [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTestView extends ViewPart {
	/**
	 * The unique view identifier.
	 */
	public static String	VIEW_ID	= "org.eclipse.swtbot.eclipse.finder.test.ui.views.SWTBotTestView";

	private TableViewer		viewer;
	private Action			iActionTypeAction;
	private Action			doubleClickAction;

	/**
	 * The constructor.
	 */
	public SWTBotTestView() {
		// Do nothing.
	}

	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new SWTBotTestContentProvider());
		viewer.setLabelProvider(new SWTBotTestLabelProvider());
		viewer.setSorter(new ViewerSorter());

		List data = new ArrayList();
		data.add(new SWTBotTestData("Test Data 1"));
		data.add(new SWTBotTestData("Test Data 2"));
		data.add(new SWTBotTestData("Test Data 3"));

		viewer.setInput(data);

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SWTBotTestView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(iActionTypeAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(iActionTypeAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(iActionTypeAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void makeActions() {
		iActionTypeAction = new Action() {
			public void run() {
				showMessage("iAction executed.");
			}
		};
		iActionTypeAction.setText("IAction Type Command");
		iActionTypeAction.setToolTipText("This represents an IAction command.");
		iActionTypeAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Sample View", message);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
