package org.simedape.wp.exception

case class InvalidMessageException(message: String) extends RuntimeException(message)
