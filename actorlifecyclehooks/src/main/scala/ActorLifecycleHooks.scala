import akka.actor.SupervisorStrategy._
import akka.actor.{OneForOneStrategy, Props, Actor, ActorSystem}

object ActorLifecycleHooks extends App {

  println("Starting actor system")
  val system = ActorSystem("ActorLifecycleHooks")
  val supervisor = system.actorOf(Props[Supervisor])
  supervisor ! DoStuff()
}

case class DoStuff()

class Supervisor extends Actor {

  val fooActor = context.actorOf(Props[Child], "child")

  override def supervisorStrategy = OneForOneStrategy() {
    case Fault(x) => Restart
  }

  override def receive = {
    case x => fooActor.forward(x)
  }
}

case class Fault(message: String) extends Exception

class Child extends Actor {

  var stepCount = 1
  
  println(step + " Actor object initialization")

  override def preStart(): Unit = println(step + " PreStart() hook called")

  override def postStop(): Unit = println(step + " PostStop() hook called")

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(step + "PreRestart(Throwable, message) hook called. ")
  }

  override def postRestart(reason: Throwable): Unit = {
    println( s""" ${step} PostRestart(Throwable) hook called""")
  }

  override def receive = {
    case DoStuff() => {
      println(step + "receive() called : Actor doing stuff and then developing a Fault ")
      throw new Fault("a fault occurred")
    }
  }

  def step: String = {
    val output = "Step " + stepCount + ": "
    stepCount = stepCount + 1
    output
  }
}
