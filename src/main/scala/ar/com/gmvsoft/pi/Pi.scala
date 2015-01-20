package ar.com.gmvsoft.pi

import akka.actor.ActorSystem
import ar.com.gmvsoft.pi.actor.messages.PiMessage.Calculate
import ar.com.gmvsoft.pi.actor.{Master, Listener}

object Pi extends App {

  calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int): Unit = {

    val system = ActorSystem("PiSystem")

    val listener = system.actorOf(Listener.props, "listener")

    val master = system.actorOf(Master.props(nrOfWorkers, nrOfElements, nrOfMessages, listener), "master")

    master ! Calculate

  }

}
