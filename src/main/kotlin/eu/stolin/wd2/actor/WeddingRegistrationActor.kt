package eu.stolin.wd2.actor

import akka.actor.ActorRef
import akka.actor.UntypedActor
import eu.stolin.wd2.messages.Deregister
import eu.stolin.wd2.messages.Register
import eu.stolin.wd2.messages.Wedding


class WeddingRegistrationActor : UntypedActor() {

    var personsToNotify = mutableSetOf<ActorRef>()

    override fun onReceive(message: Any) {
        when (message) {
            is Register -> {
                personsToNotify.add(message.ref)
                println("${message.personName} is interested in weddings")
            }
            is Wedding -> personsToNotify.forEach { it.tell(message, self) }
            is Deregister -> {
                personsToNotify.remove(message.ref)
                println("${message.personName} is't interested in weddings")
            }
        }
    }
}