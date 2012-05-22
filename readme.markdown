**Dependency Manager** is a little tool for visualizing dependencies in JVM applications.

It analyses class files using DependencyFinder and spits out a graphml file which then can be rendered using [yed](http://www.yworks.com/en/products_yed_about.html).

What makes Dependency Manager different from other tools (I know) is that it supports nested graphs. I.e. you can define rule like a class is contained in it's package, 
a package is part of a module and this fact gets visualized in the resulting graph. 

If you do a hierarchic layout in yed you can easily see which classes you can move to different packages, layer or modules without creating circles on these levels or which you have to move in order to break cycles. A feature I haven't seen in any other package dealing with dependencies so far.

**How to build**

The project currently does not include a build script, but building is straight forward. 
All the sources are structured according to a standard maven structure. 
You'll have to add the following libraries in order to compile and run the main sources:

* DependencyFinder.jar
* guava.jar (included in the DependencyFinder download)
* jakarta-oro.jar (included in the DependencyFinder download)
* log4j.jar

For executing the tests you'll have to add the following classes

For compiling
* junit-4.8.2.jar
* scalatest_2.9.0-1.6.1.jar

**How to run**

Currently the application does not accept commandline arguments. Instead you actually have to modify the source code for analyzing stuff. 
Just open the file DependencyManager.scala and edit the name of the jar file (or class path) to analyze and the name of the output file.