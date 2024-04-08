package classes

import java.io.File

class FileHelper {
    companion object{
        fun appendToFileName(file: File, text: String): String{
            val fullPath = file.path
            val extension = file.extension
            val fullPathWithoutExtension = fullPath.substring(0, fullPath.length - extension.length - 1)
            return "$fullPathWithoutExtension$text.$extension"
        }
    }
}