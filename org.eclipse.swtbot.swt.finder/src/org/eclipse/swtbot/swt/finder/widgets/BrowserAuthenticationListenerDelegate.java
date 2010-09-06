/*******************************************************************************
 * Copyright (c) 2010 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swtbot.swt.finder.utils.Credentials;

/**
 * Makes you cringe in your stomach. A workaround for java not being a real programming language. Wrapper for
 * BrowserAuthenticationListener that should only be used when {@link org.eclipse.swt.browser.AuthenticationListener} is
 * available. Requires SWT > v3.5.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 */
class BrowserAuthenticationListenerDelegate {

	private Object	delegate;

	BrowserAuthenticationListenerDelegate() {
		if (SWT.getVersion() >= 3500) {
			try {
				this.delegate = Class.forName("org.eclipse.swtbot.swt.finder.widgets.BrowserAuthenticationListener").newInstance();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		} else {
			Logger log = Logger.getLogger(BrowserAuthenticationListenerDelegate.class);
			log.warn("You are running a version of SWT lower than v3.5. Browser authentication may not be available.");
		}
	}

	Credentials getCredentials() {
		if (this.delegate == null) {
			return null;
		}
		try {
			Method method = delegate.getClass().getMethod("getCredentials");
			return (Credentials) method.invoke(this.delegate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	void setCredentials(Credentials credentials) {
		if (this.delegate == null) {
			return;
		}
		try {
			Method method = delegate.getClass().getMethod("setCredentials", Credentials.class);
			method.invoke(this.delegate, credentials);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	void init(Browser widget) {
		if (this.delegate == null) {
			return;
		}
		try {
			Method method = delegate.getClass().getMethod("init", Browser.class);
			method.invoke(this.delegate, widget);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
