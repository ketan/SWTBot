#!/bin/bash

cd /opt/public/download-staging.priv/technology/swtbot/signed
while true; do
	find * -type f -iname "*.zip" > files.unsort
	find * -type f -iname "*.jar" >> files.unsort
	sort files.unsort > files
	
	diff files ../to-sign/files > diff

	cat diff
	if [ -s 'diff' ]; then
		echo 'Still more to sign...'
	else
		echo 'Done signing...'
		break
	fi
	sleep 5
done

rm files files.unsort diff
