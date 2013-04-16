---
---

<link rel="stylesheet" type="text/css" href="stylesheets/jquery.jqzoom.css" />
<style>
    .flowBreak
	{
		clear:both
	}
</style>

# Degraph Manual #

* auto-gen TOC:
{:toc}

## Introduction ##

Degraph is a tool for visualizing and controlling class and package dependencies in JVM applications.

There are two basic usages: You can **analyze classes and create a graphml file** which then can be rendered and interpreted using [yed, a free graph editor](http://yed.yworks.com).

Or you can **define constraints on the dependency** using an internal Scala DSL **and run tests** to prevent violations of your desired dependency structure.

What makes Degraph different from other similar tools is that it supports nested graphs. Inner classes are visualy contained in their containing class; Classes are contained inside packages and if you want you can group packages to modules, layers and so on. These ways of grouping classes are [referred to as slicings in Degraph(#nomenclature).

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

Move your mouse over the images to see enlarged versions of the images

#### The structure of Degraph ####

##### Configuration File #####

The configuration file example1.config might look like this (details may vary with the version of Degraph)

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

Everything after the excludes is the configuration of slicings. See the [documentation of the configuration file format for details](#the_configuration_file_format).

##### Result #####

<a href="images/selfTest.png" class="zoomable" >  
    <img src="images/selfTest_small.png" >  
</a> 
<div class="flowBreak">
</div>

Degraph is rather boring to look at since it is rather small and also has a very clean package structure (after all it is a tool for managing package structure). So lets look at a more interesting example.

#### Overall structure of Log4J ####

Let's have a look at Log4J. Note that although we'll find stuff that I'd consider not clean, Log4J library is one of the most important little gems in the Java universe, and no matter how it looks in the inside, it does its job rather well, and that is the most important thing.

##### Configuration File #####

The configuration file looks like this.

    output = example2.graphml
    classpath = ../lib/log4j-1.2.16.jar
    include = org.apache.log4j.**
    part = {
        org.apache.log4j.(*).**
    }

	It specifies the jar to analyze and only includes stuff from `log4j` itself. So we won't see any `java.lang` stuff it might depend on. Also the configuration consideres the first package part after `org.apache.log4j` as a *part*. This means it will create nodes on that level, containing all the classes in the respective packages and subpackages.
	
##### Result #####

<a href="images/example2.png" class="zoomable" >  
    <img src="images/example2_small.png" >  
</a> 
<div class="flowBreak">
</div>

As you can see the diagram is rather large. If the dependency structure would be cycle free, there would be only arrows going from left to right. But there are many arrows going from right to left, so we have circles. Actually in a current version of Degraph cycles would be colored red and this diagram would be rather colorfull.

Two parts seem to be very important: `helpers` and `spi` there are lots of arrows to and from those nodes. I'd be rather skeptical about a `helpers` package in itself, especially when it has so many dependencies, but look at the two package and their subpackages, by filtering out everything else. 

#### The spi and helpers packages of Log4J ####

##### Configuration File #####

This is basically the same configuration as before, just with the includes fine tuned to only include the `spi` and the `helper` package.

    output = example3.graphml
    classpath = ../lib/log4j-1.2.16.jar
    include = org.apache.log4j.spi.**
    include = org.apache.log4j.helpers.**
    part = {
        org.apache.log4j.(*).**
    }

##### Result #####

<a href="images/example3.png" class="zoomable" >  
    <img src="images/example3_small.png" >  
</a> 
<div class="flowBreak">
</div>
 
For this diagram I expanded all the nodes, to see the details of the cycle between the two packages. What are we looking at? The two outer light green boxes are the parts specified through the configuration file: `spi` and `helpers`. Contained in those parts is only a single package `org.apache.log4j.spi` and `org.apache.log4j.helpers`. Although there is a one to one relationship this doesn't have to be this way with other configurations. The packages nodes visually contain all the classes. There is one special case: the `PatternParser` class has inner classes so it becomes itself a group node containing the nodes for the inner classes.

As one might have guessed the helpers package contains all kinds of stuff. If we look at the dependencies between `spi` and `helpers` we note that all dependencies go from left to right, which is a good sign, because it makes it likely that it is rather easy to break this cycle. Note that we still have a cycle because the arrows are going up and down between the nodes. This is what I call the [Bypass Antipattern of Package Dependencies](http://blog.schauderhaft.de/2013/03/24/dependency-antipatterns-the-bypass/).

If we extract the classes for `helpers` that `spi` depends on into a seperate package we should be able to improve the situation. That would be the classes `LogLog` and `Loader`. But we have to take `OptionConverter` along for the ride, since it is entangled with the other two. Now we can immediatly see that `OptionConverter` depends  on `Configurator` which is again in the `spi` part, which would cause another cycle, so take it into the new package as well, and the result should have one cycle less. 

**This is the power of Degraph: that you can see all the dependencies of all classes in the packages you decided to look at. This enables you to easily identify classes that you can or should move.**

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

<script type='text/javascript' src='//ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js'>
</script>  

<script type='text/javascript' src='javascripts/jquery.jqzoom-core-pack.js'>
</script> 

<script type='text/javascript' >
$(document).ready(function(){  
    $('.zoomable').jqzoom();  
}); 
</script>
