package org.simedape.wp.dto

case class DynamicTimeWarpingResultDTO(
  distance: Double,
  path: List[(Int, Int)],
)
