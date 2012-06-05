**Degraph** is a little tool for visualizing dependencies in JVM applications.

It analyses class files using DependencyFinder and spits out a graphml file which then can be rendered using [yed](http://www.yworks.com/en/products_yed_about.html).

What makes Dependency Manager different from other tools (that I know of) is that it 
supports nested graphs. I.e. you can define rule like a class is contained in it's package, 
a package is part of a module and this fact gets visualized in the resulting graph. 

If you do a hierarchic layout in yed you can easily see which classes you can move 
to different packages, layer or modules without creating circular dependencies or 
which you have to move in order to break cycles. 
A feature I haven't seen in any other package dealing with dependencies so far.

**How to build**

Degraph uses gradle as a build tool. So you need a gradle installation, get a copy of all the sources, and enter

    gradle build
    
It doesn't get much easier

**How to run**

I'm currently add command line arguments, so stuff is changing around. Just wait a couple of days or look into the source code. Its not much.