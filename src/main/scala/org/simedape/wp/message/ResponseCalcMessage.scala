package org.simedape.wp.message

case class ResponseCalcMessage(
  index: Int,
  distance: Double,
  warpingPath: List[(Int, Int)],
)
