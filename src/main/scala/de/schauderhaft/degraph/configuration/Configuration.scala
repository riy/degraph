package de.schauderhaft.degraph.configuration

case class Configuration(
    classpath: Seq[String],
    includes: Seq[String],
    excludes: String,
    categories: Map[String, Seq[Pattern]])

sealed trait Pattern
case class UnnamedPatter(pattern: String)
case class NamedPatter(pattern: String, name: String)