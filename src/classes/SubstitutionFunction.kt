package classes

class SubstitutionFunction : CryptoFunction<String>() {
    private fun crypt(str: String, no: Boolean): String{
        var copyStr = str
        copyStr = copyStr.lowercase()
        var result = ""
        copyStr.forEachIndexed { i, c ->
            if (c in 'a'..'z'){
                val keyInt = key[i% key.length].code - 97
                var newChar = c + (if (no) -1 else 1) * keyInt
                if ((newChar < 'a' && no) || (newChar > 'z' && !no)){
                    newChar -= (if (no) -1 else 1) * 26
                }
                result += newChar
            } else {
                result += c
            }
        }
        return result
    }

    override fun decrypt(str: String): String {
        return crypt(str, true)
    }

    override fun encrypt(str: String): String {
        return crypt(str, false)
    }

    override fun generateKey(): String {
        val rand = (1..32).map { ('a'..'z').random() }.joinToString("")
        loadKey(rand)
        return key
    }

    override fun isValidForKey(potentialKey: String): Boolean {
        val lowerAlphabet = ('a'..'z').toList()
        return potentialKey.all { it in lowerAlphabet }
    }
}