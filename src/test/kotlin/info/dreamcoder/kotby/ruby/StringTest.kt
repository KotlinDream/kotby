package info.dreamcoder.kotby.ruby

import camelize
import com.github.vertical_blank.sqlformatter.SqlFormatter
import formatSql
import org.amshove.kluent.shouldBeEqualTo
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

    @Test
    fun `formatSql能正确的格式化sql`() {
        val sql = "select major_name,studentCount,count(b.student_id) as jiuyrenshu ,count(b.student_id)/studentCount\n" +
                "from (select m.major_name, count(s.student_id) as studentCount from student s,class_ c_,major m\n" +
                "where s.class_id in c_.class_id and c_.grade_id = '001' and c_.major_id = '001' and m.major_id=c_.major_id\n" +
                "group by m.major_name) a,(select distinct student_id from employment_register_society\n" +
                "where verify_state=1 union\n" +
                "select distinct student_id from employment_register_school\n" +
                "where verify_state=1) b\n" +
                "group by major_name,studentCount;"

        sql.formatSql() shouldBeEqualTo SqlFormatter.format(sql)
    }

}