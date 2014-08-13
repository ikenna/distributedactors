import akka.actor.{Props, Actor, ActorSystem}

object PinnedDispatcherExample extends App {

  val system = ActorSystem("PinnedDispatcherExample")
  println("System default dispatcher is: " + system.dispatcher.getClass)
  println("Main thread id is: " + Thread.currentThread().getId)

  val firstActor = system.actorOf(Props(new FooActor(1)).withDispatcher("my-dispatcher"))
  val secondActor = system.actorOf(Props(new FooActor(2)).withDispatcher("my-dispatcher"))
  firstActor ! WhichDispatcher()
  secondActor ! WhichDispatcher()
}

case class WhichDispatcher()

class FooActor(val id:Int) extends Actor {
  override def receive = {
    case WhichDispatcher() => {
      println(s"""Dispatcher used by Actor ${id} is: ${context.dispatcher.getClass}. Run by thread with id: ${Thread.currentThread().getId}""")
    }
  }
}

