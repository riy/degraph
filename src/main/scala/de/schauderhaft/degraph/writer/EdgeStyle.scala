package de.schauderhaft.degraph.writer

import java.awt.Color

case class EdgeStyle(color: Color, width: Double)

object DefaultEdgeStyle extends EdgeStyle(Color.BLACK, 1.0)