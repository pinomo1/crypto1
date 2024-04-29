package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class RailFenceFunction : CryptoFunction<Int>() {

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
        val decrypted = CharArray(str.length)
        val cycleLength = 2 * (key - 1)
        var index = 0

        for (row in 0 until key) {
            var i = row
            val stepDown = 2 * (key - row - 1)
            val stepUp = cycleLength - stepDown

            var down = true
            while (i < str.length) {
                decrypted[i] = str[index++]

                if (stepDown != 0 && stepUp != 0) {
                    i += if (down) stepDown else stepUp
                    down = !down
                } else if (stepDown == 0) {
                    i += stepUp
                } else {
                    i += stepDown
                }
            }
        }

        return String(decrypted)
    }

    override fun generateKey(): Int {
        return (2..10).random()
    }

    override fun isValidForKey(potentialKey: Int): Boolean {
        return potentialKey >= 2
    }
}