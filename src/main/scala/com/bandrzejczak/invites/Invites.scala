package com.bandrzejczak.invites

import akka.actor.{Actor, Props}
import com.bandrzejczak.invites.Invites.{CreateInvitation, InvitationCreated, Invitations, InvitationsQuery}

class Invites extends Actor {
  override def receive: Receive = {
    case InvitationsQuery => sender() ! Invitations()
    case CreateInvitation(_, _) => sender() ! InvitationCreated
  }
}

object Invites {
  def props = Props[Invites]

  case object InvitationsQuery
  case class Invitations()

  case class CreateInvitation(invitee: String, email: String)
  case object InvitationCreated
}
