package gradle.plugins

import gradle.tasks.GuardTask
import org.gradle.api.Plugin
import org.gradle.api.Project


class DevTools: Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("guard", GuardTask::class.java) {
            it.group = "dreamcoder"
        }
    }

}