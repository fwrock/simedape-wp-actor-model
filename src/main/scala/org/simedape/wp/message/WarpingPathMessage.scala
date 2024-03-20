package org.simedape.wp.message

case class WarpingPathMessage(
  index: Int,
  distance: Double,
  path: List[(Int, Int)]
)
