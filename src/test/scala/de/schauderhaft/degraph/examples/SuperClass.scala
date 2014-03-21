package de.schauderhaft.degraph.examples
import org.junit.runner.RunWith
import org.junit.runner.Runner
import java.util.ArrayList
import java.lang.annotation.{RetentionPolicy, Retention}

/**
 * just a file with different dependencies for use in tests
 */

class Token

class SuperClass
class SubClass extends SuperClass

class User {
  new Token
}

class OtherUser {
  val value = new Token
}

class MyArrayList extends ArrayList[String]

abstract class ListUser {
  val listOfStrings = Vector[String]()
  def listOfInts: Seq[Int]
}

@RunWith(classOf[MyRunner])
abstract class UsesAnnotation

abstract class MyRunner extends Runner

@Retention(RetentionPolicy.RUNTIME)
abstract class EnumInAnnotationUser