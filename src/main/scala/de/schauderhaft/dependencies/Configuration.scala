package de.schauderhaft.dependencies

case class Configuration(
    fileName : String = "output.graphml",
    input : String = "",
    filter : String = "")