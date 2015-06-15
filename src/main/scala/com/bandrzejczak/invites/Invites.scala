package com.bandrzejczak.invites

import akka.actor.{Actor, Props}
import com.bandrzejczak.invites.Invites._

class Invites extends Actor {

  var invitations = Set[String]()

  override def receive: Receive = {
    case InvitationsQuery => sender() ! Invitations()
    case CreateInvitation(email, _) if invitations.contains(email) =>
      sender() ! InvitationAlreadyExists
    case CreateInvitation(email, _) =>
      invitations += email
      sender() ! InvitationCreated
  }
}

object Invites {
  def props = Props[Invites]

  case object InvitationsQuery
  case class Invitations()

  case class CreateInvitation(invitee: String, email: String)
  case object InvitationCreated
  case object InvitationAlreadyExists
}
