package de.schauderhaft.degraph.configuration

/**
  * configures what and how to print analysis results.
  *
  * Printing refers to writing the result to a graphml file
  */
sealed trait PrintConfiguration {
  def foreach(f: (String) => Unit)

  /** merges this and a second PrintConfiguration into a new one */
  def merge(other: PrintConfiguration): PrintConfiguration
  def isDefined: Boolean
}

/**
  * Nothing will be printed
  */
case class NoPrinting() extends PrintConfiguration {
  override def merge(other: PrintConfiguration): PrintConfiguration = other

  override val isDefined = false

  override def foreach(f: (String) => Unit): Unit = ()
}


/**
  * Printing will happen to the given path if onFailureOnly is false or a constraint violation happend
  * @param path where to write the result to.
  * @param onConstraintViolationOnly if result should be written to graphml only when a constraint was violated
  */
case class Print(
  path: String,
  onConstraintViolationOnly: Boolean = false
) extends PrintConfiguration {


  override def merge(other: PrintConfiguration): PrintConfiguration = {
    def isConstraintViolationOnly(other: PrintConfiguration): Boolean =
      other.isInstanceOf[Print] && other.asInstanceOf[Print].onConstraintViolationOnly
    copy(
      onConstraintViolationOnly = onConstraintViolationOnly && isConstraintViolationOnly(other)
    )
  }

  override val isDefined = true

  override def foreach(f: (String) => Unit): Unit = f(path)
}