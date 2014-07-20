---
layout: simplePage
title: Degraph Downloads
metaContent: The download page for Degraph, a tool for visualizing and testing package dependencies in JVM code.
---

# Download #

There are two distributions of Degraph available for download. 

The command line application contains everything to run Degraph in order to generate diagrams.
 
If you want to use Degraph as part of your project use the Maven Style Jars & pom.xmls you can use them as Maven dependencies

The current version is 0.1.0. For information what changed between versions, see [the release notes](https://github.com/schauder/degraph/blob/master/releaseNotes.md).

## Command Line Application ##

[Degraph Command Line Application, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.1.0.zip)

## Maven Style Jars & pom.xmls ##

I didn't manage to make Degraph available via a proper Maven repository. You can use the following downloads to set up Degraph in your repository. 

### check ###

You need this for writing tests for your dependencies with Degraph. It depends on *core*

[Degraph Maven Style stuff *check-jar*, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/check-0.1.0.jar)

[Degraph Maven Style stuff *check-pom*, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/check-0.1.0.pom)

### core ###

This is the main logic of Degraph. You need it as a dependency for the other stuff and if you want to use the graph manipulation for something completely different. Please be aware that I don't think twice before I change an API right now. If you still want to use it, let me know and I might start thinking about stabilizing the API.

[Degraph Maven Style stuff *core-jar*, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/core-0.1.0.jar)

[Degraph Maven Style stuff *core-pom*, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/core-0.1.0.pom)

### degraph ###

This is the command line application. It depends on core. No idea why somebody would want to include it as a dependency but it's there.

[Degraph Maven Style stuff *degraph-jar*, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.1.0.jar)

[Degraph Maven Style stuff *degraph-pom*, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.1.0.pom)

## Older Versions ##

[Degraph Binary Distribution, Version 0.0.4](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.0.4.zip)

[Degraph Binary Distribution, Version 0.0.3](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.0.3.zip)