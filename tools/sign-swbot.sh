#!/bin/bash
#set -e
#set -x

# clean all dirs
rm -rf /opt/public/download-staging.priv/technology/swtbot/signed
rm -rf /opt/public/download-staging.priv/technology/swtbot/to-sign

# make the signing dirs
mkdir -p /opt/public/download-staging.priv/technology/swtbot/to-sign

# make the galileo dirs
mkdir -p /opt/public/download-staging.priv/technology/swtbot/signed/galileo

# make the ganymede dirs
mkdir -p /opt/public/download-staging.priv/technology/swtbot/signed/ganymede

# copy all the binaries
cp -fr ~/to-upload/* /opt/public/download-staging.priv/technology/swtbot/to-sign

cd /opt/public/download-staging.priv/technology/swtbot/
# create update site structure
#mkdir -p \
#	galileo/dev-build/signed/update-site \
#	galileo/dev-build/signed/update-site/plugins \
#	galileo/dev-build/signed/update-site/features \
#	ganymede/dev-build/signed/update-site \
#	ganymede/dev-build/signed/update-site/plugins \
#	ganymede/dev-build/signed/update-site/features

cd /opt/public/download-staging.priv/technology/swtbot/to-sign

find * -type f -iname "*.zip" > files.unsort
find * -type f -iname "*.jar" >> files.unsort
sort files.unsort > files

cat files | while read LINE; do
	sign `pwd`/$LINE mail /opt/public/download-staging.priv/technology/swtbot/signed/`dirname $LINE`
done

echo "Signing is in progres run 'monitor.sh' to monitor progress..."