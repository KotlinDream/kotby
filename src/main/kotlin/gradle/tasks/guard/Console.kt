package gradle.tasks.guard

import gradle.utils.Logger.Companion.puts
import kotlinx.coroutines.channels.Channel
import org.gradle.api.Project

class Console(private val project: Project, private val channel: Channel<String>) {
    fun start() {
        while(true) {
            val input = readLine().toString().trim()
            puts("Get User Input $input" )
            if (input.isEmpty()) {
                puts("Run All Test")
                TestRunner(project).runAllTest()
            } else {
                puts("Don't do anything")
            }
        }
    }
}


