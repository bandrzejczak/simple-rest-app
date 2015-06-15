package com.bandrzejczak.invites

import akka.actor.ActorRefFactory
import com.bandrzejczak.invites.Invites.Invitation
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import spray.http.{HttpEntity, StatusCodes}
import spray.testkit.ScalatestRouteTest

class DispatcherSpec
  extends FlatSpec
  with Matchers
  with ScalatestRouteTest
  with Dispatcher
  with OneInstancePerTest {
  import JsonProtocol._

  val invites = system.actorOf(Invites.props)

  def actorRefFactory: ActorRefFactory = system

  "Dispatcher" should "fetch initially empty list of invites" in {
    Get("/invitation") ~> routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[List[Invitation]] shouldBe List[Invitation]()
    }
  }

  it should "create an invite" in {
    Post("/invitation", Invitation("John Smith", "john@smith.mx")) ~> routes ~> check {
      status shouldBe StatusCodes.Created
      responseAs[HttpEntity] shouldBe HttpEntity.Empty
    }
  }

  it should "fetch invites lists with recently added invitations" in {
    Post("/invitation", Invitation("John Smith", "john@smith.mx")) ~> routes ~> check {
      status shouldBe StatusCodes.Created
      responseAs[HttpEntity] shouldBe HttpEntity.Empty
    }

    Get("/invitation") ~> routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[List[Invitation]] shouldBe List(Invitation("John Smith", "john@smith.mx"))
    }
  }

  it should "respond with 409 conflict if invitation with a given email already exists" in {
    Post("/invitation", Invitation("John Smith", "john@smith.mx")) ~> routes ~> check {
      status shouldBe StatusCodes.Created
      responseAs[HttpEntity] shouldBe HttpEntity.Empty
    }

    Post("/invitation", Invitation("John Murphy", "john@smith.mx")) ~> routes ~> check {
      status shouldBe StatusCodes.Conflict
      responseAs[HttpEntity] shouldBe HttpEntity.Empty
    }
  }
}
