package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.check.hamcrest.HamcrestWrapper

/**
 * This is the Java DSL for writing Degraph dependency tests using Java.
 *
 * It is equivalent with Check, but optimized for Java usage.
 */
object JCheck {
  def classpath = Check.classpath

  def customClasspath(path: String) = Check.customClasspath(path)

  val violationFree = new HamcrestWrapper[ConstraintBuilder](Check.violationFree)
}