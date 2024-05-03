package models.classes.crypto_functions

import models.abstracts.CryptoFunction
import models.exceptions.PrivateKeyNotSet

class MiniRSA : CryptoFunction<Triple<Int, Int, Int>>() {
    private fun isPrime(n: Int): Boolean {
        if (n <= 1) return false
        if (n <= 3) return true
        if (n % 2 == 0 || n % 3 == 0) return false
        var i = 5
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) return false
            i += 6
        }
        return true
    }

    private fun isRelativePrime(a: Int, b: Int): Boolean {
        var temp: Int
        var x = a
        var y = b
        while (y != 0) {
            temp = x
            x = y
            y = temp % y
        }
        return x == 1
    }

    // TODO: if triple is 241, 61, 341, then ciphertext is the same as the plaintext; fix?
    override fun generateKey(): Triple<Int, Int, Int> {
        var isSuccess : Boolean
        var e = 0
        var d = 0
        var n = 0
        do {
            try {
                isSuccess = true
                var p : Int
                var q : Int
                do {
                    p = (10..99).random()
                    q = (10..99).random()
                } while (!isPrime(p) || !isPrime(q) || q == p)
                n = p * q

                val phi = (p - 1) * (q - 1)

                do {
                    e = (2..<phi).random()
                } while (!isRelativePrime(e, phi))

                d = (2..phi).last { (it * e) % phi == 1 && it != e }
            }
            catch (ex : NoSuchElementException){
                isSuccess = false
            }
        }while (!isSuccess)

        loadKey(Triple(e, d, n))

        return Triple(e, d, n)
    }

    override fun isValidForKey(potentialKey: Triple<Int, Int, Int>): Boolean {
        return potentialKey.first != 0 && potentialKey.third != 0
    }

    override fun crypt(str: String, isEncrypt: Boolean): String {
        val ed = if (isEncrypt) key.first else key.second
        if (ed == 0){
            throw PrivateKeyNotSet()
        }
        val n = key.third
        var cipherText = ""
        for (i in str.indices) {
            val ch = str[i]
            val temp = ch.code
            var k = 1
            for (j in 0..<ed) {
                k *= temp
                k %= n
            }
            cipherText += k.toChar()
        }
        return cipherText
    }
}