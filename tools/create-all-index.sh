#!/bin/bash

cd ~/to-upload/galileo
create_index_html.rb > index.html

cd ~/to-upload/galileo/coverage/
create_index_html.rb > index.html

cd ~/to-upload/galileo/junit/
create_index_html.rb > index.html


cd ~/to-upload/ganymede
create_index_html.rb > index.html

cd ~/to-upload/ganymede/coverage/
create_index_html.rb > index.html

cd ~/to-upload/ganymede/junit/
create_index_html.rb > index.html