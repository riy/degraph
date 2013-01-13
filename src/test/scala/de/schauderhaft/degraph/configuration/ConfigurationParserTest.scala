package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers.{ include => _, _ }

@RunWith(classOf[JUnitRunner])
class ConfigurationParserTest extends ConfigurationParser with FunSuite {
    test("empty file creates empty configuration") {
        parse("") should be(Configuration(None, Seq(), Seq(), Map(), None))
    }

    test("output configures outputfile configuration") {
        parse("output=example.file") should be(Configuration(None, Seq(), Seq(), Map(), Some("example.file")))
    }

    test("output parses output=file expression") {
        val result = parseAll(output, "output=file")
        result should be('successful)
        result.get should be("file")
    }

    test("include parses include=pattern expression") {
        val result = parseAll(include, "include=pattern")
        result should be('successful)
        result.get should be("pattern")
    }

}