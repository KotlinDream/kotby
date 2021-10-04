package gradle.tasks

import gradle.tasks.guard.Console
import gradle.tasks.guard.ProjectFileWatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


open class GuardTask : DefaultTask(){
    private val channel = Channel<String>(UNLIMITED)

    @TaskAction
    fun run() {
        startAllProjectWatch()
        startConsoleWatch()
    }

    private fun startAllProjectWatch() {
        ProjectFileWatcher(project, channel).run()
        project.subprojects.forEach{
            ProjectFileWatcher(it, channel).run()
        }
    }

    private fun startConsoleWatch() {
        Console(project, channel).start()
    }

}
