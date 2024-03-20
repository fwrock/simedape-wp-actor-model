package org.simedape.wp.util

class StopWatch {

  private var startTime: Long = 0
  private var stopTime: Long = 0
  private var running = false

  def start(): Unit = {
    this.startTime = System.currentTimeMillis
    this.running = true
  }

  def stop(): Unit = {
    this.stopTime = System.currentTimeMillis
    this.running = false
  }

  def getElapsedTime(): Long = {
    if (running) {
      System.currentTimeMillis - startTime
    } else {
      stopTime - startTime
    }
  }

  def reset(): Unit = {
    this.startTime = 0
    this.stopTime = 0
    this.running = false
  }

  def isRunning(): Boolean = {
    running
  }

  def getElapsedTimeSecs(): Long = {
    getElapsedTime() / 1000
  }

  def getElapsedTimeMins(): Long = {
    getElapsedTimeSecs() / 60
  }

  def getElapsedTimeHrs(): Long = {
    getElapsedTimeMins() / 60
  }

  def getElapsedTimeDays(): Long = {
    getElapsedTimeHrs() / 24
  }

  def getElapsedTimeFormatted(): String = {
    val days = getElapsedTimeDays()
    val hours = getElapsedTimeHrs() % 24
    val minutes = getElapsedTimeMins() % 60
    val seconds = getElapsedTimeSecs() % 60
    "%d days, %d hours, %d minutes, %d seconds".format(days, hours, minutes, seconds)
  }

  def getElapsedTimeFormattedShort(): String = {
    val days = getElapsedTimeDays()
    val hours = getElapsedTimeHrs() % 24
    val minutes = getElapsedTimeMins() % 60
    val seconds = getElapsedTimeSecs() % 60
    "%02d:%02d:%02d:%02d".format(days, hours, minutes, seconds)
  }

  def getElapsedTimeFormattedShorter(): String = {
    val days = getElapsedTimeDays()
    val hours = getElapsedTimeHrs() % 24
    val minutes = getElapsedTimeMins() % 60
    val seconds = getElapsedTimeSecs() % 60
    "%02d:%02d:%02d".format(hours, minutes, seconds)
  }
}
