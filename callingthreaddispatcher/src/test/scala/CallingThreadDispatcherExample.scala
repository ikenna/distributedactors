import akka.actor.{Actor, Props, ActorSystem}
import akka.testkit.CallingThreadDispatcher

object CallingThreadDispatcherExample extends App{

  println("Main thread id is: " + Thread.currentThread().getId)

  implicit val system = ActorSystem("CallingThreadDispatcherExample")
  val fooActor = system.actorOf(Props[FooActor].withDispatcher(CallingThreadDispatcher.Id))
  fooActor ! DoStuff()

}

case class DoStuff()

class FooActor extends Actor {
  override def receive: Actor.Receive = {
    case _ => {
      println("I am doing stuff")
      println("Actor thread id is: " + Thread.currentThread().getId)
    }
  }
}
