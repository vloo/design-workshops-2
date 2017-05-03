package eu.stolin.wd2

object PersonParser {

    fun parsePersons(line: String): Collection<Person> {
        return line.split(",").map { parsePerson(it) }
    }

    private fun parsePerson(person: String): Person {
        val pair = person.split(":")
        if (pair.size != 2) {
            throw RuntimeException("unable to parse person: " + person)
        }
        val gender = Gender.fromString(pair.first())
        return Person(gender, pair[1])
    }
}