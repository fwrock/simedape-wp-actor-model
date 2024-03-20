package org.simedape.wp.message

case class RequestCalcMessage(
  index: Int,
  simulationPoint: Array[Double],
  timeSeries: Array[Double],
)
