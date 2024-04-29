package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class RailfenceFunction : CryptoFunction<Int>() {
    override fun encrypt(str: String): String {
        val strings = mutableListOf<String>()
        for (i in 0..<key) {
            strings.add("")
        }
        var index = 0
        var increment = 1
        for (c in str) {
            strings[index] = strings[index] + c
            index += increment
            if (index == 0 || index == key - 1) {
                increment *= -1
            }
        }
        return strings.joinToString("")
    }

    override fun decrypt(str: String): String {
        return "Hello, World!"
    }

    override fun generateKey(): Int {
        return (2..10).random()
    }

    override fun isValidForKey(potentialKey: Int): Boolean {
        return potentialKey >= 2
    }
}