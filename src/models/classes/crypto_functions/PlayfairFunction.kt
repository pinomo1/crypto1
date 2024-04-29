package models.classes.crypto_functions

import models.abstracts.CryptoFunction

class PlayfairFunction : CryptoFunction<String>() {
    override fun crypt(str: String, isEncrypt: Boolean): String {
        val newStr = str.toUpperCase().filter { it in 'A'..'Z' && it != 'J' }
        val pairs = generatePairs(newStr)
        val result = pairs.map { pair ->
            val first = pair.first
            val second = pair.second
            val firstIndex = key.indexOf(first)
            val secondIndex = key.indexOf(second)
            val firstRow = firstIndex / 5
            val firstCol = firstIndex % 5
            val secondRow = secondIndex / 5
            val secondCol = secondIndex % 5
            if (firstRow == secondRow) {
                val newFirstCol = if (isEncrypt) (firstCol + 1) % 5 else (firstCol + 4) % 5
                val newSecondCol = if (isEncrypt) (secondCol + 1) % 5 else (secondCol + 4) % 5
                key[firstRow * 5 + newFirstCol].toString() + key[secondRow * 5 + newSecondCol]
            } else if (firstCol == secondCol) {
                val newFirstRow = if (isEncrypt) (firstRow + 1) % 5 else (firstRow + 4) % 5
                val newSecondRow = if (isEncrypt) (secondRow + 1) % 5 else (secondRow + 4) % 5
                key[newFirstRow * 5 + firstCol].toString() + key[newSecondRow * 5 + secondCol]
            } else {
                key[firstRow * 5 + secondCol].toString() + key[secondRow * 5 + firstCol]
            }
        }
        return result.joinToString("")
    }

    fun generatePairs(str: String) : List<Pair<Char, Char>> {
        val pairs = mutableListOf<Pair<Char, Char>>()
        var i = 0
        while (i < str.length) {
            val first = str[i]
            val second = if (i + 1 < str.length) str[i + 1] else 'X'
            if (first == second) {
                pairs.add(Pair(first, 'X'))
                i++
            } else {
                pairs.add(Pair(first, second))
                i += 2
            }
        }
        return pairs
    }

    override fun generateKey(): String {
        val alphabetWithoutJ = ('A'..'Z').filter { it != 'J' }
        loadKey(alphabetWithoutJ.shuffled().joinToString(""))
        return key
    }

    override fun isValidForKey(potentialKey: String): Boolean {
        return potentialKey.length == 25 && potentialKey.all { it in 'A'..'Z' && it != 'J' }
    }

    override fun loadKey(key: String) {
        val uniqueKey = key.toUpperCase()
            .take(25)
            .filter { it in 'A'..'Z' }
            .map { if (it == 'J') 'I' else it }
            .distinct()
            .joinToString("")
        val alphabetWithoutJ = ('A'..'Z').filter { it != 'J' }
        val restOfAlphabet = alphabetWithoutJ.filter { it !in uniqueKey }.joinToString("")
        val finalKey = uniqueKey + restOfAlphabet
        super.loadKey(finalKey)
    }
}