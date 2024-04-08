package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class CaesarFunction : CryptoFunction<Int>() {
    override fun crypt(str: String, isEncrypt: Boolean): String{
        var copyStr = str
        if (isEncrypt){
            copyStr = copyStr.lowercase()
        }
        var result = ""
        copyStr.forEachIndexed { i, c ->
            val newChar = c + (if (isEncrypt) 1 else -1) * key
            result += newChar
        }
        return result
    }

    override fun generateKey(): Int {
        val rand = (1..1024).random()
        loadKey(rand)
        return key
    }

    override fun isValidForKey(potentialKey: Int): Boolean {
        return true
    }
}