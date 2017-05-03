package eu.stolin.wd2

fun main(args: Array<String>) {

    println("Enter persons:")
    val personsInput = readLine()!!
    val persons = PersonParser.parsePersons(personsInput)

    do {
        println("Enter commands:")
        val commandInput = readLine()!!


    } while (commandInput.toUpperCase() != "DONE")
}