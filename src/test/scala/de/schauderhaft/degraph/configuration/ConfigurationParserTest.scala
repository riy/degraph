package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class ConfigurationParserTest extends ConfigurationParser with FunSuite with ShouldMatchers {
    test("empty file creates empty configuration") {
        parse("") should be(Configuration(None, Seq(), Seq(), Map(), None))
    }

    test("output configures outputfile configuration") {
        (pending)
        parse("output=example.file") should be(Configuration(None, Seq(), Seq(), Map(), Some("example.file")))
    }

    test("output parses output=file expression") {
        val result = parseAll(output, "output=file")
        result should be('successful)
        result.get should be("file")
    }

}