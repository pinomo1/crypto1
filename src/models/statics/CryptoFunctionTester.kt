package models.statics

import models.abstracts.CryptoFunction

class CryptoFunctionTester {
    companion object{
        fun test(cryptoFunction: CryptoFunction<*>) : Boolean{
            cryptoFunction.generateKey()
            val testString : String = CryptoFunction.SAMPLE_TEXT
            val encryptedString = cryptoFunction.encrypt(testString)
            val decryptedString = cryptoFunction.decrypt(encryptedString)
            print("Testing ${cryptoFunction.javaClass.simpleName}... ")
            println("Result: ${testString == decryptedString}")
            println("Original: $testString")
            println("Encrypted: $encryptedString")
            println("Decrypted: $decryptedString")
            println("Key: ${cryptoFunction.key}")
            println()
            return testString == decryptedString
        }
    }
}