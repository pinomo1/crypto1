package models.exceptions

class InvalidBlockSizeException : Exception() {
    override val message: String
        get() = "Block size must be valid."
}