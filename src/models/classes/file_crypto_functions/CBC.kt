package models.classes.file_crypto_functions

import models.abstracts.CryptoFunction
import models.abstracts.FileCryptoFunction
import models.exceptions.InvalidBlockSizeException
import models.exceptions.InvalidIVException
import models.interfaces.IHasBlockSize
import models.statics.FileHelper
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.security.SecureRandom
import kotlin.experimental.xor

class CBC<T : CryptoFunction<*>>(override val cryptoFunction: T, override var blockSize: Int = 64) : FileCryptoFunction<T, ByteArray>(cryptoFunction), IHasBlockSize {
    override fun decryptFile(fileName: String, iv: ByteArray?) {
        TODO()
    }

    override fun encryptFile(fileName: String) : ByteArray {
        TODO()
    }
}