package models.abstracts

import models.exceptions.InvalidKeyException

// override either crypt or decrypt and encrypt
abstract class CryptoFunction<T> {
    companion object{
        const val SAMPLE_TEXT = "the quick brown fox jumps over the lazy dog, lorem ipsum dolor sit amet 3.1415926535, hello world! how are you?"
    }

    var key : T = generateKey()
        protected set

    open fun crypt(str: String, isEncrypt: Boolean): String{
        return if (isEncrypt) encrypt(str) else decrypt(str)
    }

    open fun decrypt(str: String): String {
        return crypt(str, false)
    }

    open fun encrypt(str: String): String {
        return crypt(str, true)
    }

    abstract fun generateKey(): T;

    abstract fun isValidForKey(potentialKey: T): Boolean;

    open fun loadKey(key: T){
        if (!isValidForKey(key)){
            throw InvalidKeyException()
        }
        this.key = key
    }
}