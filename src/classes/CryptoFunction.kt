package classes

abstract class CryptoFunction<T> {
    companion object{
        const val SAMPLE_TEXT = "the quick brown fox jumps over the lazy dog"
    }

    var key : T = generateKey()
        protected set

    abstract fun decrypt(str: String): String;

    abstract fun encrypt(str: String): String;

    abstract fun generateKey(): T;

    abstract fun isValidForKey(potentialKey: T): Boolean;

    fun loadKey(key: T){
        if (!isValidForKey(key)){
            throw Exception("Invalid key")
        }
        this.key = key
    }
}