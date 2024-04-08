import models.abstracts.CryptoFunction
import models.classes.crypto_functions.CaesarFunction
import models.classes.crypto_functions.MonoalphabeticFunction
import models.classes.crypto_functions.VigenereFunction
import models.classes.file_crypto_functions.CBC
import models.classes.file_crypto_functions.ECB
import models.statics.CryptoFunctionTester
import models.statics.FileHelper

fun getEncryptDecryptPair(function: CryptoFunction<*>, text: String) : Pair<String, String> {
    val encrypted = function.encrypt(text)
    val decrypted = function.decrypt(encrypted)
    return Pair(encrypted, decrypted)
}

fun main(){
    val vigenere = VigenereFunction()
    val caesar = CaesarFunction()
    val monoalphabetic = MonoalphabeticFunction()

    val functions = arrayOf(vigenere, caesar, monoalphabetic)

    if(!functions.all { CryptoFunctionTester.test(it) }){
        throw Exception("1 or more functions don't work as expected.")
    }

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
    ecb.encryptFile(yourFileName)
    ecb.decryptFile(FileHelper.appendToFileName(yourFile, "_ecb_encrypted"))
}