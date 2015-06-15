package com.bandrzejczak.invites

import akka.actor.{Actor, Props}
import com.bandrzejczak.invites.Invites.{Invitations, InvitationsQuery}

class Invites extends Actor {
  override def receive: Receive = {
    case InvitationsQuery => sender() ! Invitations()
  }
}

object Invites {
  def props = Props[Invites]

  case object InvitationsQuery
  case class Invitations()
}
