**Dependency Manager** is a little tool for visualizing dependencies in JVM applications.

It analyses class files using DependencyFinder and spits out a graphml files which then can be rendered using yed.

What makes Dependency Manager different from other tools (I know) is that it supports nested graphs. I.e. you can define rule like a class is contained in it's package, 
a package is part of a module and this fact gets visualized in the resulting graph. 

If do a hierarchic layout in yed you can easily see which classes you can move to different packages, layer or modules without creating circles on these levels or which you have to move in order to break cycles. A feature I haven't seen in any other package dealing with dependencies so far.

** State of this project **

The current version of the project is able to create graphs and do the bundling of classes into subgraphs. You currently have to hard code the classpath you want to analyse in the application and it doesn't do any of the fancy stuff you might envision like different colors of different types of nodes. 

So in the version it is right now **I** can use it for the purposes I have in mind and therefore it probably won't see much progress. But if you are interested in this kind of tool, let me know. I might just implement it, or help you implementing it.   