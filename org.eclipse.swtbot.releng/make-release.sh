#!/bin/bash

rm -rf artifacts target

# usage: build_swtbot [34 ganymede | 35 galileo | 36 helios] 
# e.g.: build_swtbot 36 helios
# e.g.: build_swtbot 35 galileo
# e.g.: build_swtbot 34 ganymede
function build_swtbot(){
	ant materialize-workspace \
		"-Declipse.version=$1" \
		"-Dhas.archives=true"

	ant cruise \
		"-Declipse.version=$1" \
		"-Dhas.archives=true" && rm -rf to-upload/$2 && mkdir -p to-upload/$2 && mv artifacts/to-upload to-upload/$2/dev-build
}

build_swtbot 34 ganymede && build_swtbot 35 galileo && build_swtbot 36 helios

# rsync --delete-after --partial --progress --archive to-upload build.eclipse.org:
