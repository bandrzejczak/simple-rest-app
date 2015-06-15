package com.bandrzejczak.invites

import akka.actor.{Actor, Props}
import com.bandrzejczak.invites.Invites._

class Invites extends Actor {

  var invitations = Map[String, Invitation]()

  override def receive: Receive = {
    case InvitationsQuery => sender() ! Invitations(invitations.values.toList)
    case CreateInvitation(Invitation(_, email)) if invitations.contains(email) =>
      sender() ! InvitationAlreadyExists
    case CreateInvitation(invitation @ Invitation(_, email)) =>
      invitations += email -> invitation
      sender() ! InvitationCreated
  }
}

object Invites {
  def props = Props[Invites]

  case object InvitationsQuery
  case class Invitations(invitations: List[Invitation])
  object Invitations {
    def apply(invitations: Invitation*): Invitations =
      Invitations(invitations.toList)
  }
  case class Invitation(invitee: String, email: String)

  case class CreateInvitation(invitation: Invitation)
  object CreateInvitation {
    def apply(invitee: String, email: String): CreateInvitation =
      CreateInvitation(Invitation(invitee, email))
  }
  case object InvitationCreated
  case object InvitationAlreadyExists
}
