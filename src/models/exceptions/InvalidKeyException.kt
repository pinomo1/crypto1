package models.exceptions

class InvalidKeyException : Exception() {
    override val message: String
        get() = "Invalid key"
}