import com.github.vertical_blank.sqlformatter.SqlFormatter

fun String.camelize() : String {
    return this.split(Regex("_|\\ ")).joinToString("") {
            word ->  word.lowercase().replaceFirstChar{ it.uppercase() }
    }
}

fun String.formatSql() : String {
    return SqlFormatter.format(this)
}


