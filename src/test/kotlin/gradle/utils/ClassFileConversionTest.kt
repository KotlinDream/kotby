package gradle.utils

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ClassFileConversionTest {

    @Test
    @DisplayName("得到跟目录下的包名")
    fun getFullClassNameInRootPackageByFile() {
        val fullClassName = ClassFileConversion.fullClassNameByFile(
            "/Users/abc/project/java/test_project/src/test/kotlin/MainKtTest.kt")
        fullClassName.shouldBeEqualTo("MainKtTest")
    }

    @Test
    @DisplayName("得到一级包名的类")
    fun getFullClassNameInOneLevelPackageByFile() {
        val fullClassName = ClassFileConversion.fullClassNameByFile(
            "/Users/abc/project/java/test_project/src/test/kotlin/abc/MainKtTest.kt")

        fullClassName.shouldBeEqualTo("abc.MainKtTest")
    }

    @Test
    @DisplayName("得到两级包名的类")
    fun getFullClassNameInTwoLevelPackageByFile() {
        val fullClassName = ClassFileConversion.fullClassNameByFile(
            "/Users/abc/project/java/test_project/src/test/kotlin/abc/def/MainKtTest.kt")

        fullClassName.shouldBeEqualTo("abc.def.MainKtTest")
    }

}