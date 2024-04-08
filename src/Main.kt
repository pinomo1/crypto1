import classes.*

fun getEncryptDecryptPair(function: CryptoFunction<*>, text: String) : Pair<String, String> {
    val encrypted = function.encrypt(text)
    val decrypted = function.decrypt(encrypted)
    return Pair(encrypted, decrypted)
}

fun main(){
    val substitution = SubstitutionFunction()
    val caesar = CaesarFunction()

    val functions = arrayOf(substitution, caesar)

    if(!functions.all { CryptoFunctionTester.test(it) }){
        throw Exception("1 or more functions don't work as expected.")
    }

    val text = CryptoFunction.SAMPLE_TEXT

    substitution.loadKey("abcdefgh") // works as a polyalphabetic substitution cipher
    println(getEncryptDecryptPair(substitution, text))

    substitution.generateKey()
    println(getEncryptDecryptPair(substitution, text))
    println(substitution.key)

    caesar.loadKey(3) // works as a Caesar cipher
    println(getEncryptDecryptPair(caesar, text))

    caesar.generateKey()
    println(getEncryptDecryptPair(caesar, text))
    println(caesar.key)

    val yourFileName : String = "C:\\Users\\Nail SH\\Desktop\\file.txt"
    val yourFile = java.io.File(yourFileName)

    val ecb = ECB(caesar)
    ecb.encryptFile(yourFileName)
    ecb.decryptFile(FileHelper.appendToFileName(yourFile, "_ecb_encrypted"))

    val cbc = CBC(caesar)
    cbc.encryptFile(yourFileName)
    cbc.decryptFile(FileHelper.appendToFileName(yourFile, "_cbc_encrypted"))
}