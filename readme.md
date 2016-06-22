**Degraph** is a tool for visualizing and testing class and package dependencies in JVM applications.

You can [download](http://schauder.github.io/degraph/download.html) it, or read the [documentation](http://schauder.github.io/degraph/documentation.html).

## How to build ##

I'm highly interested in contributions, so if you are interested, drop me a message and until I respond have a look at the source code.

Degraph uses gradle as a build tool. Since gradle is awesome you actually don't have to install gradle, you just need a working JDK installation.

Just get the sourcecode and run one of the following commands:

To execute all the tests:

    gradlew test 

To create a directory that looks like you just installed Degraph:

    gradlew installApp

## Roadmap ##

Degraph allows to defined dependency constraints using tests written in Scala with ScalaTest or Java with JUnit. 
This feature will need quite some tweaking in the future: 

* Support for ignoring dependencies
* Separating the decision to ignore dependencies not mentioned in a constraint and if one can skip steps in the chain of dependencies (i.e. splitting allow/allowDirect into multiple DSL artifacts
* Creating a graph on test failure that visualizes the constraint violations.

Also while yed is a cool Graph editor it does way more then Degraph actually needs and misses some other features. So a standaloe (or IDE Plugin based) GUI would be nice. I'm experimenting with an HTML5 based solution.

If you have more ideas what Degraph should be able to do, just open an [issue](https://github.com/schauder/degraph/issues).


## Feedback ##

Please use [the github issue feature](https://github.com/schauder/degraph/issues) for questions, bug reports or improvement requests. 

If you like Degraph just say so on twitter, facebook or wherever you like. 

## Build Info ##

[![Build Status](https://api.shippable.com/projects/55a4b660edd7f2c0526bb24c/badge?branch=master)](https://app.shippable.com/projects/55a4b660edd7f2c0526bb24c)
