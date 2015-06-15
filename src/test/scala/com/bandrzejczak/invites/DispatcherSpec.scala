package com.bandrzejczak.invites

import akka.actor.ActorRefFactory
import com.bandrzejczak.invites.Invites.Invitation
import org.scalatest.{FlatSpec, Matchers}
import spray.http.StatusCodes
import spray.testkit.ScalatestRouteTest

class DispatcherSpec
  extends FlatSpec
  with Matchers
  with ScalatestRouteTest
  with Dispatcher {
  import JsonProtocol._

  def actorRefFactory: ActorRefFactory = system

  "Dispatcher" should "fetch initially empty list of invites" in {
    Get("/invitation") ~> routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[List[Invitation]] shouldBe List[Invitation]()
    }
  }
}
