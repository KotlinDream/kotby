package gradle.plugins

import com.adarshr.gradle.testlogger.TestLoggerExtension
import gradle.tasks.GuardTask
import gradle.utils.Logger
import gradle.utils.Logger.Companion.puts
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import java.net.URI


class DevTools: Plugin<Project> {

    override fun apply(project: Project) {
        setupTestLoggerPlugin(project)
        project.tasks.create("guard", GuardTask::class.java) {
            it.group = "dreamcoder"
        }
    }

    private fun setupTestLoggerPlugin(project: Project) {
        project.repositories.maven {
            it.url = URI("https://plugins.gradle.org/m2/")
        }

        project.allprojects.forEach {
            it.pluginManager.apply("com.adarshr.test-logger")
            project.extensions.configure(TestLoggerExtension::class.java) { testLoggerExtension ->
                testLoggerExtension.theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
                testLoggerExtension.showStackTraces = false
                testLoggerExtension.showSummary = true
                testLoggerExtension.logLevel = LogLevel.QUIET
            }
        }
    }

}