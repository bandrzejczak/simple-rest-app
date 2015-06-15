package com.bandrzejczak.invites

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.bandrzejczak.invites.Invites._
import spray.http.MediaTypes._
import spray.http.{HttpResponse, StatusCodes}
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

import scala.concurrent.duration._

class DispatcherActor(val invites: ActorRef) extends Actor with Dispatcher {
  def actorRefFactory = context
  def receive = runRoute(routes)
}

object DispatcherActor {
  def props(invites: ActorRef) = Props(classOf[DispatcherActor], invites)
}

trait Dispatcher extends HttpService with SprayJsonSupport {
  import JsonProtocol._

  val invites: ActorRef

  implicit val executionContext = actorRefFactory.dispatcher
  implicit val actorTimeout = Timeout(1.second)

  val routes =
      path("invitation") {
        get {
          respondWithMediaType(`application/json`) {
            onSuccess((invites ? InvitationsQuery).mapTo[Invitations].map(_.invitations)) {
              complete(_)
            }
          }
        } ~
        post {
          entity(as[Invitation]) { invitation =>
            onSuccess((invites ? CreateInvitation(invitation)).mapTo[Status]) {
              case InvitationCreated => complete(HttpResponse(StatusCodes.Created))
              case InvitationAlreadyExists => complete(HttpResponse(StatusCodes.Conflict))
            }
          }
        }
      }

}
