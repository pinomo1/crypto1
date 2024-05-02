package models.exceptions

class PrivateKeyNotSet : Exception() {
    override val message: String
        get() = "Private key not set"
}