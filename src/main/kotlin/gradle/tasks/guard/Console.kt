package gradle.tasks.guard

import kotlinx.coroutines.channels.Channel
import mu.KotlinLogging
import org.gradle.api.Project

class Console(private val project: Project, private val channel: Channel<String>) {
    private val logger  = KotlinLogging.logger {}

    fun start() {
        while(true) {
            val input = readLine()
            println("Get User Input $input")
            TestRunner(project).runTest("*")
        }
    }
}


