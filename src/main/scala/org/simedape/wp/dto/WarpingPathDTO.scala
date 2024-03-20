package org.simedape.wp.dto

case class WarpingPathDTO(
 index: Int,
 cluster: Int,
 path: List[(Int, Int)],
)
