package eu.stolin.wd2.messages

import akka.actor.ActorRef


class Deregister (val ref: ActorRef, val personName: String)