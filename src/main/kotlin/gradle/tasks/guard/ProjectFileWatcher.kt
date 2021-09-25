package gradle.tasks.guard

import FileWatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.gradle.api.Project

class ProjectFileWatcher(private val project: Project, private val channel: Channel<String>) {

    private val logger  = KotlinLogging.logger {}

    private val fileWatcher = FileWatcher(project.projectDir.path).apply {
        onFileCreate { fileCreateEvent(it) }
        onFileModify { fileModifyEvent(it) }
        onFileDelete { fileDeleteEvent(it) }
    }

    suspend fun run() {
        withContext(Dispatchers.IO) {
            fileWatcher.create()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun fileCreateEvent(file: String) {

    }

    @Suppress("UNUSED_PARAMETER")
    private fun fileModifyEvent(file: String) {

    }

    @Suppress("UNUSED_PARAMETER")
    private fun fileDeleteEvent(file: String) {

    }
}