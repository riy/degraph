---
---

**Degraph** is a tool for controling and visualizing class and package dependencies in JVM applications.

## Degraph for visualization##

You can  analyse class files and jars using Degraph and get a graphml file as result. This can be rendered using [yed](http://www.yworks.com/en/products_yed_about.html).

What makes Degraph different from other tools is that it 
supports nested graphs. Inner classes are visualy contained in their containing class; Classes are contained inside packages and if you want you can group packages to modules, layers and so on. These ways of grouping classes are referred to __slices__ in Degraph. 

If you do a hierarchic layout in yed of the resulting graphml file you can easily see which classes you can move 
to different packages, layer or modules without creating circular dependencies or 
which you have to move in order to break cycles.

Read more ...

## Degraph for controlling dependencies ##

Ever wanted to establish a rule in a project like "stuff from the presentation layer must not access stuff from the persistence layer!"? Now you can. Based on a simple DSL you can write tests that check rules like this and fail with a list of the violations found.

Since these are normal tests based on mainstream test frameworks they are easily integrated into Continuous Integration builds.

Read more ...

## Download ##

To get started download the  **[current distribution of Degraph](http://schauder.github.com/degraph//download/degraph-0.0.3.zip)** and follow the steps in [Getting Started with Visualization] or [Getting Started with Tests]
