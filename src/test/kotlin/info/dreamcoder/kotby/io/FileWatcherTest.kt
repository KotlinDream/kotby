package info.dreamcoder.kotby.io

import kotlinx.coroutines.*
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import java.io.File


internal class FileWatcherTest {
    private lateinit var watcher: FileWatcher
    private val testDir = "/tmp/FileWatcherTest"
    private val testFile = "/tmp/FileWatcherTest/test_file"
    private val testCreateFile = "/tmp/FileWatcherTest/test_create_file"

    @BeforeEach
    fun initWatcher() {
        File(testDir).deleteRecursively()
        File(testDir).mkdir()
        File(testFile).createNewFile()

        watcher = FileWatcher(testDir)
    }

    @AfterEach
    fun closeWatcher() {
        watcher.close()
        File(testDir).deleteRecursively()
    }

    @Test
    fun `创建监听后，当文件创建的时候，会回调onFileCreate对应的函数`() {

        watcher.onFileCreate {
            assertEquals(it, testCreateFile)
            assertNotEquals(it, testFile)

            watcher.close()
        }

        startWatcher {
            File(testCreateFile).createNewFile()
        }
    }

    @Test
    fun `创建监听后，当文件删除的时候，会回调onFileDelete对应的函数`() {

        watcher.onFileDelete {
            assertEquals(it, testFile)

            watcher.close()
        }

        startWatcher {
            File(testFile).deleteRecursively()
        }
    }

    @Test
    fun `创建监听后，当内容变更的时候，会回调onFileModify对应的函数`() {
        watcher.onFileModify {
            assertEquals(it, testFile)

            watcher.close()
        }

        startWatcher {
            File(testFile).writeText("test text")
        }
    }

    private fun startWatcher(block: () -> Unit) = runBlocking {
        launch {
            withContext(Dispatchers.IO) {
                try { watcher.create() } catch (e: Exception) { }
            }
        }

        launch {
            delay(1000)
            block.invoke()
        }
    }

}