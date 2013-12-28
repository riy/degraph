package de.schauderhaft.degraph.writer

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.awt.Color
import java.awt.Color.RED

@RunWith(classOf[JUnitRunner])
class EdgeStyleTest extends FunSuite {
  test("colorHexString represents color") {
    EdgeStyle(new Color(255, 255, 255), 1.0).colorHexString should be("#FFFFFF")
    EdgeStyle(new Color(238, 9, 1), 1.0).colorHexString should be("#EE0901")
    EdgeStyle(new Color(0, 0, 0), 1.0).colorHexString should be("#000000")
  }
  test("widthString represents width") {
    EdgeStyle(RED, 1.0).widthString should be("1.0")
    EdgeStyle(RED, 3.5).widthString should be("3.5")
    EdgeStyle(RED, 0.0).widthString should be("0.1") // not to let lines completly disapear
  }
}