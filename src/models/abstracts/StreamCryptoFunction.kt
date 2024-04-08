package models.abstracts

import java.io.InputStream
import java.io.OutputStream

// override either fileOperation or decryptFile and encryptFile
abstract class StreamCryptoFunction<T : CryptoFunction<*>, T2>(open val cryptoFunction: T) {
    open fun streamOperation(inputStream: InputStream, outputStream: OutputStream, byteLength: Long, isEncrypt: Boolean, iv: T2? = null) : T2? {
        if (isEncrypt){
            return encryptStream(inputStream, outputStream, byteLength)
        }
        decryptStream(inputStream, outputStream, byteLength, iv)
        return null
    }

    open fun decryptStream(inputStream: InputStream, outputStream: OutputStream, byteLength: Long, iv: T2? = null){
        streamOperation(inputStream, outputStream, byteLength, false, iv)
    }

    open fun encryptStream(inputStream: InputStream, outputStream: OutputStream, byteLength: Long) : T2? {
        return streamOperation(inputStream, outputStream, byteLength, true)
    }
}