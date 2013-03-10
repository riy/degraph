**Degraph** is a tool for visualizing class and package dependencies in JVM applications.

It analyses class files using [DependencyFinder](http://depfind.sourceforge.net/) and spits out a graphml file which then can be rendered using [yed](http://www.yworks.com/en/products_yed_about.html).

What makes Degraph different from other tools (that I know of) is that it 
supports nested graphs. Inner classes are visualy contained in their containing class; Classes are contained inside packages and if you want you can group packages to modules, layers and so on. These ways of grouping classes are referred to __slices__ in Degraph. 

If you do a hierarchic layout in yed for the resulting graphml file you can easily see which classes you can move 
to different packages, layer or modules without creating circular dependencies or 
which you have to move in order to break cycles. 

## How to run ##

Degraph is a command line tool. Just download the **[binary distribution of Degraph](http://schauder.github.com/degraph//download/degraph-0.0.3.zip)**
and execute *degraph* or *degraph.bat* depending on the operating system you are on.

You'll have to provide at least a class path to analyze. These are the command line arguments available right now:

*-c* <directoryOrFileNameList> This is the classpath to be analyzed. It can be a jar file or a directory or a list of those. If you use a list you have to use the operating system specifc classpath seperator.

*-i* <regex> This is a regular expression to limit the classes to be inlcuded in the graph. Note that the regular expression only has to match part of the name. So a value of 'Test' (without the quotes) will match all classes containing the part 'Test' in their name. If not provided, all classes will make it into the graph by default. Including those that are referenced by the analyzed classes.

*-e* <regex> everything matched by this regular expression will be excluded from the graph.

*-o* <filename> The name for the outputfile. Defaults to output.graphml

*-f* <configuration file> takes the path of a configuration file. When a configuration file is provided all other command line arguments get ignored. Configuration files allow to specify the arguments described above, plus they allow for slicing your code base, i.e. you can define modules, layers or other 'parts' of your application that you want to viusalize in the dependency graph. See the description of the [configuration file format](https://github.com/schauder/degraph/wiki/Configuration-File-Format) for details.

## How do the results look like? ##
Take a look at the [examples page](https://github.com/schauder/degraph/wiki/Examples) to get an impression of how the results might look and the effect of specifc slicings.

## Vision ##

While currently Degraph only visualizes dependencies, I plan to add the possibility to define allowed and disallowed dependencies, plus an easy way to integrate it into various test frameworks. Failures should then create a diagram of the violation found, therby making fixing the problem easier.

If you have any ideas what Degraph should be able to do, just open an [issue](https://github.com/schauder/degraph/issues).

## How to build ##

I'm highly interested in contributions, so if you are interested, drop me a message and until I respond have a look at the source code.

Degraph uses gradle as a build tool. Since gradle is awesome you actually don't have to install gradle, you just need a working JDK installation.

Just get the sourcecode and run one of the following commands:

To execute all the tests:

    gradlew test 

To create a directory that looks like you just installed Degraph:

    gradlew installApp

## Download ##

[Binary distribution of Degraph](http://schauder.github.com/degraph//download/degraph-0.0.3.zip)

## Feedback ##

Please use [the github issue feature](https://github.com/schauder/degraph/issues) for questions, bug reports or improvement requests. 

If you like Degraph just say so on twitter, facebook or wherever you like. 
