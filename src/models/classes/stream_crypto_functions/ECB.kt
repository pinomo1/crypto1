package models.classes.stream_crypto_functions

import models.abstracts.CryptoFunction
import models.abstracts.StreamCryptoFunction
import models.exceptions.InvalidBlockSizeException
import models.interfaces.IHasBlockSize
import java.io.InputStream
import java.io.OutputStream

class ECB<T : CryptoFunction<*>>(override val cryptoFunction: T, override var blockSize: Int = 64) : StreamCryptoFunction<T, Unit>(cryptoFunction), IHasBlockSize {
    init {
        if (!this.isBlockSizeValid()) {
            throw InvalidBlockSizeException()
        }
    }

    override fun streamOperation(inputStream: InputStream, outputStream: OutputStream, isEncrypt: Boolean, iv: Unit?) {
        val buffer = ByteArray(blockSize)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } > 0) {
            val str = buffer.take(bytesRead).toByteArray().toString(Charsets.UTF_8)
            val modifiedString = if (isEncrypt) cryptoFunction.encrypt(str) else cryptoFunction.decrypt(str)
            outputStream.write(modifiedString.toByteArray())
        }
    }
}