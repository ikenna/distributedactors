import akka.actor.{Actor, Props, ActorSystem}

object CallingThreadDispatcherExample extends App{

  val system = new ActorSystem("CallingThreadDispatcherExample")
  val fooActor = system.actorOf(Props[FooActor])
  fooActor ! DoStuff()

  import akka.testkit.TestActorRef

  val actorRef = TestActorRef[FooActor]
  val actor = actorRef.underlyingActor

}

case class DoStuff()

class FooActor extends Actor {
  override def receive: Actor.Receive = {
    case _ => println("I am doing stuff")
  }
}
