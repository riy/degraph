package de.schauderhaft.dependencies.examples
import org.junit.runner.RunWith
import org.junit.runner.Runner

/**
 * just a file with different dependencies for us in tests
 */

class Token

class SuperClass 
class SubClass extends SuperClass


class User{
    new Token
}

class OtherUser{
    val value = new Token
}

@RunWith(classOf[MyRunner])
abstract class UsesAnnotation

abstract class MyRunner extends Runner
