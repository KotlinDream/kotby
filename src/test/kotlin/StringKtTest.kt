import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StringKtTest : DescribeSpec({

    describe("camelize") {
        it("当只有一个1单词的时候能正确的把首个字母大写") {
            "aBc".camelize() shouldBe "Abc"
        }

        it("当有多个单词的时候，能去掉中间的下划线，并且每个单词的首字母大写") {
            "abc_eDf".camelize() shouldBe "AbcEdf"
        }

        it("当有多个单词的时候，能去掉中间的空格，并且每个单词的首字母大写") {
            "abc eDf".camelize() shouldBe "AbcEdf"
        }
    }
})
