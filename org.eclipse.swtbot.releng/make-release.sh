#!/bin/bash

rm -rf to-upload artifacts target

mkdir to-upload

# usage: build_swtbot 3.5M6 galileo
function build_swtbot(){
	ant materialize-workspace \
		"-Dupdate.site.features.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/$2/dev-build/update-site/features" \
		"-Dupdate.site.plugins.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/$2/dev-build/update-site/plugins" \
		"-Declipse.sdk.archive=eclipse-SDK-$1-macosx-carbon.tar.gz" \
		"-Declipse.buildId=$1" \
		"-Dhas.archives=true"

	ant cruise \
		"-Dupdate.site.features.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/$2/dev-build/update-site/features" \
		"-Dupdate.site.plugins.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/$2/dev-build/update-site/plugins" \
		"-Declipse.sdk.archive=eclipse-SDK-$1-macosx-carbon.tar.gz" \
		"-Declipse.buildId=$1" \
		"-Dhas.archives=true"

	rm -rf to-upload/$2
	mkdir to-upload/$2
	mv artifacts/to-upload to-upload/$2/dev-build
}

build_swtbot 3.4.2 ganymede
build_swtbot 3.5M6 galileo

# rsync --delete-after --partial --progress --archive to-upload build.eclipse.org:
