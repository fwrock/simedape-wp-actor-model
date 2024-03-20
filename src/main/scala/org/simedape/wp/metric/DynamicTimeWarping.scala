package org.simedape.wp.metric

import org.simedape.wp.dto.DynamicTimeWarpingResultDTO

import scala.collection.mutable.ListBuffer
import scala.math.{abs, max, min}

class DynamicTimeWarping {

  def distance(s1: Array[Double], s2: Array[Double]): DynamicTimeWarpingResultDTO = {
    val n = s1.length
    val m = s2.length
    val DTW = Array.ofDim[Double](n + 1, m + 1)
    for (i <- 1 to n) {
      DTW(i)(0) = Double.PositiveInfinity
    }
    for (i <- 1 to m) {
      DTW(0)(i) = Double.PositiveInfinity
    }
    DTW(0)(0) = 0
    for (i <- 1 to n) {
      for (j <- 1 to m) {
        val cost = Math.abs(s1(i - 1) - s2(j - 1))
        DTW(i)(j) = cost + Math.min(DTW(i - 1)(j), Math.min(DTW(i)(j - 1), DTW(i - 1)(j - 1)))
      }
    }
    DynamicTimeWarpingResultDTO(DTW(n)(m), bestPath(DTW))
  }

  def distance(seqA: Array[Double], seqB: Array[Double], r: Int): DynamicTimeWarpingResultDTO = {
    val n = seqA.length
    val m = seqB.length

    val dtw = Array.fill[Double](n + 1, m + 1)(Double.PositiveInfinity)
    dtw(0)(0) = 0

    val lowerBound = Array.fill[Double](n)(Double.PositiveInfinity)
    val upperBound = Array.fill[Double](n)(Double.NegativeInfinity)

    for (i <- 0 until n; j <- Integer.max(0, i - r) until Integer.min(m, i + r)) {
      val value = seqB(j)
      lowerBound(i) = min(lowerBound(i), value)
      upperBound(i) = max(upperBound(i), value)
    }

    for (i <- 1 to n; j <- max(1, i - r) to min(m, i + r)) {
      val cost = abs(seqA(i - 1) - seqB(j - 1))
      val adjCost = if (cost > upperBound(i - 1)) abs(cost - upperBound(i - 1))
      else if (cost < lowerBound(i - 1)) abs(cost - lowerBound(i - 1))
      else cost
      dtw(i)(j) = adjCost + min(min(dtw(i - 1)(j), dtw(i)(j - 1)), dtw(i - 1)(j - 1))
    }
    DynamicTimeWarpingResultDTO(dtw(n)(m), bestPath(dtw))
  }

  private def bestPath(dtw: Array[Array[Double]]): List[(Int, Int)] = {
    val n = dtw.length
    val m = dtw(0).length
    val path = ListBuffer[(Int, Int)]()
    var i = n - 1
    var j = m - 1
    while (i > 0 || j > 0) {
      path.prepend((i, j))
      val minPrev = min(min(dtw(i - 1)(j), dtw(i)(j - 1)), dtw(i - 1)(j - 1))
      if (minPrev == dtw(i - 1)(j - 1)) {
        i -= 1
        j -= 1
      } else if (minPrev == dtw(i)(j - 1)) {
        j -= 1
      } else {
        i -= 1
      }
    }
    path.prepend((0, 0))
    path.toList
  }

}
