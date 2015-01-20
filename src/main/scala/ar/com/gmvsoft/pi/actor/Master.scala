package ar.com.gmvsoft.pi.actor

import akka.actor.{ActorRef, Props, Actor}
import akka.routing.RoundRobinRouter
import ar.com.gmvsoft.pi.actor.messages.PiMessage.{PiApproximation, Result, Work, Calculate}

object Master {

  def props(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int, listener: ActorRef) =
    Props(new Master(nrOfWorkers, nrOfElements, nrOfMessages, listener))

}

class Master(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int, listener: ActorRef) extends Actor {

  var pi: Double = _
  var nrOfResults: Int = _
  val start: Long = System.currentTimeMillis

  val workerRouter = context.actorOf(
    Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")

  def receive = {
    case Calculate =>
      for (i ← 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
    case Result(value) =>
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start))
        // Stops this actor and all its supervised children
        context.stop(self)
      }
  }

}
