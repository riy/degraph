---
layout: simplePage
title: Degraph Downloads
metaContent: The download page for Degraph, a tool for visualizing and testing package dependencies in JVM code.
---

# Download #

There are two distributions of Degraph available for download. 

The command line application contains everything to run Degraph in order to generate diagrams.
 
If you want to use Degraph as part of your project use the Maven style Jars & pom.xmls you can use them as Maven dependencies

The current version is {{current_version}}. For information what changed between versions, see [the release notes](https://github.com/schauder/degraph/blob/master/releaseNotes.md).

## Command Line Application ##

[Degraph Command Line Application, Version {{current_version}}](http://dl.bintray.com/schauder/schauderhaft-de/degraph-{{current_version}}.zip)

## Maven Style Jars & pom.xmls ##

### check ###

You need this for writing tests for your dependencies with Degraph. It depends on *core*

     <dependency>
       <groupId>de.schauderhaft.degraph</groupId>
       <artifactId>degraph-check</artifactId>
       <version>{{current_version}}</version>
     </dependency>

### core ###

This is the main logic of Degraph. You need it if you want to use the graph manipulation for something completely different. Please be aware that I don't think twice before I change an API right now. If you still want to use it, let me know and I might start thinking about stabilizing the API.

     <dependency>
       <groupId>de.schauderhaft.degraph</groupId>
       <artifactId>degraph-core</artifactId>
       <version>{{current_version}}</version>
     </dependency>

## Older Versions ##


[Degraph Command Line Application, Version 0.1.1](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.1.1.zip)

[Degraph Binary Distribution, Version 0.1.0](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.1.0.zip)

[Degraph Binary Distribution, Version 0.0.4](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.0.4.zip)

[Degraph Binary Distribution, Version 0.0.3](http://dl.bintray.com/schauder/schauderhaft-de/degraph-0.0.3.zip)