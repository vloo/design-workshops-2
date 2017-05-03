package eu.stolin.wd2

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import eu.stolin.wd2.actor.PersonActor
import eu.stolin.wd2.actor.Register
import eu.stolin.wd2.actor.Wedding
import eu.stolin.wd2.actor.WeddingRegistrationActor

fun main(args: Array<String>) {

    println("Enter persons:")
    val personsInput = readLine()!!
    val persons = PersonParser.parsePersons(personsInput)

    val system: ActorSystem = ActorSystem.create("person-actor-system")

    var personsMap: Map<String, ActorRef> = mutableMapOf()
    persons.forEach {
        val actorRef = system.actorOf(Props.create(PersonActor::class.java, it), it.name)
        personsMap += Pair(it.name, actorRef)
    }

    val weddingRegistrationActorRef = system.actorOf(Props.create(WeddingRegistrationActor::class.java))


    do {
        println("Enter commands:")
        val commandInput = readLine()!!
        val command = CommandParser.parseCommand(commandInput)
        when (command.type) {
            CommandType.WEDDING_INTEREST_REGISTRATION -> {
                val actorRef = personsMap[command.personName]
                if (actorRef != null) {
                    weddingRegistrationActorRef.tell(Register(actorRef), weddingRegistrationActorRef)
                }
            }
            CommandType.WEDDING_EVENT -> {
                weddingRegistrationActorRef.tell(Wedding(command.personName), weddingRegistrationActorRef)
            }
        }

    } while (commandInput.toUpperCase() != "DONE")
}