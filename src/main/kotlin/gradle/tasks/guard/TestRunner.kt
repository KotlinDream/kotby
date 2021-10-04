package gradle.tasks.guard

import gradle.utils.Logger.Companion.puts
import mu.KotlinLogging
import org.gradle.api.Project
import org.gradle.tooling.GradleConnector

class TestRunner(private val project: Project) {

    private val logger  = KotlinLogging.logger {}

    fun runAllTest() {
        runTest("*")
    }

    fun runTest(className: String) {
        puts("> 运行项目 [${project.name}] 类 [$className] 的测试")
        val connection = GradleConnector.newConnector().forProjectDirectory(project.projectDir).connect()

        try {
            connection.newTestLauncher().withJvmTestClasses(className).setStandardOutput(System.out).run()
        } catch (e: Exception) {
            logger.info { e.printStackTrace() }
        }

        connection.close()
    }
}