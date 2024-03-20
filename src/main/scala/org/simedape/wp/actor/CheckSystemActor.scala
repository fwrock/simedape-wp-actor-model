package org.simedape.wp.actor

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import org.simedape.wp.message.CheckQueueMessage

class CheckSystemActor extends Actor {

  private val logger = Logger(classOf[CheckSystemActor])

  private var messageCount = 0

  override def receive: Receive = {
    case message : CheckQueueMessage =>
      if (messageCount == 0) {
        logger.info("System is idle. Terminating the system.")
        logger.info(s"Elapsed time: ${message.stopWatch.getElapsedTime()}")
        context.system.terminate()
      } else {
        logger.info(s"System is busy. Message count: ${messageCount}")
        messageCount = 0
      }
    case _ => messageCount += 1
  }
}
