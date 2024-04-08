package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class MonoalphabeticFunction : CryptoFunction<Map<Char, Char>>() {
    override fun crypt(str: String, isEncrypt: Boolean): String {
        var copyStr = str
        copyStr = copyStr.lowercase()
        var result = ""
        var dict = key
        if (isEncrypt) {
            dict = dict.entries.associate { (k, v) -> v to k }
        }
        copyStr.forEach { c ->
            result += if (c in 'a'..'z') {
                dict[c]
            } else {
                c
            }
        }
        return result
    }

    override fun generateKey(): Map<Char, Char> {
        val alphabet = ('a'..'z').toList()
        val scrambledAlphabet = alphabet.shuffled()
        val key = alphabet.zip(scrambledAlphabet).toMap()
        loadKey(key)
        return this.key
    }

    override fun isValidForKey(potentialKey: Map<Char, Char>): Boolean {
        val lowerAlphabet = ('a'..'z').toList()
        return potentialKey.keys.all { it in lowerAlphabet } && potentialKey.values.all { it in lowerAlphabet } && potentialKey.keys.size == 26
    }

}