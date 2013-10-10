arc-cd-wrap-cdrdao
==================

# Introduction

This project is a Java wrapper around the command line cdrdao utility.  The wrapper provides:

 * Automatic detection of cdrdao installation.
 * Calling of an executable by path if no installation found.
 * Interfaces and classes for cdrdao entities, e.g. TOC files, track timings, and disk information.
 * A straight forward API for calling cdrdao commands and some options.
 
The code makes sensible assumptions where possible, e.g. it will pick a default drive although methods that allow the caller to find all of the device names and uses a specific drive are provided. In nearly all cases the simplest version of a method requires only the information that MUST be provided to operate, i.e. names for created files.

The project is intended for developers looking to call the cdrdao utility from within Java code, and does NOT currently provide any user interface.  Any interface provided would be developed in it's own Maven project, similar to the command line interface provided by the [arc-cd-cli](../arc-cd-cli) project.

A user might want to use cdrdao as it can create Disk At Once (DAO) copies of audio CD's. These can be used as archive masters of the CD's, from which copies of original CD can be made, or derived audio files can be created (WAVs, MP3s, OGG, etc.).

# Status

The project is still in an initial development phase, currently only a subset of cdrdao functionality can be invoked:

 * scanbus
   Detects CD drives attached to the machine.
 * drive-info
   Returns information about a specific drive.
 * disk-info
   Retrieve information about inserted media.
 * read-toc
   Reads a CD table of contents.
 * read-cd
   Reads the audio data from a CD and create a bin image and toc file.

## API Status

Until end of October 2013, I won't even guarantee that the API methods won't change name, or arguments while I'm refactoring.

## Build

[![Build Status](https://travis-ci.org/openplanets/arcCD.png)](https://travis-ci.org/openplanets/arcCD)
   
# Current Activity

The following tasks are currently under active development

* [ ] Refactoring the code that parses cdrdao output so that the ProcessRunner takes a filter interface. These filters will parse cdrdao output and create the appropriate objects, the filter interface provides a generic "processStream()" method. 
* [ ] A TOC file to CUE file converter, toc2cue sucks metadata wise.
* [ ] Wrapping cdrdao's CDDB functionality to obtain metadata for audio CDs.
 
# Using the project

So the best place to start is the unit tests, in lieu of documentation but DO READ the section below first.  Test coverage is pretty good (to be measured soon), so nearly all classes and methods have tests.  The JavaDoc should also help although class usage documentation could be improved.

## Briefly

The CdrdaoWrapper interface provides the methods that wrap cdrdao functionality, you can obtain an instance from the CdrdaoCliWrapperFactory's static methods as follows:

### If cdrdao is installed

```
CdrdaoCliWrapperFactory.getInstalledInstance()
```
This only works if cdrdao is installed, and throws a checked cdrdao exception if it isn't.  Beware, it's very likely that this method will become newInstalledInstance() very soon.  You can check whether an installation has been detected by calling:
```
CdrdaoCliWrapperFactory.isCdrdaoInstalled()
```
if you prefer code without exception handling.

### If the application has access to a cdrdao executable file
```
CdrdaoCliWrapperFactory.createInstanceFromExecutableFile(File file)
```
This method creates a wrapper using the file object after checking that it's a valid cdrdao executable.

The method
```
CdrdaoCliWrapperFactory.createInstanceFromExecutablePath(String path)
```
is identical in function as the previous method but takes a String path to the cdrdao executable.
  
