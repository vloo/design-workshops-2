package eu.stolin.wd2

object CommandParser {

    //todo: vs: pekna prasarna
    fun parseCommand(line: String): Command {
        if (line.endsWith("wedding")) {
            if (line.contains(":")) {
                return Command(CommandType.WEDDING_EVENT, line.split(":").first())
            } else if (line.contains("->")) {
                return Command(CommandType.WEDDING_INTEREST_REGISTRATION, line.split("->").first())
            } else {
                return Command(CommandType.UNKNOWN, "")
            }
        } else if (line.endsWith("birthday")) {
            if (line.contains(":")) {
                return Command(CommandType.BIRTHDAY_EVENT, line.split(":").first())
            } else if (line.contains("->")) {
                return Command(CommandType.BIRTHDAY_INTEREST_REGISTRATION, line.split("->").first())
            } else {
                return Command(CommandType.UNKNOWN, "")
            }
        } else if (line.toUpperCase() == "DONE") {
            return Command(CommandType.DONE, "")
        } else {
            return Command(CommandType.UNKNOWN, "")
        }
    }
}