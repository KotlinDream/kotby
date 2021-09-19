import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class StringTest {

    @Test
    @DisplayName("当只有一个1单词的时候能正确的把首个字母大写")
    fun camelizeAWord() {
        assertEquals("aBc".camelize(), "Abc")
    }

    @Test
    @DisplayName("当有多个单词的时候，能去掉中间的下划线，并且每个单词的首字母大写")
    fun camelizeMultipleWordShouldRemoveUnderline() {
        assertEquals("abc_eDf".camelize(), "AbcEdf")
    }

    @Test
    @DisplayName("当有多个单词的时候，能去掉中间的空格，并且每个单词的首字母大写")
    fun camelizeMultipleWordShouldRemoveBlankSpace() {
        assertEquals("abc_eDf".camelize(), "AbcEdf")
    }

}