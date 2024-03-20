package org.simedape.wp.actor

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import org.simedape.wp.message.WarpingPathMessage

class ReportWriterActor extends Actor {

  private val logger = Logger(classOf[ReportWriterActor])

  override def receive: Receive = {
    case message: WarpingPathMessage => {
      println(s"ReportWriterActor received a message: ${message.index}")
    }
    case _ => println("ReportWriterActor received a message")
  }
}
