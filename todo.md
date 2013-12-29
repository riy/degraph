Is exclude available in checks?

* nicely format documentation
* write release notes
- Migration to Scala 2.10
- Java API

* speed up tests
* split degraph in at least two projects: Degraph Check + Degraph Core (+ Degrap App)
* remove runtime dependency to all things test from all but the check project

* test and cleanup malformed Configuration
* refactor Categories to Slicing

* make dependency finder available for maven/gradle/ivy

**Other Dependency Analysis Tools** 
* http://classycle.sourceforge.net/
* Dependency Finder
* JDepend

**Layout Algorithms**
* http://en.wikipedia.org/wiki/Layered_graph_drawing
* http://www.graphviz.org/Documentation/TSE93.pdf
* http://en.wikipedia.org/wiki/Coffman%E2%80%93Graham_algorithm
* http://en.wikipedia.org/wiki/Layered_graph_drawing especially first bullet point
* http://graphstream-project.org/doc/Tutorials/Getting-Started_1.0/

**Other Resources and Stuff**
* http://stackoverflow.com/questions/4421882/how-to-unit-test-package-dependencies-in-scala
* Spanning Tree as Basis for Layout of cyclic directed graphs


**Todo for release**
* set version in CommandLineParser
* set version in example config
* release
* upload distribution files
* update download links in readme
* test examples 
* check for version numbers in the wiki pages

