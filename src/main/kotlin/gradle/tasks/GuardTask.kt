package gradle.tasks

import FileWatcher
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


open class GuardTask : DefaultTask(){

    @TaskAction
    fun run() {
        FileWatcher(project.projectDir.path).apply {
            onFileCreate { fileCreateEvent(it) }
            onFileModify { fileModifyEvent(it) }
            onFileDelete { fileDeleteEvent(it) }
        }.create()
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
