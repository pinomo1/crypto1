package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class MonoalphabeticFunction : CryptoFunction<Map<Char, Char>>() {
    override fun crypt(str: String, isEncrypt: Boolean): String {
        var result = ""
        var dict = key
        if (isEncrypt) {
            dict = dict.entries.associate { (k, v) -> v to k }
        }
        str.forEach { c ->
            result += if (c in (('a'..'z') + ('A'..'Z') + ('0'..'9'))) {
                dict[c]
            } else {
                c
            }
        }
        return result
    }

    override fun generateKey(): Map<Char, Char> {
        val alphabet = (('a'..'z') + ('A'..'Z') + ('0'..'9')).toList()
        val scrambledAlphabet = alphabet.shuffled()
        val key = alphabet.zip(scrambledAlphabet).toMap()
        loadKey(key)
        return this.key
    }

    override fun isValidForKey(potentialKey: Map<Char, Char>): Boolean {
        val lowerAlphabet = (('a'..'z') + ('A'..'Z') + ('0'..'9')).toList()
        return potentialKey.keys.all { it in lowerAlphabet } && potentialKey.values.all { it in lowerAlphabet } && potentialKey.keys.size == 62
    }

}