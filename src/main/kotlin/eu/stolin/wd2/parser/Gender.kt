package eu.stolin.wd2.parser

enum class Gender(val value: String) {
    MALE("M"),
    FEMALE("F");

    companion object {
        fun fromString(value: String): Gender {
            return values().single { it.value == value }
        }
    }
}