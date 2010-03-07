#!/bin/bash

rm -rf artifacts target

# usage: build_swtbot [34 | 35 | 36]
# e.g.: build_swtbot 35
# e.g.: build_swtbot 34
function build_swtbot(){
	ant materialize-workspace \
		"-Declipse.version=$1" \
		"-Dhas.archives=true"

	ant cruise \
		"-Declipse.version=$1" \
		"-Dhas.archives=true" && rm -rf to-upload/$3 && mkdir to-upload/$3 && mv artifacts/to-upload to-upload/$3/dev-build
}

build_swtbot 34 && build_swtbot 35 && build_swtbot 36

# rsync --delete-after --partial --progress --archive to-upload build.eclipse.org:
