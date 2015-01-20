package ar.com.gmvsoft.pi.actor

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import ar.com.gmvsoft.pi.actor.messages.PiMessage.PiApproximation
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class ListenerSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("PiSpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Listener actor" must {
    "shutdown the system" in {
      val listener = system.actorOf(Listener.props)
      listener ! PiApproximation(3.14159, System.currentTimeMillis())
      expectNoMsg()
    }
  }

}
