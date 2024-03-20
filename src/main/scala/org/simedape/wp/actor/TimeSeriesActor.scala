package org.simedape.wp.actor

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import org.simedape.wp.exception.InvalidMessageException
import org.simedape.wp.message.{RequestCalcMessage, ResponseCalcMessage, WarpingPathMessage}
import org.simedape.wp.metric.DynamicTimeWarping

class TimeSeriesActor() extends Actor {

  private val logger = Logger(classOf[TimeSeriesActor])

  private val dtw = new DynamicTimeWarping()
  override def receive: Receive = {
    case message: RequestCalcMessage => {
      val result = dtw.distance(message.simulationPoint, message.timeSeries, (message.timeSeries.length * 0.1).toInt)

      val reportWriterActor = context.actorSelection("/user/ReportWriterActor")
      val checkSystemActor = context.actorSelection("/user/CheckSystemActor")

      reportWriterActor ! WarpingPathMessage(message.index, result.distance, result.path)
      checkSystemActor ! "Hi, I'm busy."
    }
    case _ => throw InvalidMessageException("Invalid message received by TimeSeriesActor.")
  }
}
