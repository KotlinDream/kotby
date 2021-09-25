package gradle.tasks.guard

import org.gradle.api.Project
import org.gradle.tooling.GradleConnector

class TestRunner(private val project: Project) {
    fun runTest(className: String) {
        val connection = GradleConnector.newConnector().forProjectDirectory(project.projectDir).connect()

        try {
            connection.newBuild().forTasks("test").setStandardOutput(System.out).run()
        } catch (e: Exception) {

        }

//        connection.newTestLauncher()
    }
}