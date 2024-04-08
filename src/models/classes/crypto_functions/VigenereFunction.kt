package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class VigenereFunction : CryptoFunction<String>() {
    override fun crypt(str: String, isEncrypt: Boolean): String{
        var copyStr = str
        copyStr = copyStr.lowercase()
        var result = ""
        copyStr.forEachIndexed { i, c ->
            if (c in 'a'..'z'){
                val keyInt = key[i% key.length].code - 97
                var newChar = c + (if (isEncrypt) 1 else -1) * keyInt
                if ((newChar < 'a' && !isEncrypt) || (newChar > 'z' && isEncrypt)){
                    newChar -= (if (isEncrypt) 1 else -1) * 26
                }
                result += newChar
            } else {
                result += c
            }
        }
        return result
    }

    override fun generateKey(): String {
        val rand = (1..64).map { ('a'..'z').random() }.joinToString("")
        loadKey(rand)
        return key
    }

    override fun isValidForKey(potentialKey: String): Boolean {
        val lowerAlphabet = ('a'..'z').toList()
        return potentialKey.all { it in lowerAlphabet }
    }
}