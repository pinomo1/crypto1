package models.classes.stream_crypto_functions

import models.abstracts.CryptoFunction
import models.abstracts.StreamCryptoFunction
import models.exceptions.InvalidBlockSizeException
import models.exceptions.InvalidIVException
import models.interfaces.IHasBlockSize
import java.io.InputStream
import java.io.OutputStream
import kotlin.experimental.xor

class CBC<T : CryptoFunction<*>>(override val cryptoFunction: T, override var blockSize: Int = 64) : StreamCryptoFunction<T, ByteArray>(cryptoFunction), IHasBlockSize {
    init {
        if (!this.isBlockSizeValid()) {
            throw InvalidBlockSizeException()
        }
    }

    override fun decryptStream(inputStream: InputStream, outputStream: OutputStream, iv: ByteArray?) {
        if (iv == null || iv.size != blockSize) {
            throw InvalidIVException()
        }
        var previousCiphertextBlock : ByteArray = iv
        val buffer = ByteArray(blockSize)
        while (inputStream.read(buffer) > 0) {
            val decryptedBlock = cryptoFunction.decrypt(buffer.toString(Charsets.UTF_8)).toByteArray()
            val plaintextBlock = decryptedBlock.zip(previousCiphertextBlock) { a, b -> a xor b }.toByteArray()
            outputStream.write(plaintextBlock)
            previousCiphertextBlock = buffer
        }
    }

    override fun encryptStream(inputStream: InputStream, outputStream: OutputStream) : ByteArray {
        val iv = generateIV()
        var previousCiphertextBlock = iv
        val buffer = ByteArray(blockSize)
        while (inputStream.read(buffer) > 0) {
            val xorBlock = buffer.zip(previousCiphertextBlock) { a, b -> a xor b }.toByteArray()
            val ciphertextBlock = cryptoFunction.encrypt(xorBlock.toString(Charsets.UTF_8)).toByteArray()
            outputStream.write(ciphertextBlock)
            previousCiphertextBlock = ciphertextBlock
        }
        return iv
    }

    fun generateIV() : ByteArray {
        val iv = ByteArray(blockSize)
        val random = java.security.SecureRandom()
        random.nextBytes(iv)
        return iv
    }
}