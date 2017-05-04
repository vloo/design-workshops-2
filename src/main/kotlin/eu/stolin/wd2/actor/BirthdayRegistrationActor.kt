package eu.stolin.wd2.actor

import akka.actor.ActorRef
import akka.actor.UntypedActor


class BirthdayRegistrationActor : UntypedActor() {

    var personsToNotify = mutableSetOf<ActorRef>()

    override fun onReceive(message: Any) {
        when (message) {
            is Register -> {
                personsToNotify.add(message.ref)
                println("${message.personName} is interested in birthdays")
            }
            is Birthday -> personsToNotify.forEach { it.tell(message, self) }
            is Deregister -> {
                personsToNotify.remove(message.ref)
                println("${message.personName} is't interested in birthdays")
            }
        }
    }
}