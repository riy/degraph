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

### Getting Started ### 

### The Configuration File Format ###

### Working with yed ###

### Manipulating Dependencies based on Degraph ###

## Testing of Dependencies ##

### Getting Started ###

### Scala Constraints DSL ###

### Java Constraints DSL ###

## A little Theory ##

## Nomenclature

## Ressources ##

### Feedback ###

### Sources ###

### Homepage ### 