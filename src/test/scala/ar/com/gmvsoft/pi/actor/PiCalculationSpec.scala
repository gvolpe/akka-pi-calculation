package ar.com.gmvsoft.pi.actor

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import ar.com.gmvsoft.pi.actor.messages.PiMessage.{Result, Work}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class PiCalculationSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("PiSpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Worker actor" must {
    "send back a partial Result of Pi" in {
      val worker = system.actorOf(Worker.props)
      worker ! Work(2000, 1000)
      expectMsg(Result(1.6666664467593578E-4))
    }
  }

  "A Master actor" must {
    "stop it self" in {
      val listener = system.actorOf(Listener.props)
      val master = system.actorOf(Master.props(4, 1, 1, listener), "master")
      master ! Result(1.6666664467593578E-4)
      expectNoMsg()
    }
  }

}
