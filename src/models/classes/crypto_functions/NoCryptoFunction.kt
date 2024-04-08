package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class NoCryptoFunction : CryptoFunction<Unit>() {
    override fun crypt(str: String, isEncrypt: Boolean): String{
        return str
    }

    override fun generateKey() {
        loadKey(Unit)
    }

    override fun isValidForKey(potentialKey: Unit): Boolean {
        return true
    }
}