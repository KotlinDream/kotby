
fun String.camelize() : String {
    return this.split("_").joinToString("") {
            word ->  word.lowercase().replaceFirstChar{ it.uppercase() }
    }
}
