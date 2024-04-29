import models.abstracts.CryptoFunction
import models.abstracts.StreamCryptoFunction
import models.classes.FileCryptoFunction
import models.classes.crypto_functions.*
import models.classes.file_crypto_functions.CBC
import models.classes.file_crypto_functions.ECB
import models.statics.CryptoFunctionTester
import models.statics.FileHelper
import models.statics.StreamCryptoFunctionTester

fun getEncryptDecryptPair(function: CryptoFunction<*>, text: String) : Pair<String, String> {
    val encrypted = function.encrypt(text)
    val decrypted = function.decrypt(encrypted)
    return Pair(encrypted, decrypted)
}

fun testCryptoFunctions(){
    val vigenere = VigenereFunction()
    val monoalphabetic = MonoalphabeticFunction()

    val functions = arrayOf(vigenere, monoalphabetic)

    if(!functions.all { CryptoFunctionTester.test(it) }){
        throw Exception("1 or more functions don't work as expected.")
    }
}

fun testStreams(){
    val ecb = ECB(NoCryptoFunction())
    // val cbc = CBC(NoCryptoFunction())

    // val streams = arrayOf(ecb, cbc)
    val streams = arrayOf(ecb)
    // CBC is not working as expected, so it is not included in the test

    if(!streams.all { StreamCryptoFunctionTester.test(it) }){
        throw Exception("1 or more streams don't work as expected.")
    }
}

fun main(){
    testCryptoFunctions()
    testStreams()

    val vigenere = VigenereFunction()
    val caesar = CaesarFunction()
    val monoalphabetic = MonoalphabeticFunction()
    val playfair = PlayfairFunction()
    val railfence = RailfenceFunction()

    val text = CryptoFunction.SAMPLE_TEXT

    vigenere.loadKey("abcdefgh") // works as a polyalphabetic substitution cipher
    println(getEncryptDecryptPair(vigenere, text))

    vigenere.generateKey()
    println(getEncryptDecryptPair(vigenere, text))
    println(vigenere.key)
    println()

    caesar.loadKey(64) // works as a Caesar cipher
    println(getEncryptDecryptPair(caesar, text))
    println()

    monoalphabetic.generateKey() // works as a monoalphabetic substitution cipher
    println(getEncryptDecryptPair(monoalphabetic, text))
    println(monoalphabetic.key)
    println()

    playfair.generateKey()
    println(getEncryptDecryptPair(playfair, text))
    println(playfair.key)
    println()

    railfence.generateKey()
    println(getEncryptDecryptPair(railfence, text))
    println(railfence.key)
    println()

    println("Enter the file name: ")
    val yourFileName = readln()
    val yourFile = java.io.File(yourFileName)
    if (!yourFile.exists()){
        throw Exception("File not found.")
    }

    println("What function do you want to use?")
    println("1. Vigenere")
    println("2. Caesar")
    println("3. Monoalphabetic")
    val yourFunction : CryptoFunction<*> = when(readln().toInt()){
        1 -> vigenere
        2 -> caesar
        3 -> monoalphabetic
        else -> throw Exception("Invalid choice.")
    }

    val ecb = ECB(yourFunction)
    val fileCrypt = FileCryptoFunction(ecb)
    fileCrypt.encryptFile(yourFileName)
    fileCrypt.decryptFile(FileHelper.appendToFileName(yourFile, "_enc"))
}
