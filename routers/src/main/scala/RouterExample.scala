import akka.actor.{Props, Actor, ActorSystem}
import akka.routing.{BroadcastRoutingLogic, Router}

object RouterExample extends App {

  val system = ActorSystem("RouterExample")
  val master = system.actorOf(Props[Master], "master")
  for (i <- 1 to 6) master ! WorkLoad()

}

case class WorkLoad()

class Master extends Actor {
  val actor1 = context.actorOf(Props[Slave], "slave-1")
  val actor2 = context.actorOf(Props[Slave], "slave-2")
  val actor3 = context.actorOf(Props[Slave], "slave-3")

  val router = Router(BroadcastRoutingLogic())
  router.addRoutee(actor1)
  router.addRoutee(actor2)
  router.addRoutee(actor3)

  override def receive = {
    case WorkLoad() => router.route(WorkLoad(), self)
  }
}

class Slave extends Actor {
  override def receive = {
    case WorkLoad() => println("Doing work " + self.path)
  }
}