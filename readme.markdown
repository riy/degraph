**Degraph** is a tool for visualizing dependencies in JVM applications.

It analyses class files using [DependencyFinder](http://depfind.sourceforge.net/) and spits out a graphml file which then can be rendered using [yed](http://www.yworks.com/en/products_yed_about.html).

What makes Degraph different from other tools (that I know of) is that it 
supports nested graphs. Inner classes are visualy contained in their containing class. 

Classes are contained in their packages, and you can specify rules how to group packages into modules. 

If you do a hierarchic layout in yed you can easily see which classes you can move 
to different packages, layer or modules without creating circular dependencies or 
which you have to move in order to break cycles. 
A feature I haven't seen in any other package dealing with dependencies so far.


**How to run**

Currently Degraph is just a simple command line tool. Just download the **[binary distribution of Degraph](http://blog.schauderhaft.de/wp-content/uploads/2012/12/degraph-0.0.1.zip)**
and execute *degraph* or *degraph.bat* depending on the operating system you are on.

You'll at least provide a class path to analyze. These are the command line arguments available right now:

*-c* <directoryOrFileNameList> This is the classpath to be analyzed. It can be a jar file or a directory or a list of those. If you use a list you have to use the operating system specifc classpath seperator.

*-i* <regex> This is a regular expression to limit the classes to be inlcuded in the graph. Note that the regular expression only has to match part of the name. So a value of 'Test' (without the quotes) will match all classes containing the part 'Test' in their name. If not provided, all classes will make it into the graph by default.

*-e* <regex> everything matched by this regular expression will be excluded from the graph.

*-o* <filename> The name for the outputfile. Defaults to output.graphml

*-g* <list of groups> This is a list of regular expressions that define the module structure of the application. The idea is that the first match of the regular expression becomes the name of the category/module. For example the argument '-g (.*)\..*' will cause each upper level package to group its contained packages (Note: you might have to escape parts of the regex to make it work). Doesn't make sense? Try it with and without the argument, I think you'll understand the difference. Note: This one is even more experimental then everything else and will probably soon be replaced by some more powerfull configuration file.

[There is a page with examples in the wiki](https://github.com/schauder/degraph/wiki/Examples).

**Vision**

While currently Degraph only visualizes dependencies, I plan to add the possibility to define allowed and disallowed dependencies, plus an easy way to integrate it into various test frameworks. Failures should then create a diagram of the violation found, therby making fixing the problem easier.

**How to build**

I'm highly interested in contributions, so if you are interested, drop me a message and until I respond have a look at the source code.

Degraph uses gradle as a build tool. Since gradle is awesome you actually don't have to install gradle, you just need a working JDK installation.

Just get the sourcecode and run one of the following commands:

To execute all the tests:

    gradlew test 

To create a directory that looks like you just installed Degraph:

    gradlew installApp

**Download**

[Binary distribution of Degraph](http://blog.schauderhaft.de/wp-content/uploads/2012/12/degraph-0.0.1.zip)

**Feedback**

Please use [the github issue feature](https://github.com/schauder/degraph/issues) for questions, bug reports or improvement requests. 

If you like Degraph just say so on twitter, facebook or wherever you like. 
