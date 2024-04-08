package models.interfaces

interface IHasBlockSize {
    var blockSize: Int
    val MIN_BLOCK_SIZE: Int
        get() = 0

    fun isBlockSizeValid() : Boolean {
        return blockSize > MIN_BLOCK_SIZE
    }
}