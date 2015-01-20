package ar.com.gmvsoft.pi.actor

import akka.actor.{Actor, Props}
import ar.com.gmvsoft.pi.actor.messages.PiMessage.{Result, Work}

object Worker {

  def props = Props(new Worker)

}

class Worker extends Actor {

  def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0
    for (i ← start until (start + nrOfElements))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    acc
  }

  def receive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiFor(start, nrOfElements))
  }

}
