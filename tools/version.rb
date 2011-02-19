#!/usr/bin/env ruby

require 'fileutils'

HERE = File.expand_path('..', __FILE__)

FROM_VERSION=ARGV[0]
TO_VERSION=ARGV[1]

FileUtils.cd "#{HERE}/.." do
  files = `git ls-tree -r master | grep 'MANIFEST.MF\\|feature.xml' | awk '{print $4}'`
  files.each_line do |file|
    file.chomp!
    contents = File.read(file).gsub("#{FROM_VERSION}.qualifier", "#{TO_VERSION}.qualifier")
    File.open(file, 'w') {|f| f.write contents}
  end
end
