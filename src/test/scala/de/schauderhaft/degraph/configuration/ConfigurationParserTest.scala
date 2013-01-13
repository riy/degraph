package de.schauderhaft.degraph.configuration

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConfigurationParserTest extends FunSuite with ShouldMatchers {
    test("empty file creates empty configuration") {
        ConfigurationParser.parse("") should be(Configuration(None, Seq(), Seq(), Map(), None))
    }
}