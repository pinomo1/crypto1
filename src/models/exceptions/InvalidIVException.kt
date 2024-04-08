package models.exceptions

// must have been used in CBC
class InvalidIVException : Exception() {
    override val message: String
        get() = "IV must be valid."
}