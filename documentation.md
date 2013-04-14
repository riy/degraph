---
---

<link rel="stylesheet" type="text/css" href="css/jquery.jqzoom.css" />

# Degraph Manual #

* auto-gen TOC:
{:toc}

## Introduction ##

Degraph is a tool for visualizing and controlling class and package dependencies in JVM applications.

There are two basic usages: You can **analyze classes and create a graphml file** which then can be rendered and interpreted using yed, a free graph editor.

Or you can define constraints on the dependency using an internal Scala DSL an run tests to prevent violations of your desired dependency structure.

What makes Degraph different from other similar tools is that it supports nested graphs. Inner classes are visualy contained in their containing class; Classes are contained inside packages and if you want you can group packages to modules, layers and so on. These ways of grouping classes are referred to slices in Degraph.

If you do a hierarchic layout in yed for the resulting graphml file you can easily see which classes you can move to different packages, layer or modules without creating circular dependencies or which you have to move in order to break cycles.

## Visualization of Dependencies ##

Visualization of Dependencies can be very helpfull when you try to understand the structure (or the absence of such structure) of dependencies in a project. Lets get started right away by analyzing Degraph and some of it's components itself.

### Getting Started ### 
The following images are created (and can be recreated by yourself) by 

* downloading degraph
* unzip to a directory ( _theDir_ )
* open a console and change into _theDir_/degraph/bin
* execute `degraph -f ../example/example#.config` with # replace by one of the digits 1, 2 or 3
* open the resulting `example#.graphml' in yed, opening up some of the nodes and applying an hierarchic layout. 

If you open the images in a separate tab you can see an even larger version, where you can read all the labels.

#### The structure of Degraph ####

##### Configuration File #####

    output = example1.graphml
    classpath = ../lib/degraph-0.0.4.jar
    exclude = java
    exclude = scala
    part = {
        de.schauderhaft.*.(*).**
    }
    lib = {
	de.schauderhaft.(*).**
	*.(*).**
    }
    internalExternal = {
        internal de.schauderhaft.**
        external **
    }

Note that there are two simple excludes for filtering out scala and java core libraries, but other libraries show up in the diagram, although they are not part of the analyzed jar, but referenced from the jar

There is a separate [wiki page explaining the configuration file format](https://github.com/schauder/degraph/wiki/Configuration-File-Format)

##### Result #####

<a href="images/selfTest.png" class="zoomable" title="Dependency Structure of Degraph itself (large)">  
    <img src="images/selfTest_small.png" title="Dependency Structure of Degraph itself (small)">  
</a> 


Degraph is rather boring to look at since it is rather small and also has a very clean package structure (after all it is a tool for managing package structure). So lets look at a more interesting example.

#### Overall structure of Log4J ####

##### Configuration File #####

    output = example2.graphml
    classpath = ../lib/log4j-1.2.16.jar
    include = log4j
    part = {
        org.apache.log4j.(*).**
    }

##### Result #####

![Diagram of the structure of Log4J as created by Degraph](http://blog.schauderhaft.de/wp-content/uploads/2013/02/example2.png)

As you can see the diagram is rather large. But two parts seem to be very important: `helpers` and `spi` there are lots of arrows to and from those nodes. So lets have a closer look at those, by filtering out everything else

#### The spi and helpers packages of Log4J ####

##### Configuration File #####

    output = example3.graphml
    classpath = ../lib/log4j-1.2.16.jar
    include = log4j.spi
    include = log4j.helpers
    part = {
        org.apache.log4j.(*).**
    }

##### Result #####

![Diagram of the structure of the spi and helpers packages of Log4J as created by Degraph](http://blog.schauderhaft.de/wp-content/uploads/2013/02/example3.png)
 
For this diagram I expanded all the nodes, to see the details of the cycle between the two packages.

As one might have guessed the helpers package contains all kinds of stuff. If this get separated into specific packages one can easily reduce the cycles between the two packages. For example `LogLog` seems to be some basis implementation or interface used by the the `spi` while `OnlyOnceErrorHandler`, `QuietWriter`, `SyslogQuietWriter` and `CountingQuietWriter`seem to be some implementations of an interface provided by `spi`. It might be a good idea to move these two things into two more specific packages. 

### The Configuration File Format ###

### Working with yed ###

## Testing of Dependencies ##

### Getting Started ###

### Scala Constraints DSL ###

### Java Constraints DSL ###

## Stuff ##
### Installation ###

### FAQ ###

#### The resulting Graph is empty ####

Make sure you specify the correct path to the **class**-files. Source files don't work. Paths that don't contain class-files (nor jar files, containing class files) will be silently ignored. So a typo in the path specified easily results in a empty graphml file.

#### Analyzing my classes takes for ever / yed takes for ever to load or display my graphml file ####

Given a path Degraph analyzes everything that looks like a class file in that directory or in subdirectories, including jar files. So if you use the root directory of a large multi module project in standard maven layout, it will analyze all the class files of the project (test and main). If have jars in there, those will get analyzed as well.

Make sure you have only those classes / jars in the path given to Degraph, that you are really interested in. Make use of the filter options to limit the classes that actually end up in the graph.

#### Degraph show some circular dependencies but not all ####

The truth is: Degraph currently shows only a single circular dependency per slicetype. If you fix that circular dependency Degraph might find the next one. This is obviously not nice and will be fixed in one of the upcoming versions.

### A little Theory ###

### Nomenclature ###

## Ressources ##

### Feedback ###
If you have a problem using Degraph, a question, a bug or an idea: Go ahead and create an issue at [Degraphs issue tracker](https://github.com/schauder/degraph/issues?state=open).

### Sources ###
You can find the source code to [Degraph at github](https://github.com/schauder/degraph).

### Keep Up To Date ### 

Want to kept updated about news about Degraph? Consider subscribing to the [blog of the author](http://blog.schauderhaft.de), or keep an eye on the [homepage of Degraph](http:/schauder.github.com/degraph/).

Don't want to miss the next release? Follow the [author on twitter](http://www.twitter.com/jensschauder).


<!-- scripts -->

<script type='text/javascript' src='//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js'></script>  

<script type='text/javascript' src='javascripts/jquery.jqzoom-core-pack.js'></script> 

<script type='text/javascript' >
$(document).ready(function(){  
    $('zoomable').jqzoom();  
}); 
</script>
