package models.classes.file_crypto_functions

import models.abstracts.CryptoFunction
import models.abstracts.StreamCryptoFunction
import models.interfaces.IHasBlockSize
import java.io.InputStream
import java.io.OutputStream

class CBC<T : CryptoFunction<*>>(override val cryptoFunction: T, override var blockSize: Int = 64) : StreamCryptoFunction<T, ByteArray>(cryptoFunction), IHasBlockSize {
    override fun decryptStream(inputStream: InputStream, outputStream: OutputStream, byteLength: Long, iv: ByteArray?) {
        TODO()
    }

    override fun encryptStream(inputStream: InputStream, outputStream: OutputStream, byteLength: Long) : ByteArray {
        TODO()
    }
}