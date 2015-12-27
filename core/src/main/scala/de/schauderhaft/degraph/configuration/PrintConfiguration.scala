package de.schauderhaft.degraph.configuration

/**
  * configures what and how to print analysis results.
  *
  * Printing refers to writing the result to a graphml file
  */
sealed trait PrintConfiguration

/**
  * Nothing will be printed
  */
case class NoPrinting() extends PrintConfiguration


/**
  * Printing will happen to the given path if onFailureOnly is false or a constraint violation happend
  * @param path where to write the result to.
  * @param onConstraintViolationOnly if result should be written to graphml only when a constraint was violated
  */
case class Print(
  path: String,
  onConstraintViolationOnly: Boolean
) extends PrintConfiguration