package com.bandrzejczak.invites

import akka.actor.ActorSystem
import akka.io.IO
import spray.can.Http

object Main extends App {
  implicit val system = ActorSystem("invites-system")
  val invites = system.actorOf(Invites.props, "invites")
  implicit val dispatcher = system.actorOf(DispatcherActor.props(invites) , "dispatcher")
  IO(Http) ! Http.Bind(dispatcher, interface = "localhost", port = 18080)
}
