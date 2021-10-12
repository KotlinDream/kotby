package info.dreamcoder.kotby.io

import java.io.File

object FileContentWatcher {
    private val files = mutableMapOf<String, String>()

    fun isChange(filePath: String): Boolean {
        var change = false

        if(File(filePath).isFile) {
            val fileContent = File(filePath).readText()
            change = if(files.containsKey(filePath)) {
                !files[filePath].equals(fileContent)
            } else {
                true
            }
            files[filePath] = fileContent
        }

        return change
    }
}
