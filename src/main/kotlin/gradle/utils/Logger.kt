package gradle.utils

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal

class Logger {
    companion object {
        private val t = Terminal(AnsiLevel.TRUECOLOR)

        fun puts(message: String) {
            t.println("> ${green("Kotby")} $message")
        }
    }
}
