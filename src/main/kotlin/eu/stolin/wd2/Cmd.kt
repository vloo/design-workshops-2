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

    val commandActorRef: ActorRef = system.actorOf(Props.create(CommandActor::class.java, persons), "command-actor")


    while(true) {
        println("Enter commands:")
        val commandInput = readLine()!!

        if (commandInput.toUpperCase() == "DONE") {
            break
        }

        commandActorRef.tell(Line(commandInput), commandActorRef)
    }

    system.terminate()
    system.awaitTermination()

}
