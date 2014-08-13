import akka.actor.{Props, Actor, ActorSystem}

object PinnedDispatcherExample extends App {

  val system = ActorSystem("PinnedDispatcherExample")
  println("System default dispatcher is: " + system.dispatcher.getClass)
  println("Main thread id is :" + Thread.currentThread().getId)

  val firstActor = system.actorOf(Props[FooActor].withDispatcher("my-dispatcher"))
  val secondActor = system.actorOf(Props[FooActor].withDispatcher("my-dispatcher"))
  firstActor ! WhichDispatcher()
  secondActor ! WhichDispatcher()
}

case class WhichDispatcher()

class FooActor extends Actor {
  override def receive = {
    case WhichDispatcher() => {
      println("Actor -- " + this.getClass +
        ". Dispatcher used by Actor is: " + context.dispatcher.getClass +
        ". Run by thread with id : " + Thread.currentThread().getId)
    }
  }
}

