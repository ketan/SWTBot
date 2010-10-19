#!/bin/bash

cd ~/to-upload/galileo/
create_index_html.rb > index.html

cd ~/to-upload/galileo/dev-build
create_index_html.rb > index.html

cd ~/to-upload/galileo/dev-build/coverage/
create_index_html.rb > index.html

cd ~/to-upload/galileo/dev-build/junit/
create_index_html.rb > index.html


cd ~/to-upload/ganymede/
create_index_html.rb > index.html

cd ~/to-upload/ganymede/dev-build/
create_index_html.rb > index.html

cd ~/to-upload/ganymede/dev-build/coverage/
create_index_html.rb > index.html

cd ~/to-upload/ganymede/dev-build/junit/
create_index_html.rb > index.html


cd ~/to-upload/helios/
create_index_html.rb > index.html

cd ~/to-upload/helios/dev-build/
create_index_html.rb > index.html

cd ~/to-upload/helios/dev-build/coverage/
create_index_html.rb > index.html

cd ~/to-upload/helios/dev-build/junit/
create_index_html.rb > index.html
