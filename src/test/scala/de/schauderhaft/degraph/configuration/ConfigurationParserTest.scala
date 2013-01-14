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

    test("output configures outputfile configuration with trailing eol") {
        parse("""output=example.file
""") should be(Configuration(None, Seq(), Seq(), Map(), Some("example.file")))
    }

    test("include configures include configuration") {
        parse("include=pattern") should be(Configuration(None, Seq("pattern"), Seq(), Map(), None))
    }

    test("leading whitespace gets ignored") {
        parse("   include=pattern") should be(Configuration(None, Seq("pattern"), Seq(), Map(), None))
    }

    test("trailing whitespace gets ignored") {
        parse("include=pattern    ") should be(Configuration(None, Seq("pattern"), Seq(), Map(), None))
    }

    test("trailing new lines gets ignored") {
        parse("""include=pattern
""") should be(Configuration(None, Seq("pattern"), Seq(), Map(), None))
    }

    test("full configuration example") {
        parse("""
                output=example.file
       include=pattern
                """) should be(Configuration(None, Seq("pattern"), Seq(), Map(), Some("example.file")))
    }

}