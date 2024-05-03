import models.abstracts.CryptoFunction
import models.classes.crypto_functions.*

fun getEncryptDecryptPair(function: CryptoFunction<*>, text: String) : Pair<String, String> {
    val encrypted = function.encrypt(text)
    val decrypted = function.decrypt(encrypted)
    return Pair(encrypted, decrypted)
}

fun main(){
    val miniRSADecryption = MiniRSA()

    val miniRSAEncryption = MiniRSA()
    miniRSAEncryption.loadKey(Triple(miniRSADecryption.key.first, 0, miniRSADecryption.key.third))

    val text = "Hello, World!"

    val encryptedRSA = miniRSAEncryption.encrypt(text)

    val decryptedRSA = miniRSADecryption.decrypt(encryptedRSA)
    println(miniRSADecryption.key)

    println("Original: $text")
    println("Encrypted: $encryptedRSA")
    println("Decrypted: $decryptedRSA")
    println()

    /*
    val functions : List<CryptoFunction<*>> = listOf(
        CaesarFunction(),
        VigenereFunction(),
        MiniRSA(),
        MonoalphabeticFunction(),
        PlayfairFunction(),
        RailFenceFunction()
    )


    functions.forEach { function ->
        val (encrypted, decrypted) = getEncryptDecryptPair(function, CryptoFunction.SAMPLE_TEXT)
        println("Original: ${CryptoFunction.SAMPLE_TEXT}")
        println("Encrypted: $encrypted")
        println("Decrypted: $decrypted")
        println("Key: ${function.key}")
        println("Function: ${function::class.simpleName}")
        println()
    }
    */
}
