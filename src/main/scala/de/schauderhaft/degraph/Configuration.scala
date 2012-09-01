package de.schauderhaft.degraph

case class Configuration(
    fileName : String = "output.graphml",
    input : String = "",
    filter : String = "")