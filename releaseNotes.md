## Release Notes

### 0.0.3

includes & excludes now use the ant like syntax used for slicing. This makes them easier to use. 
They loose some of the theoretical power, but that was of little use for this purpose anyway.

Edges have now smooth corners making following them with the eye easier.

For each slicing a check for circular dependencies is implemented. 
If a cycle is found all dependencies contributing to that circle get colored red. 
For each slicing there is at most one circle detected. 
So after breaking that circle Degraph might confront you with another one.
This is a easy to implement solution for the final goal of marking an as small as 
possible set of edges to break in order to get a direacted acylcic graph for all slicings

-g option removed

### 0.0.2

Configuration files introduced. Thereby achieving a usable way to define slices. 
See https://github.com/schauder/degraph/wiki/Configuration-File-Format for usage

Graph now has nice colors

### 0.0.1

Basic graph creation