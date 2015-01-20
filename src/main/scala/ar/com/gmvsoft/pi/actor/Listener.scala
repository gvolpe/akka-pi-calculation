package ar.com.gmvsoft.pi.actor

import akka.actor.{Props, Actor}
import ar.com.gmvsoft.pi.actor.messages.PiMessage.PiApproximation

object Listener {

  def props = Props(new Listener)

}

class Listener extends Actor {

  def receive = {
    case PiApproximation(pi, duration) =>
      println("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s ms"
        .format(pi, duration))
      context.system.shutdown
  }

}
