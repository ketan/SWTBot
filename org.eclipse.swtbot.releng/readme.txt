To build/run/test SWTBot from source you need to do the following:

Checkout code:
 $ svn co http://dev.eclipse.org/svnroot/technology/org.eclipse.swtbot/trunk swtbot
 $ cd swtbot/org.eclipse.swtbot.releng
Edit some properties file for your platform
 $ cp build.developer.properties.sample build.developer.properties
 $ vi build.developer.properties (Change the os, ws arch parameters)
Create a target platform
 $ ant materialize-workspace
Point your workspace root to the directory where you checked out SWTBot, then import all the swtbot projects.
Under Preferences>Target-Platform set the target platform(preferences>Plugin development>target platform) to the eclipse directory under the swtbot checkout.

Build swtbot and run all tests:
$ ant

The build artifacts are generated in the org.eclipse.swtbot.releng/artifacts directory.
