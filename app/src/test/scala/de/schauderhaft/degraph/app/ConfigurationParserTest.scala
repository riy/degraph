package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers.{ include => _, _ }
import org.scalatest.FunSuiteLike
import de.schauderhaft.degraph.app.ConfigurationParser

@RunWith(classOf[JUnitRunner])
class ConfigurationParserTest extends ConfigurationParser with FunSuiteLike {
  test("empty file creates empty configuration") {
    parse("") should be(Configuration(None, Seq(), Seq(), Map(), None))
  }
  test("file with just comments creates empty configuration") {
    parse("# comment") should be(Configuration(None, Seq(), Seq(), Map(), None))
  }

  test("output configures outputfile configuration") {
    parse("output=example.file") should be(Configuration(None, Seq(), Seq(), Map(), Some("example.file")))
  }
  test("output configures outputfile configuration, leading newlines") {
    parse("""
output=example.file""") should be(Configuration(None, Seq(), Seq(), Map(), Some("example.file")))
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

  test("trailing new line gets ignored") {
    parse("""include=pattern
""") should be(Configuration(None, Seq("pattern"), Seq(), Map(), None))
  }

  test("trailing new lines gets ignored") {
    parse("""include=pattern
    
    	
    	""") should be(Configuration(None, Seq("pattern"), Seq(), Map(), None))
  }

  test("two part configuration example") {
    parse("""output=example.file
include=pattern""") should be(Configuration(None, Seq("pattern"), Seq(), Map(), Some("example.file")))
  }

  test("two part configuration example with whitespace") {
    parse("""
    			output=example.file
    			include=pattern
    			""") should be(Configuration(None, Seq("pattern"), Seq(), Map(), Some("example.file")))
  }
  test("two part configuration example with whitespace around =") {
    parse("""
    			output = example.file
    			include = pattern
    			""") should be(Configuration(None, Seq("pattern"), Seq(), Map(), Some("example.file")))
  }

  test("full configuration example with whitespace") {
    parse("""
                classpath=ap;x.jar
    			output=example.file
    			include=pattern
    	        exclude=expattern
    			""") should be(Configuration(Some("ap;x.jar"), Seq("pattern"), Seq("expattern"), Map(), Some("example.file")))
  }
  test("full configuration example with whitespace &  comments") {
    parse("""
                classpath=ap;x.jar
                # some comment
    			output=example.file
                      # more comments
    			include=pattern
    	        exclude=expattern
                # trailing comments
    			""") should be(Configuration(Some("ap;x.jar"), Seq("pattern"), Seq("expattern"), Map(), Some("example.file")))
  }

  test("Named Pattern") {
    parseAll(namedPattern, """DependencyFinder *.jeant*.**
""").get should be(NamedPattern("""*.jeant*.**""", "DependencyFinder"))
  }

  test("word") {
    parseAll(word, """ß439qu8rej""").get should be("""ß439qu8rej""")
  }

  test("curly braces aren't a word : {") {
    parseAll(word, """{""").successful should be(false)
  }

  test("curly braces aren't a word : }") {
    parseAll(word, """{""").successful should be(false)
  }

  test("slice definition") {
    parse("""
            libs = {
                DependencyFinder *.jeant*.**
                *.(*).**           
            }
                """) should be(Configuration(
      categories = Map("libs" -> List(NamedPattern("""*.jeant*.**""", "DependencyFinder"), UnnamedPattern("""*.(*).**""")))))
  }

}