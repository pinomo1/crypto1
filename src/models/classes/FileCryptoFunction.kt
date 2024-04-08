package models.classes

import models.abstracts.StreamCryptoFunction
import models.statics.FileHelper
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class FileCryptoFunction<T: StreamCryptoFunction<*, T2>, T2>(val cryptoFunction: T) {
    fun fileOperation(fileName: String, isEncrypt: Boolean, iv: T2?) {
        val postfix = if (isEncrypt) "_enc" else "_dec"
        val originalFile = File(fileName)
        val resultFile = File(FileHelper.appendToFileName(originalFile, postfix))
        val inputStream: InputStream = originalFile.inputStream()
        val outputStream: OutputStream = resultFile.outputStream()
        val fileByteSize = originalFile.length()
        cryptoFunction.streamOperation(inputStream, outputStream, fileByteSize, isEncrypt, iv)
        inputStream.close()
        outputStream.close()
    }

    fun decryptFile(fileName: String, iv: T2? = null){
        fileOperation(fileName, false, iv)
    }

    fun encryptFile(fileName: String){
        fileOperation(fileName, true, null)
    }
}