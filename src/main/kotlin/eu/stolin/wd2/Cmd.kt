package eu.stolin.wd2

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import eu.stolin.wd2.actor.*

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

    val birthdayRegistrationActorRef = system.actorOf(Props.create(BirthdayRegistrationActor::class.java))


    do {
        println("Enter commands:")
        val commandInput = readLine()!!
        val command = CommandParser.parseCommand(commandInput)
        when (command.type) {
            CommandType.WEDDING_INTEREST_REGISTRATION -> {
                val actorRef = personsMap[command.personName]
                if (actorRef != null) {
                    weddingRegistrationActorRef.tell(Register(actorRef, command.personName), weddingRegistrationActorRef)
                }
            }
            CommandType.WEDDING_EVENT -> {
                weddingRegistrationActorRef.tell(Wedding(command.personName), weddingRegistrationActorRef)
            }
            CommandType.BIRTHDAY_INTEREST_REGISTRATION -> {
                val actorRef = personsMap[command.personName]
                if (actorRef != null) {
                    birthdayRegistrationActorRef.tell(Register(actorRef, command.personName), birthdayRegistrationActorRef)
                }
            }
            CommandType.BIRTHDAY_EVENT -> {
                birthdayRegistrationActorRef.tell(Birthday(command.personName), birthdayRegistrationActorRef)
            }
            CommandType.UNKNOWN -> {
                println("Unknown command")
            }
        }

    } while (commandInput.toUpperCase() != "DONE")
}