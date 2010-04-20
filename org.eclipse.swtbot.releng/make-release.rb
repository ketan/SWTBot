#!/usr/bin/env ruby

require 'rubygems'
require 'rake'
require 'fileutils'
require 'optparse'
require 'open-uri'

class Release
  def self.run(args)
    puts "Fetching revision available on the download site..."
    uri = URI.parse("http://download.eclipse.org/technology/swtbot/ganymede/dev-build/RELEASE_NOTES.txt")
    puts "Reading #{uri}"

    open(uri) do |f|
      f.each_line do |line|
        if line =~ /Revision:/
          @available_revision = line.gsub(/Revision:\s*/, '').strip
        end
      end
    end

    @current_head = `git log --pretty='%H' -1`.strip

    puts "Revision on the download site: #{@available_revision}"
    puts "Generating revision log since  #{@available_revision} to HEAD(#{@current_head})"

    @revision_log = `git log --pretty='%h - by %cn on %cd%n%s%n%b%n' --date=short #{@available_revision}..#{@current_head}`
    
    @revision_log.gsub!(/.*git-svn-id:.*@(\d+).*/, '  svn-revision: \1')
    @revision_log = @revision_log.gsub(/\t/,"     ").gsub(/.{1,72}(?:\s|\Z)/){($& + 5.chr).gsub(/\n\005/,"\n  ").gsub(/\005/,"\n  ")}

    FileUtils.rm_rf('to-upload')
    FileUtils.rm_rf('target')

    build_swtbot(34, 'ganymede')
    build_swtbot(35, 'ganymede')
    build_swtbot(36, 'ganymede')
  end

  def self.release_notes(dir)
    File.open("#{dir}/RELEASE_NOTES.txt", 'w') do |f|
      f.puts("RELEASE NOTES")
      f.puts("=============")
      f.puts("")
      f.puts("="*"Revision: #{@current_head}".length)
      f.puts("Revision: #{@current_head}")
      f.puts("="*"Revision: #{@current_head}".length)
      f.puts("")
      f.puts(@revision_log)
    end
  end

  #version=34, code_name=ganymede
  #version=35, code_name=galileo
  #version=36, code_name=helios
  def self.build_swtbot(version, code_name)

    FileUtils.rm_rf("to-upload/#{code_name}")
    FileUtils.mkdir_p("to-upload/#{code_name}/dev-build")
    release_notes("to-upload/#{code_name}/dev-build")

    sh("ant materialize-workspace -Declipse.version=#{version} -Dhas.archives=true")
    sh("ant cruise -Declipse.version=#{version} -Dhas.archives=true")
    FileUtils.rm_rf("to-upload/#{code_name}")
    FileUtils.mkdir_p("to-upload/#{code_name}")
    FileUtils.mv("artifacts/to-upload", "to-upload/#{code_name}/dev-build")
    release_notes("to-upload/#{code_name}/dev-build")
  end
end

Release.run(ARGV)
