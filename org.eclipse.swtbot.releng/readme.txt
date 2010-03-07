To build/run/test SWTBot from source and command-line you need to do the following:

Install Subversion Command-Line Client, Ant and Info-ZIP. Add them into your environment's command execution path.
Verify that you can execute these commands on your command-line: svn, ant, unzip.

Checkout code:
 $ svn co http://dev.eclipse.org/svnroot/technology/org.eclipse.swtbot/trunk swtbot
 $ cd swtbot/org.eclipse.swtbot.releng

Edit some properties file for your platform:
 $ cp build.developer.properties.sample build.developer.properties
 $ vi build.developer.properties (Change the os, ws, arch and eclipse.version parameters)

Copy the Eclipse SDK archive which you want to use into: /org.eclipse.swtbot.releng/externals

Create a target platform:
 $ ant materialize-workspace

Build swtbot and run all tests:
 $ ant

The build artifacts are generated in the org.eclipse.swtbot.releng/artifacts directory.


To develop SWTBot in in Eclipse IDE you need to do do the following:

Point your workspace root to the directory where you checked out SWTBot, then import all the swtbot projects.

Under Preferences>Target-Platform set the target platform(preferences>Plugin development>target platform) to the eclipse directory under the swtbot checkout.
