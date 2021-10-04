package gradle.tasks

import gradle.tasks.guard.Console
import gradle.tasks.guard.ProjectFileWatcher
import gradle.tasks.guard.TestRunner
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


open class GuardTask : DefaultTask(){

    @TaskAction
    fun run() {
        val channel = Channel<String>(UNLIMITED)

        GlobalScope.launch {
            ProjectFileWatcher(project, channel).run()
            project.subprojects.forEach{
                ProjectFileWatcher(it, channel).run()
            }
        }

        Console(project, channel).start()
    }
}
