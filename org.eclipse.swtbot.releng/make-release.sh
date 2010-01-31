#!/bin/bash

rm -rf artifacts target

# usage: build_swtbot <eclipse-version> <eclipse-major.minor-version> <eclipse-release-name>
# e.g.: build_swtbot 3.5.1 35 galileo
# e.g.: build_swtbot 3.4 34 ganymede
function build_swtbot(){
	ant materialize-workspace \
		"-Dupdate.site.base.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/$3/dev-build/update-site" \
		"-Declipse.sdk.archive=eclipse-SDK-$1-macosx-carbon.tar.gz" \
		"-Declipse.buildId=$1" \
		"-Dhas.archives=true" \
		"-Declipse.qualifier=$2"

	ant cruise \
		"-Dupdate.site.base.url=http://www.eclipse.org/downloads/download.php?r=1&amp;file=/technology/swtbot/$3/dev-build/update-site" \
		"-Declipse.sdk.archive=eclipse-SDK-$1-macosx-carbon.tar.gz" \
		"-Declipse.buildId=$1" \
		"-Dhas.archives=true" \
		"-Declipse.qualifier=$2" && rm -rf to-upload/$3 && mkdir to-upload/$3 && mv artifacts/to-upload to-upload/$3/dev-build
}

build_swtbot 3.4.2 e34 ganymede && build_swtbot 3.5 e35 galileo && build_swtbot 3.6M4 e36 helios

# rsync --delete-after --partial --progress --archive to-upload build.eclipse.org:
