package classes

import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ECB<T : CryptoFunction<*>>(override val cryptoFunction: T) : FileCryptoFunction<T>(cryptoFunction) {
    override fun decryptFile(fileName: String) {
        val encryptedFile = File(fileName)
        val decryptedFile = File(FileHelper.appendToFileName(encryptedFile, "_decrypted"))
        val inputStream: InputStream = encryptedFile.inputStream()
        val outputStream: OutputStream = decryptedFile.outputStream()
        val buffer = ByteArray(64)
        while (inputStream.read(buffer) > 0) {
            val str = buffer.toString(Charsets.UTF_8)
            val decryptedStr = cryptoFunction.decrypt(str)
            outputStream.write(decryptedStr.toByteArray())
        }
        inputStream.close()
        outputStream.close()
    }

    override fun encryptFile(fileName: String) {
        val originalFile = File(fileName)
        val encryptedFile = File(FileHelper.appendToFileName(originalFile, "_ecb_encrypted"))
        val inputStream: InputStream = originalFile.inputStream()
        val outputStream: OutputStream = encryptedFile.outputStream()
        val buffer = ByteArray(64)
        while (inputStream.read(buffer) > 0) {
            val str = buffer.toString(Charsets.UTF_8)
            val encryptedStr = cryptoFunction.encrypt(str)
            outputStream.write(encryptedStr.toByteArray())
        }
        inputStream.close()
        outputStream.close()
    }
}