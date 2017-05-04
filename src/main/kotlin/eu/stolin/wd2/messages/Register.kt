package eu.stolin.wd2.messages

import akka.actor.ActorRef

data class Register(val ref: ActorRef, val personName: String)