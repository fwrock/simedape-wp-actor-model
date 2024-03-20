package org.simedape.wp.dto

case class ClusteringDTO(
  numberOfClusters: Int,
  totalTimeSeries: Int,
  simulationPoints: Array[SimulationPointDTO],
)
