package eu.stolin.wd2.actor

import akka.actor.UntypedActor
import eu.stolin.wd2.Gender
import eu.stolin.wd2.Person


class PersonActor(val person: Person) : UntypedActor() {


    override fun onReceive(message: Any) {
        when (message) {
            is Wedding -> writeWedding(message)
            is Birthday -> writeBirthday(message)
        }
    }

    private fun writeWedding(message: Wedding) {
        when (person.gender) {
            Gender.FEMALE -> println("${person.name} says: all the best ${message.person.name}, wish you a wonderful life")
            Gender.MALE -> println("${person.name} says: oops ${message.person.name} has a birthday, one more step to the grave")
        }
    }

    private fun writeBirthday(message: Birthday) {
        when (person.gender) {
            Gender.FEMALE -> println("${person.name} says: yaaay, ${message.person.name} is so lucky to be married now")
            Gender.MALE -> println("${person.name} says: poor ${message.person.name}, the freedom is gone")
        }
    }
}