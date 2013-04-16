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
    classpath = ../lib/degraph-0.0.3.jar
    exclude = java*.**
    exclude = scala.**
    exclude = org.scalatest.**
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
		
Note that there are two simple excludes for filtering out scala and java core libraries, but other libraries show up in the diagram, although they are not part of the analyzed jar, but referenced from the jar.

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

Except for very basic experiments you'll want to specify a configuration file using the **-f** command line argument. This section describes the format of the configuration file.

#### Example File ####
We'll go through the different parts of the configuration file by examining an example, which is also included in the distribution of Degraph 


    output = example1.graphml
    classpath = ../lib/degraph-0.0.3.jar
    exclude = java*.**
    exclude = scala.**
    exclude = org.scalatest.**
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

#### Simple Properties ####

You can provide simple properties for the path to analyze, the name of the file to generate, classes to include or exclude using the syntax

    <property>=<value>

Available properties are:

* output - the file where the resulting graphml file will be created. Relative paths are relative to your current directory when you start degraph.
* classpath - this is the path to analyze. It is a list of files and directories seperated by the classpath separation character of your platform, i.e. `':'` on Unix and `';'` ond windows. All class and jar files found in those directories or subdirectories will get picked up by Degraph.  
* exclude an Ant like pattern of class names to exclude from the analysis
* include an Ant like pattern of class names to include from the analysis. If not specified, all classes (minus any excludes) are inlcuded. There can be an arbitrary number of exclude and includes specified and the order does not matter. 

Each property has to stand on its own line.

#### Slicing ####

You can specify an arbitrary number of slicings through your code base. A slicing is a grouping of classes that in some sense belong to each other. Examples might be: 

* classes belonging to the same library, like _hibernate_, _log4j_ and so on.
* classes belonging to the same module, like _shoppingcart_, _authentication_, _fullfillment_.
* classes belonging to the same layer, like _UI_, _domain_, _persistence_, _restapi_
* classes belonging to your code vs. external stuff: _internal_, _external_

For each slicing you want to apply you add a section like this to the configuration:

    <slicinglabel> = {
	    <list of patterns>
    }

Note that the opening `{` has to be on the same line as the label and `=` sign while the closing `}`  has to be on its own line. Patterns come in two and a half flavors:

*Named patterns* look like this: 

    <name> <pattern>

Every class that is matched by the pattern is part of the slice given by the name. So a pattern of 

    mine de.schauderhaft.** 

will put all classes with a full qualified name starting with `de.schauderhaft.` in a slice named `mine`.

*Simple patterns* look like this:

    <pattern>

or 

    <prefix>(<naming part>)<suffix>

A class matched by this pattern will get added to the slice given by the full `<pattern>` (first case) or by the `<naming part>` in the second case. 

For example this pattern

    *.(*).** 

will put all classes from `org.junit.` in the slice `junit` and all the stuff from `org.hibernate.` in the slice `hibernate`.

All patterns in a slicing definition (i.e. between `{`and `}`) will get tried in order for each class until a match is found. That match defines the slice used for the class.

#### The pattern matching syntax ####

Pattern matching in the definition of slices uses an Ant like syntax for specifying full qualified class names. With `*` standing in for an arbitrary number (0-n) of arbitrary characters, but no dots. `**` matches and arbitrary number (0-n) of arbitrary characters, including dots.

### Working with yed ###

Degraph generates `.graphml` files that are intended to be rendered using [yed](yed.yworks.com). Yed is a general purpose graph editor with strong layout capabilities. Lots of its capabilities aren't needed when working with files from Degraph. So this article describes an efficient way to work with it.

I'll assume you have downloaded and installed yed. So go ahead and start it up.

I also assume you have a graphml file ready. If not, head over to the [getting started section](#visualization_of_dependencies) and learn how to create one.

#### Basic Layout ####

On startup you get various options what to do. Choose 'Open...' and select the graphml file you want to view.

You will probably see a single box. That's ok, don't worry. Degraph doesn't generate any layout information on its own, so all boxes are located at the same position, hiding behind each other. We'll change that in a second. 

Select the menu Layout -> Hierarchical ...

This opens a dialog. Select the following options and leave everything else as it is.

On the General tab

Orientation: Left to Right

On the Edges tab

Routing Style: Polyline

Click on the Dock button. This will put the dialog in the side bar.

Press the green triangle play button.

Voila, you should now see your graph nicely laid out.

#### Unfolding nodes ####

You are now seeing nodes representing slices (either custom slices configured in the configuration file or packages). You can unfold those slices by clicking on the little  + marker in the top left corner. After that you probably want to layout the graph again. That's why we docked the layout dialog. You just have to press the play button once more.
  
#### Overview ####

In the Overview (it's open by default) you can see a tiny version of the complete graph. If you zoom in, in the main window (for example using the scrollwheel) the overview will highlight the area you are looking at in the main view. You can drag that view port around using the mouse in the Overview, allowing you to quickly navigate even a large graph.

#### Neighborhood View ####

Yed offers a couple of context views. For our purpose the must useful one is the Neighborhood View. It might be already docked on the side of the screen. If not, open it up using the menu Windows -> Context Views -> Neighborhood View. You might want to increase it in size.

If you now click on a node or an edge it shows you the predecessor and successor nodes of whatever you selected. Very helpful if you try to prune dependencies from a class.

#### General Tips ####

No matter how sophisticated a layout algorithm is. A graph witho 1000s of nodes and even more edges will look messy. In order to limit the size of graph you have to deal with use the following techniques:

* Collapse nodes which you don't care about in detail

* Remove collapsed nodes which you don't care about at all, this will also delete all contained nodes.

* When changing your code based on what you see in the graph, you'll probably want to look at the same nodes over and over again. Use filters in your configuration file to limit the nodes generated.

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
