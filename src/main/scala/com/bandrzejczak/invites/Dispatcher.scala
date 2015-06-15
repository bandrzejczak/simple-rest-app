package com.bandrzejczak.invites

import com.bandrzejczak.invites.Invites._
import spray.http.MediaTypes._
import spray.http.{HttpResponse, StatusCodes}
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

trait Dispatcher extends HttpService with SprayJsonSupport {
  import JsonProtocol._

  val routes =
      path("invitation") {
        get {
          respondWithMediaType(`application/json`) {
            complete {
              List[Invitation]()
            }
          }
        } ~
        post {
          entity(as[Invitation]) { invitation =>
            complete(HttpResponse(StatusCodes.Created))
          }
        }
      }

}
