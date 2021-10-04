package gradle.utils

import java.io.File

class ClassFileConversion {
    companion object {
        fun fullClassNameByFile(filePath: String): String {
            val dirs = filePath.split("/")
            val className = File(filePath).nameWithoutExtension
            val srcIndex = dirs.lastIndexOf("src")

            return if (srcIndex < 0 || srcIndex + 3 >= dirs.size) {
                ""
            } else {
                val packageName = dirs.subList(srcIndex + 3, dirs.size - 1).joinToString(".")
                if(packageName.isEmpty()) {
                    className
                } else {
                    "$packageName.$className"
                }
            }
        }

        fun fullTestClassName(filePath: String): String {
            var testFilePath = filePath.replace("main", "test")
            return fullClassNameByFile(testFilePath)
        }
    }
}