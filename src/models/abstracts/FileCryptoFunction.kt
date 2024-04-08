package models.abstracts

// override either fileOperation or decryptFile and encryptFile
abstract class FileCryptoFunction<T : CryptoFunction<*>, T2>(open val cryptoFunction: T) {
    open fun fileOperation(fileName: String, isEncrypt: Boolean, iv: T2? = null) : T2? {
        if (isEncrypt){
            return encryptFile(fileName)
        }
        decryptFile(fileName, iv)
        return null
    }

    open fun decryptFile(fileName: String, iv: T2? = null){
        fileOperation(fileName, false, iv)
    }

    open fun encryptFile(fileName: String) : T2? {
        return fileOperation(fileName, true)
    }
}