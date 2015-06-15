package com.bandrzejczak.invites

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.bandrzejczak.invites.Invites.{CreateInvitation, InvitationCreated, Invitations, InvitationsQuery}
import org.scalatest.FlatSpecLike

class InvitesSpec
  extends TestKit(ActorSystem("InvitesSystem"))
  with ImplicitSender
  with FlatSpecLike {

  "Invites" should "initially respond to a query with an empty list" in {
    //given
    val invites = system.actorOf(Invites.props)
    //when
    invites ! InvitationsQuery
    //then
    expectMsg(Invitations())
  }

  it should "successfully create a new invite" in {
    //given
    val invites = system.actorOf(Invites.props)
    //when
    invites ! CreateInvitation("John Smith", "john@smith.mx")
    //then
    expectMsg(InvitationCreated)
  }

}
