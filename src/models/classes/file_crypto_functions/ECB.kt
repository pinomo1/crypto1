package models.classes.file_crypto_functions

import models.abstracts.CryptoFunction
import models.abstracts.StreamCryptoFunction
import models.exceptions.InvalidBlockSizeException
import models.interfaces.IHasBlockSize
import models.statics.FileHelper
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ECB<T : CryptoFunction<*>>(override val cryptoFunction: T, override var blockSize: Int = 64) : StreamCryptoFunction<T, Unit>(cryptoFunction), IHasBlockSize {
    override fun streamOperation(inputStream: InputStream, outputStream: OutputStream, byteLength: Long, isEncrypt: Boolean, iv: Unit?) {
        if (!this.isBlockSizeValid()) {
            throw InvalidBlockSizeException()
        }
        var buffer = ByteArray(blockSize)
        var i : Long = 0
        while (inputStream.read(buffer) > 0) {
            if (i + blockSize > byteLength) {
                val tmpBuffer = ByteArray((byteLength - i).toInt())
                buffer.copyInto(tmpBuffer, 0, 0, (byteLength - i).toInt())
                buffer = tmpBuffer
            }
            val str = buffer.toString(Charsets.UTF_8)
            val modifiedString = if (isEncrypt) cryptoFunction.encrypt(str) else cryptoFunction.decrypt(str)
            outputStream.write(modifiedString.toByteArray())
            buffer.fill(0)
            i+=blockSize
        }
    }
}