package models.statics

import models.abstracts.CryptoFunction
import models.abstracts.StreamCryptoFunction
import models.classes.crypto_functions.NoCryptoFunction
import java.io.ByteArrayOutputStream

class StreamCryptoFunctionTester {
    companion object {
        fun <T> test(streamCryptoFunction: StreamCryptoFunction<*, T>) : Boolean{
            val testString : String = CryptoFunction.SAMPLE_TEXT
            val inputStream = testString.byteInputStream()
            val intermediateStream = ByteArrayOutputStream()
            val outputStream = ByteArrayOutputStream()
            val iv : T? = streamCryptoFunction.encryptStream(inputStream, intermediateStream)
            val intermediateString = String(intermediateStream.toByteArray())
            val intermediateInputStream = intermediateStream.toByteArray().inputStream()
            streamCryptoFunction.decryptStream(intermediateInputStream, outputStream, iv)
            val finalString = String(outputStream.toByteArray())
            print("Testing ${streamCryptoFunction.javaClass.simpleName}... ")
            println("Result: ${testString == finalString}")
            println("Original: $testString")
            println("Intermediate: $intermediateString")
            println("Final: $finalString")
            println()
            return testString == finalString
        }
    }
}