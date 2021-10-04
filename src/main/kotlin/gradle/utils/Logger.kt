package gradle.utils

import mu.KotlinLogging

class Logger {
    companion object {
        private val logger = KotlinLogging.logger {}

        fun puts(message: String) {
            println(message)
        }
    }
}