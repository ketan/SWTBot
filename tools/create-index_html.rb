#!/usr/bin/env ruby

pwd = Dir.pwd

puts "<html>"

puts "<head><title>Directory index of #{pwd}</title></head>"

puts "<h1>Directory index of #{pwd}</h1>"

puts "<table>"

puts "<tr><td>Name</td><td>Size</td></tr>"
  
Dir.foreach(".") do |file|
  if File.directory?(file)
    puts "<tr><td><a href=\'#{file}\'>#{file}</a></td><td>-</td></tr>"
  else
    puts "<tr><td><a href=\'#{file}\'>#{file}</a></td><td>#{File.size(file)}</td></tr>"
  end
  # puts "<tr><td><a href=#{file}>#{file}</a></td><td>#{File.size(file)}</td></tr>" if !File.directory(file)?
end

puts "</table>"
puts "</html>"

