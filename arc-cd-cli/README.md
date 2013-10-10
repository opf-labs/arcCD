arc-cd-cli
==========

# Introduction

This project provides a command line interface for the [arc-cd-wrap-cdrdao](../arc-cd-wrap-cdrdao) project.  The application is currently tightly tied to a workflow that was developed specifically for archiving the Maltese Music collection, a Hull University project.

The workflow assumes that the user is archiving CDs that are unlikely to have any metadata available online so it demands that:

 * The user creates a <item-id>.info metadata file holding the cd metadata (Title, Artist,and Track Data).
 * That item-id is a five digit, zero padded number (i.e. 1 == 00001).
 * The user places these files in the default archive folder, ~/Music/arcCD.
 * The user metadata lists the same number of tracks as are found on the CD.

If the above all hold, the user can enter the item-id of the CD they wish to archive and the app will:

 * Create a new folder ~/Music/arcCd/<item-id>
 * Extract the disk audio data to ~/Music/arcCd/<item-id>/<item-id>.bin
 * Extract the disk table of contents to ~/Music/arcCd/<item-id>/<item-id>.toc
 * Move the info file from ~/Music/arcCd/<item-id>.info to ~/Music/arcCd/<item-id>/<item-id>.info
 * Generate an MD5 checksum of all of the above files and record them in a manifest file ~/Music/arcCd/<item-id>/<item-id>.man

# Status

This code is still very much in development, with the aim of creating a more generic workflow for Audio CDs based upon the use of CD-TEXT, and CDDB data to replace IDs.

Current tasks are:

* [ ] Implement MD5 checking, to verify manifests.
* [ ] Generation of CUE files using .info files and cdrdao's .toc output.
* [ ] Some top level collection reporting.

## Build

[![Build Status](https://travis-ci.org/openplanets/arcCD.png)](https://travis-ci.org/openplanets/arcCD)
