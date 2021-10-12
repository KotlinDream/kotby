package info.dreamcoder.kotby.io

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class FileContentWatcherTest {
    private val filePath = "/tmp/FileContentWatcherTestFile"

    @Test
    fun `如果传入的是目录，返回false`() {
        FileContentWatcher.isChange("/tmp").shouldBeFalse()
    }

    @Test
    fun `如果文件不存在，返回false`() {
        FileContentWatcher.isChange("/tmp/xxxa23f").shouldBeFalse()
    }

    @Test
    fun `文件如果存在，且第一次判断返回true`() {

        File(filePath).createNewFile()
        FileContentWatcher.isChange(filePath).shouldBeTrue()
        File(filePath).deleteRecursively()
    }

    @Test
    fun `文件如果存在，文件内容不变，第二次之后的判断都应该返回false`() {
        File(filePath).createNewFile()
        FileContentWatcher.isChange(filePath)

        FileContentWatcher.isChange(filePath).shouldBeFalse()
        FileContentWatcher.isChange(filePath).shouldBeFalse()

        File(filePath).deleteRecursively()
    }

    @Test
    fun `文件如果存在，文件内容变化，第二次的判断都应该返回true`() {
        File(filePath).createNewFile()
        FileContentWatcher.isChange(filePath)
        File(filePath).writeText("test change")

        FileContentWatcher.isChange(filePath).shouldBeTrue()

        File(filePath).deleteRecursively()
    }

}
