System Requirements
# Ant 1.7 or higher (http://ant.apache.org)
# JDK 1.4 or above (http://java.sun.com)
# Info-Zip zip and unzip executables on system path. Tar for *nix distributions (http://www.info-zip.org/)
# A minimum of 1GB of free disk space

To build SWTBot from source you need to download all code from SVN using the following command.

$ svn co http://dev.eclipse.org/svnroot/technology/org.eclipse.swtbot/trunk swtbot

Then you need to download the following from the Eclipse download site (http://download.eclipse.org/eclipse/downloads/ or http://archive.eclipse.org/eclipse/downloads/index.php for archives)

    * Eclipse SDK 3.3 or higher

Copy over the eclipse-SDK-x.y.tar.gz (or eclipse-SDK-x.y.zip) to swtbot/org.eclipse.swtbot.releng/externals

$ cp /path/to/eclipse-SDK-x.y.tar.gz swtbot/org.eclipse.swtbot.releng/externals

Change directory to org.eclipse.swtbot.releng

$ cd swtbot/org.eclipse.swtbot.releng

Customize your build.developer.properties (use build.developer.properties.sample as your starting point)

# see http://archive.eclipse.org/eclipse/downloads/drops/R-3.3-200706251500/srcIncludedBuildInstructions.html#build_platforms for valid values
os = win32
ws = win32
arch = x86
eclipse.sdk.archive = eclipse-SDK-3.3-win32.zip
eclipse.buildId = 3.3

Extract a target eclipse against which SWTBot will be built.

$ ant materialize-workspace

You can now point your workspace root to the directory where you checked out SWTBot, then import all the swtbot projects. Under Preferences>Target-Platform set the target eclipse to the eclipse directory under the swtbot checkout.

Build swtbot and run all tests:
$ ant

The build artifacts are generated in the org.eclipse.swtbot.releng/artifacts directory.
