import akka.actor.{Props, ActorSystem, Actor, FSM}
import akka.actor.LoggingFSM
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

object Turnstile {

  trait State

  case class Locked() extends State

  case class Unlocked() extends State

  case class Data(step:Int)
}

object Messages {

  trait Event

  case class Coin() extends Event

  case class Push() extends Event
}

import Turnstile._
import Messages._

class Turnstile extends Actor with FSM[State, Data] with  LoggingFSM[State, Data]{

  val config = ConfigFactory.load()
  println("config = " + config.getString("akka.actor.debug.fsm"))

  startWith(Locked(), Data(0))

  log.debug("Starting logging")

  when(Locked()) {
    case Event(Coin(), Data(step)) =>
      println(step + ": Locked + Coin = Unlocked")
      goto(Unlocked()) using Data(step + 1)

    case Event(Push(), Data(step)) =>
      println(step + ": Locked + Push = Locked")
      goto(Locked()) using Data(step + 1)
  }

  when(Unlocked()) {
    case Event(Coin(), Data(step)) =>
      println(step + ": UnLocked + Coin = Unlocked")
      goto(Unlocked()) using Data(step + 1)

    case Event(Push(), Data(step)) =>
      println(step + ": UnLocked + Push = Locked")
      goto(Locked()) using Data(step + 1)
  }

  onTransition{
    case Locked() -> Unlocked() => println("Moving from Locked to Unlocked")
  }

  initialize()

}

object MyApp extends App {

  val system = ActorSystem("myApp")
  val actor = system.actorOf(Props[Turnstile])
  actor ! Coin()
  actor ! Coin()
  actor ! Push()
  actor ! Push()
}