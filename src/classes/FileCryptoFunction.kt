package classes

abstract class FileCryptoFunction<T : CryptoFunction<*>>(open val cryptoFunction: T) {
    abstract fun decryptFile(fileName: String)

    abstract fun encryptFile(fileName: String)
}