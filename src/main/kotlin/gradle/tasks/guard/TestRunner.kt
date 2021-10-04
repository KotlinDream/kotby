package gradle.tasks.guard

import gradle.utils.Logger.Companion.puts
import mu.KotlinLogging
import org.gradle.api.Project
import org.gradle.tooling.GradleConnector

class TestRunner(private val project: Project) {

    private val logger  = KotlinLogging.logger {}
    private val connection = GradleConnector.newConnector().forProjectDirectory(project.projectDir).connect()

    fun runAllTest() {
        runTest("*")
    }

    fun runTest(className: String) {
        puts("运行项目 [${project.name}] 类 [$className] 的测试 [${project.projectDir.absolutePath}]")

        try {
            connection.newTestLauncher()
                .withJvmTestClasses(className)
                .setStandardOutput(System.out)
                .withArguments("-q")
                .run()
        } catch (e: Exception) {
            logger.info { e.printStackTrace() }
        }
    }
}