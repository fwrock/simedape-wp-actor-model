package org.simedape.wp

import akka.actor.{ActorSystem, Props}
import org.simedape.wp.actor.{CheckSystemActor, ClusterActor, ReportWriterActor}
import org.simedape.wp.message.{CheckQueueMessage, StartMessage}
import org.simedape.wp.util.{Generators, StopWatch}

import scala.concurrent.duration._

object SimEDaPE extends App {

  private val clustering = Generators.generateClusteringStringObject(64, 3403)

  private val stopWatch = new StopWatch()

  stopWatch.start()

  private val actorSystem = ActorSystem("SimEDaPEWarpingPath")

  actorSystem.actorOf(Props(classOf[ReportWriterActor]), "ReportWriterActor")
  val checkSystemActor = actorSystem.actorOf(Props(classOf[CheckSystemActor]), "CheckSystemActor")

  clustering.simulationPoints.foreach(simulationPoint => {
    val clusterActor = actorSystem.actorOf(Props(classOf[ClusterActor]), s"ClusterActor-${simulationPoint.index}")
    clusterActor ! StartMessage(simulationPoint.index, simulationPoint.timeSeries, "src/main/resources/dataset.csv")
  })

  actorSystem.scheduler.scheduleAtFixedRate(
    initialDelay = 30.seconds,
    interval = 15.seconds
  ) {
    () => checkSystemActor ! CheckQueueMessage(stopWatch)
  }(actorSystem.dispatcher)

}
