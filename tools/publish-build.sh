#!/bin/bash
cd /home/data/httpd/download.eclipse.org/technology/swtbot

if [ ! -s 'galileo/dev-build/version.txt' ]; then
	echo "Could not detect version of swtbot currently deployed."
	exit -1
fi

CURRENT_VERSION_ON_DOWNLOAD_SITE=`cat galileo/dev-build/version.txt | sed -e 's/-e[0-9][0-9]$//'`

rm -rf archives/$CURRENT_VERSION_ON_DOWNLOAD_SITE
mkdir -p archives/$CURRENT_VERSION_ON_DOWNLOAD_SITE
rm -rf new
mkdir new
cp -rf ~/to-upload/* new 
cp -rf /opt/public/download-staging.priv/technology/swtbot/signed/* new 
mv galileo ganymede helios archives/$CURRENT_VERSION_ON_DOWNLOAD_SITE
mv new/* .

cd /home/data/httpd/download.eclipse.org/technology/swtbot/archives
create_index_html.rb > index.html

cd $CURRENT_VERSION_ON_DOWNLOAD_SITE
create_index_html.rb > index.html

