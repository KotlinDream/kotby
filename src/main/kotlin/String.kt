
fun String.camelize() : String {
    return this.split(Regex("_|\\ ")).joinToString("") {
            word ->  word.lowercase().replaceFirstChar{ it.uppercase() }
    }
}
