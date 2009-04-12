#!/bin/bash

rm -rf to-upload

rm -rf artifacts target

mkdir to-upload

# make the ganymede binaries
ant materialize-workspace \
	'-Dupdate.site.features.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/ganymede/dev-build/update-site/features' \
	'-Dupdate.site.plugins.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/ganymede/dev-build/update-site/plugins' \
	'-Declipse.sdk.archive=eclipse-SDK-3.4-macosx-carbon.tar.gz' \
	'-Declipse.buildId=3.4' \
	'-Dhas.archives=true'

ant cruise \
	'-Dupdate.site.features.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/ganymede/dev-build/update-site/features' \
	'-Dupdate.site.plugins.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/ganymede/dev-build/update-site/plugins' \
	'-Declipse.sdk.archive=eclipse-SDK-3.4-macosx-carbon.tar.gz' \
	'-Declipse.buildId=3.4' \
	'-Dhas.archives=true'
mv artifacts/to-upload to-upload/ganymede

# make the galileo binaries

rm -rf artifacts target
ant materialize-workspace \
	'-Dupdate.site.features.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/galileo/dev-build/update-site/features' \
	'-Dupdate.site.plugins.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/galileo/dev-build/update-site/plugins' \
	'-Declipse.sdk.archive=eclipse-SDK-3.5M6-macosx-carbon.tar.gz' \
	'-Declipse.buildId=3.5M6' \
	'-Dhas.archives=true'
	
ant cruise \
	'-Dupdate.site.features.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/galileo/dev-build/update-site/features' \
	'-Dupdate.site.plugins.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/galileo/dev-build/update-site/plugins' \
	'-Declipse.sdk.archive=eclipse-SDK-3.5M6-macosx-carbon.tar.gz' \
	'-Declipse.buildId=3.5M6' \
	'-Dhas.archives=true'
mv artifacts/to-upload to-upload/galileo

# rsync --delete-after --partial --progress --archive to-upload build.eclipse.org:
