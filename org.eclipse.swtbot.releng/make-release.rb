#!/usr/bin/env ruby

require 'rubygems'
require 'rake'
require 'fileutils'
require 'optparse'
require 'open-uri'

class Release
  def self.run(args)
    if args.empty?
      puts "Usage: #{$0} VERSION
      VERSION: major.minor.patch"
      exit(-1)
    end
    
    @now = Time.at(`git log -1 --format='%ct'`.to_i).utc
    @version = args.first
    puts "Fetching revision available on the download site..."
    uri = URI.parse("http://download.eclipse.org/technology/swtbot/helios/dev-build/RELEASE_NOTES.txt")
    puts "Reading #{uri}"

    @available_revision = open(uri).readlines.grep(/ rev\(.*\) /).first.gsub(/RELEASE NOTES v(.*) rev\((.*)\) \(.*\)/, '\2').strip

    @current_head = `git log --pretty='%H' -1`.strip

    puts "Revision on the download site: #{@available_revision}"
    puts "Generating revision log since  #{@available_revision} to HEAD(#{@current_head})"

    @revision_log = `git log --reverse --pretty='%h - by %cn on %cd%n%w(80, 4, 4)%B\n' --date=short #{@available_revision}..#{@current_head}`

    @revision_log += "\n\n"
    @revision_log += open(uri).read

    FileUtils.rm_rf('to-upload')
    FileUtils.rm_rf('target')
    
    build_swtbot(35, 'galileo')
    build_swtbot(36, 'helios')
    build_swtbot(37, 'indigo')
  end

  def self.release_notes(dir)
    File.open("#{dir}/RELEASE_NOTES.txt", 'w') do |f|
      title = "RELEASE NOTES v#{@version} rev(#{@current_head[0..6]}) (#{@now.strftime('%b %d, %Y')})"
      f.puts(title)
      f.puts("=" * title.length)
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
    extra_jvm_opts = "-Dextra.jvm.options='#{ENV['JAVA_OPTS']}'" if ENV['JAVA_OPTS']
    sh("ant cruise -Declipse.version=#{version} -Dhas.archives=true #{extra_jvm_opts} -Dnow.now=#{@now.strftime('%Y%m%d_%H%M')}")
    FileUtils.rm_rf("to-upload/#{code_name}")
    FileUtils.mkdir_p("to-upload/#{code_name}")
    FileUtils.mv("artifacts/to-upload", "to-upload/#{code_name}/dev-build")
    release_notes("to-upload/#{code_name}/dev-build")
  end
end

Release.run(ARGV)
