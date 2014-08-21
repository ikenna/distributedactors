import akka.actor._
import akka.event.{LoggingAdapter, Logging}

object LoggingExample extends App {

  val actorSystem = ActorSystem("LoggingExample")
  val actor = actorSystem.actorOf(Props[MyActor])
  actor ! AMessage()
  actor ! AMessage()
  actor ! FooMessage()
  actor ! PoisonPill

}

case class AMessage()
case class FooMessage()

class MyActor extends Actor {

  val log:LoggingAdapter = Logging(context.system, this)
  log.info("Here is how to log")

  override def receive = {
    akka.event.LoggingReceive {
      case AMessage() => println("Received a message")
    }
  }
}

class MyOtherActor extends Actor with ActorLogging {

  log.info("Another way to log")

  override def receive = {
      case AMessage() => println("Received a message")
  }
}
