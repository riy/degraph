# Release Notes

## 0.1.5

- Upgraded Gradle from `3.0` to `6.0` in order to make Degraph build with Java 11.
- Specified Java `1.8` as `sourceCompatibility` and `targetCompatibility`.
- Building on the latest Shippable image (v7.2.4) using the new `shippable.yml` format

## 0.1.4

### New Features

- NamedPattern warns, when the name looks like a pattern. Multiple users experienced confusing
    results, due to providing the parameters in wrong order.

- `printTo` in the ConstraintBuilder now always prints the analysis results. If you want the old behaviour back, use
    `printOnFailure`

- `Check` and `JCheck` got a new method `customClasspath`. You can use it in order to provide a classpath explicitly 
    instead of using the classpath used by the current JVM.

- ConstraintBuilder got a new method `filterClasspath(pattern: String): ConstraintBuilder`
    for limiting the classpath to elements matching the pattern.
    This should be useful, if you want to analyze some, but not all jar-files.

- ConstraintBuilder got a new method `filterClasspath(filter: (String) => Boolean): ConstraintBuilder`
    for limiting the classpath to elements matching the pattern.
    This should be useful, if you want to analyze some, but not all jar-files.

- minor performance improvements

### Known Issues

- Due to a JDK or ASM bug analysis of class files which contain Type-Annotations might fail. Dependencies of such classes
will be incomplete. A message saying so will be displayed. See 

## 0.1.3

Version 0.1.3 is the same as 0.1.2

## 0.1.2

### Bugfixes

- Fixed the build file so it works without any special properties, like usernames, password and private keys,
that are only needed for releasing [#56](https://github.com/schauder/degraph/issues/56)

- Fixed [Type annotations cause RuntimeException (#55)](https://github.com/schauder/degraph/issues/55)

### New Features

- Java 8 Type Annotations get analyzed.

## 0.1.1

### Bugfixes
    
- classes referenced in an array typed parameter of an annotation where not recognized as a dependency. This is fixed. 

- static methods that get referenced didn't create the appropriate dependency https://github.com/schauder/degraph/issues/53

### New Features

- Degraph is now available from Maven Central, so if you are using it as a library you can specify it in your 
    ´build.gradle´ file or ´pom.xml´ or whatever. The maven syntax for the dependency you need for tests is:
     
         <dependency>
           <groupId>de.schauderhaft.degraph</groupId>
           <artifactId>degraph-check</artifactId>
           <version>0.1.1</version>
         </dependency>

- ´.printTo("somepath.graphml")´ you can create a graphml diagram on test failure.

## 0.1.0

### Bugfixes
    
- in the previous releases Degraph used DependencyFinder for analyzing dependencies. Unfortunately this missed many
dependencies based on java language features post 1.5. I switched to ASM, which finds many more dependencies.
Hopefully all, but to be honest: I can't really tell. Bug reports are highly welcome.

### New Features

- Split into three modules:

    - core contains the main logic and algorithm. Use this when you want to build your own stuff based on 'my' algorithms

    - app contains the command line application. Also contains the parser for the configuration files

    - check contains the DSL for testing dependencies in your tests

    Since I strongly believe that a package should exist only in one module, some package names had to change as well.


- you now can define and test dependency constraints using Java http://schauder.github.io/degraph/documentation.html for details

## 0.0.4 

### Bugfixes

- in previous releases dependencies only used inside a method didn't get picked up by Degraph. This is now fixed.

### New Features

- you can now define and test dependency constraints, see http://schauder.github.io/degraph/documentation.html for details

### 0.0.3

includes & excludes now use the ant like syntax used for slicing. This makes them easier to use. 
They loose some of the theoretical power, but that was of little use for this purpose anyway.

Edges have now smooth corners making following them with the eye easier.

For each slicing a check for circular dependencies is implemented. 
If a cycle is found all dependencies contributing to that circle get colored red. 
For each slicing there is at most one circle detected. 
So after breaking that circle Degraph might confront you with another one.
This is a easy to implement solution for the final goal of marking a small as
possible set of edges to break in order to get a directed acylcic graph for all slicings

-g option removed

### 0.0.2

Configuration files introduced. Thereby achieving a usable way to define slices. 
See https://github.com/schauder/degraph/wiki/Configuration-File-Format for usage

Graph now has nice colors

### 0.0.1

Basic graph creation
