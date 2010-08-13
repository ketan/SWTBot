/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Libor Zoubek, Red Hat - initial API and implementation
 *     Ketan Padegaonkar - cleanup to conform to SWTBot standards
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import org.eclipse.swt.browser.AuthenticationEvent;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swtbot.swt.finder.ReferenceBy;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.Credentials;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.hamcrest.SelfDescribing;

/**
 * This represents a {@link Browser} widget.
 * 
 * @author Libor Zoubek &lt;lzoubek [at] redhat [dot] com&gt;
 */
@SWTBotWidget(clasz = Browser.class, preferredName = "browser", referenceBy = { ReferenceBy.LABEL })
public class SWTBotBrowser extends AbstractSWTBotControl<Browser> {

	private final InternalProgressListener				progressListener;
	private final static BrowserAuthenticationListener	authListener	= new BrowserAuthenticationListener();

	/**
	 * Constructs an instance of this object with the given browser
	 * 
	 * @param browser the widget.
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotBrowser(Browser browser) {
		this(browser, null);
	}

	/**
	 * Constructs an instance of this object with the given browser
	 * 
	 * @param browser the widget.
	 * @param description the description of the widget, this will be reported by {@link #toString()}
	 * @throws WidgetNotFoundException if the widget is <code>null</code> or widget has been disposed.
	 * @since 2.0
	 */
	public SWTBotBrowser(Browser browser, SelfDescribing description) {
		super(browser, description);
		progressListener = new InternalProgressListener(this);
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				widget.addAuthenticationListener(authListener);
			}
		});
	}

	/**
	 * Loads given URI into browser, the page is loaded asynchronously (see {@link #isPageLoaded()})
	 * 
	 * @param url
	 */
	public void setUrl(final String url) {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				progressListener.setDone(false);
				widget.addProgressListener(progressListener);
				boolean result = widget.setUrl(url);
				if (!result) {
					progressListener.setDone(true);
					widget.removeProgressListener(progressListener);
				}
			}
		});
	}

	/**
	 * @return the current URL or an empty String if there is no current URL
	 */
	public String getUrl() {
		waitForPageLoaded();
		return UIThreadRunnable.syncExec(new Result<String>() {
			public String run() {
				return widget.getUrl();
			}
		});
	}

	@Override
	public String getText() {
		waitForPageLoaded();
		return super.getText();
	}

	/**
	 * Executes script in browser asynchronously
	 * 
	 * @param script
	 */
	public void execute(final String script) {
		waitForPageLoaded();
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				widget.execute(script);
			}
		});
	}

	/**
	 * @return the receiver's back command enabled state
	 */
	public boolean isBackEnabled() {
		return UIThreadRunnable.syncExec(new Result<Boolean>() {
			public Boolean run() {
				return widget.isBackEnabled();
			}
		});
	}

	/**
	 * @return the receiver's forward command enabled state
	 */
	public boolean isForwardEnabled() {
		return UIThreadRunnable.syncExec(new Result<Boolean>() {
			public Boolean run() {
				return widget.isForwardEnabled();
			}
		});
	}

	/**
	 * Navigate to the previous session history item.
	 */
	public void back() {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				progressListener.setDone(false);
				widget.addProgressListener(progressListener);
				boolean result = widget.back();
				if (!result) {
					progressListener.setDone(true);
					widget.removeProgressListener(progressListener);
				}
			}
		});

	}

	/**
	 * Navigate to the next session history item.
	 */
	public void forward() {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				progressListener.setDone(false);
				widget.addProgressListener(progressListener);
				boolean result = widget.forward();
				if (!result) {
					progressListener.setDone(true);
					widget.removeProgressListener(progressListener);
				}
			}
		});

	}

	/**
	 * Refreshes browser
	 */
	public void refresh() {
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				widget.refresh();
			}
		});
	}

	/**
	 * @return <code>true</code> by default or when page was completely loaded by browser after asynchronous page load
	 *         invoked by {@link #goURL(String)} was finished, this method returns false only during page loading
	 */
	public boolean isPageLoaded() {
		return progressListener.isDone();
	}

	/**
	 * Waits until browser loads page.
	 * 
	 * @throws TimeoutException if page is not loaded after default timeout
	 */
	public void waitForPageLoaded() {
		new SWTBot().waitUntil(new WaitForBrowserLoadsPage(this));
	}

	/**
	 * Sets credentials, which will be used when page requires authentication, if both username and password set to null
	 * authentication (if requested) will be canceled
	 * <p>
	 * <b>Note:</b> Credentials are shared by all {@link SWTBotBrowser} instances.
	 * </p>
	 * instances
	 * 
	 * @param username the username
	 * @param password the password
	 */
	public void setCredentials(String username, String password) {
		setCredentials(new Credentials(username, password));
	}

	/**
	 * Sets credentials, which will be used when page requires authentication, if both username and password set to null
	 * authentication (if requested) will be canceled
	 * <p>
	 * <b>Note:</b> Credentials are shared by all {@link SWTBotBrowser} instances.
	 * </p>
	 * instances
	 * 
	 * @param credentials the credentials with the username and password
	 */
	public void setCredentials(Credentials credentials) {
		authListener.setCredentials(credentials);
	}

	/**
	 * Gets credentials.
	 * 
	 * @return the credentials containing the username and password.
	 */
	public Credentials getCredentials() {
		return authListener.getCredentials();
	}

	/**
	 * This represents internal progress listener notified when browser finishes loading of URL
	 * 
	 * @author Libor Zoubek &lt;lzoubek [at] redhat [dot] com&gt;
	 */
	class InternalProgressListener implements ProgressListener {
		private final SWTBotBrowser	browser;
		private boolean				done	= true;

		public InternalProgressListener(SWTBotBrowser browser) {
			this.browser = browser;
		}

		public synchronized boolean isDone() {
			return done;
		}

		public synchronized void setDone(boolean done) {
			this.done = done;
		}

		public void changed(ProgressEvent event) {
		}

		public void completed(ProgressEvent event) {
			setDone(true);
			browser.widget.removeProgressListener(this);
		}

	}

	private static final class BrowserAuthenticationListener implements AuthenticationListener {
		private Credentials	credentials;

		public void setCredentials(Credentials credentials) {
			this.credentials = credentials;
		}

		public Credentials getCredentials() {
			return this.credentials;
		}

		public void authenticate(AuthenticationEvent event) {
			if (credentials == null) {
				event.doit = false;
				return;
			}
			event.doit = true;
			event.user = credentials.username();
			event.password = credentials.password();
		}

	}

	private static final class WaitForBrowserLoadsPage extends DefaultCondition {

		private final SWTBotBrowser	browser;

		public WaitForBrowserLoadsPage(SWTBotBrowser browser) {
			Assert.isNotNull(browser, "The browser can not be null"); //$NON-NLS-1$		
			this.browser = browser;
		}

		public String getFailureMessage() {
			return "Browser dit not finish loading page before timeout."; //$NON-NLS-1$	
		}

		public boolean test() throws Exception {
			return browser.isPageLoaded();
		}

	}

}
