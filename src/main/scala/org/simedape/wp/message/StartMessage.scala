package org.simedape.wp.message

case class StartMessage(
  index: Int,
  simulationPoint: Array[Double],
  filePath: String,
)
