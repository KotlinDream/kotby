package gradle.tasks.guard

import FileWatcher
import gradle.utils.ClassFileConversion.Companion.fullTestClassName
import gradle.utils.Logger.Companion.puts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.gradle.api.Project
import java.io.File

class ProjectFileWatcher(private val project: Project, private val channel: Channel<String>) {

    private val logger  = KotlinLogging.logger {}

    private val fileWatcher = FileWatcher(project.projectDir.path + "/src").apply {
        onFileModify { fileModifyEvent(it) }
    }

    fun run() {
        puts("开始监听项目 [${project.name}] ")
        fileWatcher.create()
    }

    private fun fileModifyEvent(filePath: String) {
        val testRunner = TestRunner(project)
        val extensionNames = listOf<String>("kt", "java")
        val fileException = File(filePath).extension

        if(extensionNames.contains(fileException)) {
            val fullTestClassName = fullTestClassName(filePath)
            testRunner.runTest(fullTestClassName)
        }
    }

}