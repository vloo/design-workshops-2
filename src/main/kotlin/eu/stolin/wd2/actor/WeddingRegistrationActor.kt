package eu.stolin.wd2.actor

import akka.actor.ActorRef
import akka.actor.UntypedActor


class WeddingRegistrationActor : UntypedActor() {

    var personsToNotify = mutableSetOf<ActorRef>()

    override fun onReceive(message: Any) {
        when (message) {
            is Register -> personsToNotify.add(message.ref)
            is Wedding -> personsToNotify.forEach { it.tell(message, self)}
            is Deregister -> personsToNotify.remove(message.ref)
        }
    }
}