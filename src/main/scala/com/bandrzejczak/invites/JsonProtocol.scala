package com.bandrzejczak.invites

import com.bandrzejczak.invites.Invites.Invitation
import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {
  implicit val InvitationFormat = jsonFormat2(Invitation)
}
