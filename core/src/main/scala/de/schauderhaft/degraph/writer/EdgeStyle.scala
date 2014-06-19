package de.schauderhaft.degraph.writer

import java.awt.Color
import java.util.Locale

case class EdgeStyle(color: Color, width: Double) {

    private def toHex(i: Int): String = {
        val s = i.toHexString
        if (s.length == 1) "0" + s
        else s
    }

    val colorHexString = "#" + (
        toHex(color.getRed()) +
        toHex(color.getGreen()) +
        toHex(color.getBlue())).toUpperCase()

    val widthString =
        if (width < 0.1)
            "0.1"
        else
            "%2.1f".formatLocal(Locale.US, width)
}

object DefaultEdgeStyle extends EdgeStyle(Color.BLACK, 1.0)