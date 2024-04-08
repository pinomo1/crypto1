package models.classes.file_crypto_functions

import models.abstracts.CryptoFunction
import models.abstracts.FileCryptoFunction
import models.exceptions.InvalidBlockSizeException
import models.interfaces.IHasBlockSize
import models.statics.FileHelper
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ECB<T : CryptoFunction<*>>(override val cryptoFunction: T, override var blockSize: Int = 64) : FileCryptoFunction<T, Unit>(cryptoFunction), IHasBlockSize {
    override fun fileOperation(fileName: String, isEncrypt: Boolean, iv: Unit?) {
        val postfix = if (isEncrypt) "_ecb_encrypted" else "_decrypted"
        if (!this.isBlockSizeValid()) {
            throw InvalidBlockSizeException()
        }
        val originalFile = File(fileName)
        val resultFile = File(FileHelper.appendToFileName(originalFile, postfix))
        val inputStream: InputStream = originalFile.inputStream()
        val outputStream: OutputStream = resultFile.outputStream()
        var buffer = ByteArray(blockSize)
        val fileByteSize = originalFile.length()
        var i = 0
        while (inputStream.read(buffer) > 0) {
            if (i + blockSize > fileByteSize) {
                val tmpBuffer = ByteArray(fileByteSize.toInt() - i)
                buffer.copyInto(tmpBuffer, 0, 0, fileByteSize.toInt() - i)
                buffer = tmpBuffer
            }
            val str = buffer.toString(Charsets.UTF_8)
            val modifiedString = if (isEncrypt) cryptoFunction.encrypt(str) else cryptoFunction.decrypt(str)
            outputStream.write(modifiedString.toByteArray())
            buffer.fill(0)
            i+=blockSize
        }
        inputStream.close()
        outputStream.close()
    }
}