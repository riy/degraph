package de.schauderhaft.dependencies

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CommandLineParserTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("default outputfile is 'output.graphml'") {
        val config = CommandLineParser.parse(Array[String]())
        config.fileName() should be("output.graphml")
    }

    test("the string after -o is considered the output file name") {
        val config = CommandLineParser.parse(Array[String]("-o", "ExampleFile"))
        config.fileName() should be("ExampleFile")
    }

    test("default input is empty") {
        val config = CommandLineParser.parse(Array[String]())
        config.input() should be("")
    }

    test("the string after -i is considered the input classpath") {
        val config = CommandLineParser.parse(Array[String]("-i", "input;blah.jar"))
        config.input() should be("input;blah.jar")
    }

    test("default filter is empty") {
        val config = CommandLineParser.parse(Array[String]())
        config.filter() should be("")
    }
    test("the strings after -f s are considered the filter") {
        val config = CommandLineParser.parse(Array[String]("-f", "filter"))
        config.filter() should be("filter")
    }

    test("the strings after -g s are considered the groupings") {
        pending
    }

    test("no parameter results in an help message") {
        pending
    }

    test("any undefined parameter results in an help message") {
        pending
    }

    test("any undefined parameter results in an help message, even when correct parameters are present") {
        pending
    }

}