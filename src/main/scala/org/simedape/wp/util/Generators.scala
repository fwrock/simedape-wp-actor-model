package org.simedape.wp.util

import org.simedape.wp.dto.{ClusteringDTO, SimulationPointDTO}

object Generators extends App {

  def generateRandomDoubleArray(index: Int, size: Int): Array[Double] = {
    val array = (0 to size).map(_ => scala.util.Random.nextDouble()).toArray
    array.prepended(index)
  }

  def generateRandomDoubleArray(size: Int): Array[Double] = {
    (0 to size).map(_ => scala.util.Random.nextDouble()).toArray
  }

  def generateTimeSeriesDataset(size: Int, length: Int): Array[Array[Double]] = {
    (0 to size).map(i => generateRandomDoubleArray(i, length)).toArray
  }

  def generateClusteringStringJson(size: Int, timeSeriesSize: Int): String = {
    val clustering = (0 to size).map { i =>
      s"""{"index":$i,"simulationPoint":[${generateRandomDoubleArray(timeSeriesSize).mkString(",")}],"filePath":"file$i", "numberOfClusters":$size", "totalTimeSeries":$size""}"""
    }.mkString(",")
    s"[$clustering]"
  }

  def generateClusteringStringObject(size: Int, timeSeriesSize: Int): ClusteringDTO = {
    val simulationPoints = (1 to size).map { i =>
      SimulationPointDTO(i, generateRandomDoubleArray(timeSeriesSize))
    }.toArray
    ClusteringDTO(size, timeSeriesSize, simulationPoints)
  }


  println(generateRandomDoubleArray(10).mkString(","))
  println(generateTimeSeriesDataset(10, 10).map(_.mkString(",")).mkString("\n"))
  println(generateClusteringStringJson(10, 10))
}
