package classes

class CaesarFunction : CryptoFunction<Int>() {
    private fun crypt(str: String, no: Boolean): String{
        var copyStr = str
        copyStr = copyStr.lowercase()
        var result = ""
        copyStr.forEachIndexed { i, c ->
            val newChar = c + (if (no) -1 else 1) * key
            result += newChar
        }
        return result
    }

    override fun decrypt(str: String): String {
        return crypt(str, true)
    }

    override fun encrypt(str: String): String {
        return crypt(str, false)
    }

    override fun generateKey(): Int {
        val rand = (1..4096).random()
        loadKey(rand)
        return key
    }

    override fun isValidForKey(potentialKey: Int): Boolean {
        return true
    }
}