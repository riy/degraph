package de.schauderhaft.degraph.app

import org.rogach.scallop.exceptions.{Help, ScallopException}
import de.schauderhaft.degraph.configuration.{Print, Configuration}
import scala.io.Source

object ConfigurationFromCommandLine {

  /**
   * just pass the commandline arguments to this method to get an Either representing the
   * result of attempting to create a configuration out of the arguments.
   *
   * If something went wrong a Left will get returned containing an error message, including
   * some usage advice suitable for presenting it to the user.
   *
   * Otherwise a Right instance containing the complete configuration is returned.
   */
  def apply(args: Array[String]): Either[String, Configuration] = {
    val eitherConfig = fromCommandLine(args)

    eitherConfig
  }
  def fromCommandLine(args: Array[String]): Either[String, Configuration] = {
    import scala.language.reflectiveCalls
    var errorMessage: Option[String] = None
    val commandLine = CommandLineParser.parse(args)
    commandLine.initialize {
      case ScallopException(m) => errorMessage = Some(m + "\nUsage:\n" + commandLine.builder.help)
      case Help(_) => errorMessage = Some("\nUsage:\n" + commandLine.builder.help)
    }
    errorMessage match {
      case Some(m) => Left(m)
      case _ if commandLine.file.isEmpty => Right(
        Configuration(
          classpath = Some(commandLine.classpath()),
          includes = commandLine.includeFilter(),
          excludes = commandLine.excludeFilter(),
          output = Print(commandLine.output()),
          categories = Map()
        )
      )
      case _ => Right(new ConfigurationParser().parse(Source.fromFile(commandLine.file()).mkString))
    }
  }
}
