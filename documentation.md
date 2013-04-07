---
---
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

Visualization of Dependencies can be very helpfull when you try to understand the structure (or the absence of such structure) of dependencies in a project. Lets get started right away by analyzing Degraph and it's components itself.

### Getting Started ### 

### The Configuration File Format ###

### Working with yed ###

### Manipulating Dependencies based on Degraph ###

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

### Sources ###

### Homepage ### 