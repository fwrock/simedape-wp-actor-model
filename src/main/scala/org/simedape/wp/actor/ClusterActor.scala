package org.simedape.wp.actor

import akka.actor.{Actor, Props}
import com.typesafe.scalalogging.Logger
import org.simedape.wp.exception.InvalidMessageException
import org.simedape.wp.message.{RequestCalcMessage, StartMessage}
import org.simedape.wp.util.{ClusteringLoader, Generators, TimeSeriesLoader}

class ClusterActor extends Actor {

  private val logger = Logger(classOf[TimeSeriesActor])
  def receive: Receive = {
    case message: StartMessage =>
      logger.info(s"Start clustering for simulation point ${message.index}")

      //val dataset = TimeSeriesLoader.loadTimeSeriesDatasetFromCSVFile(10, 10)

      val dataset = Generators.generateTimeSeriesDataset(6961, 3403)

      dataset.foreach(row => processTimeSeries(message.simulationPoint, row))

      //dataset.foreachPartition(iterator => processPartition(iterator, message.simulationPoint))

    case _ => throw InvalidMessageException("Invalid message received")
  }


  private def processPartition(iterator: Iterator[Array[Double]], simulationPoint: Array[Double]): Unit = {
    iterator.foreach(row => processTimeSeries(simulationPoint, row))
  }

  private def processTimeSeries(simulationPoint: Array[Double], timeSeries: Array[Double]): Unit = {
    val timeSeriesActor = context.actorOf(Props(classOf[TimeSeriesActor]), s"TimeSeriesActor-${timeSeries(0)}")

    timeSeriesActor ! RequestCalcMessage(timeSeries(0).intValue(),
      simulationPoint,
      timeSeries.slice(1, timeSeries.length)
    )
  }
}
