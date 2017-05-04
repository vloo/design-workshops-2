package eu.stolin.wd2.actor

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import eu.stolin.wd2.parser.CommandParser
import eu.stolin.wd2.parser.CommandType
import eu.stolin.wd2.parser.Person
import eu.stolin.wd2.messages.Birthday
import eu.stolin.wd2.messages.Line
import eu.stolin.wd2.messages.Register
import eu.stolin.wd2.messages.Wedding


class CommandActor : UntypedActor {

    val system: ActorSystem = context.system()
    var personsMap: Map<String, ActorRef> = mutableMapOf()
    val weddingRegistrationActorRef: ActorRef = system.actorOf(Props.create(WeddingRegistrationActor::class.java))
    val birthdayRegistrationActorRef: ActorRef = system.actorOf(Props.create(BirthdayRegistrationActor::class.java))

    constructor(persons: Collection<Person>) {
        persons.forEach {
            val actorRef = system.actorOf(Props.create(PersonActor::class.java, it), it.name)
            personsMap += Pair(it.name, actorRef)
        }
    }

    override fun onReceive(message: Any) {
        when (message) {
            is Line -> {
                executeCommand(message.input)
            }
        }
    }

    private fun executeCommand(commandInput: String) {
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
    }
}