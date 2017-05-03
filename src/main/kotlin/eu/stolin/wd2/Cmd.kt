package eu.stolin.wd2

import akka.actor.ActorSystem
import akka.actor.Props
import eu.stolin.wd2.actor.PersonActor

fun main(args: Array<String>) {

    println("Enter persons:")
    val personsInput = readLine()!!
    val persons = PersonParser.parsePersons(personsInput)

    val system: ActorSystem = ActorSystem.create("person-actor-system")

    val actorRefs = persons.map {
        system.actorOf(Props.create(PersonActor::class.java, it), it.name)
    }


    do {
        println("Enter commands:")
        val commandInput = readLine()!!


    } while (commandInput.toUpperCase() != "DONE")
}